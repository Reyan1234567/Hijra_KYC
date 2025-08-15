package com.example.hijra_kyc.service;

import com.example.hijra_kyc.model.MakeForm;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.MakeFormRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;
import com.example.hijra_kyc.util.countOfUnmade;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.*;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class DistributorService {

    private final MakeFormRepository makeFormRepository;
    private final UserProfileRepository userProfileRepository;

    final Long NIGHT_CHECKER_1=19L;
    final Long NIGHT_CHECKER_2=25L;

    final Long ROLE=2L;

    @Transactional
    public void setPresent(){
        try{
            userProfileRepository.updateUsersAttendance(ROLE);
        }
        catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Long> whoIsPresent(){
        try{
            return userProfileRepository.findPresentUsers(ROLE);
        }
        catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Integer> makesNotAssignedToday(){
        try{
            List<Integer> makesNotAssignedToday = makeFormRepository.findMakeForms(getBeginningOfTheMorning());
            log.info(getBeginningOfTheMorning().toString());
            log.info("makesNotAssignedToday:{}", makesNotAssignedToday);
            makesNotAssignedToday.forEach((make)->log.info(make.toString()));
            return makesNotAssignedToday;
        }
        catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    public void changeStatus(List<Long> ids){
        try{
            for (Long id : ids) {
                UserProfile user = userProfileRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User with id: " + id + " not found"));
                user.setPresentStatus(1);
                userProfileRepository.save(user);
            }
        }
        catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void Makeassign(Long hoId, List<Long> ids){
        System.out.println("In Makeassign function");
        UserProfile headOfficeProfile=userProfileRepository.findById(hoId)
                .orElseThrow(()->new RuntimeException("User with id: "+hoId+" not found"));
        System.out.println("headOfficeProfile "+headOfficeProfile.getId());
        for(Long id:ids){
            System.out.println("In the for loop of the Makeassign");
            MakeForm makeForm=makeFormRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("Make form with id: "+id+" not found"));
            makeForm.setHo(headOfficeProfile);
            System.out.println(makeForm.getHo());
            makeFormRepository.save(makeForm);
        }
    }
    @Transactional
    public String Assign(){
        List<Long> presentCheckers=whoIsPresent();
        List<Integer> makesNotAssignedToday=makesNotAssignedToday();
        System.out.println(makesNotAssignedToday);
        long indexVariable=0;

        if(presentCheckers.isEmpty()){
           throw new EntityNotFoundException("No available checkers");
        }
        if(makesNotAssignedToday.isEmpty()){
            throw new EntityNotFoundException("Nothing to assign");
        }

        long forEachChecker = makesNotAssignedToday.size() / presentCheckers.size();
        System.out.println("forEachChecker: "+forEachChecker);
        if(forEachChecker==0){
            List<Long> shuffledCheckers=new ArrayList<>(presentCheckers);
            Collections.shuffle(shuffledCheckers);

            for (int i=0; i<makesNotAssignedToday.size(); i++) {
                makeFormRepository.updateHoIdOfaListOfMakeForms(shuffledCheckers.get(i), List.of(makesNotAssignedToday.get(i)), Instant.now());
            }
            return "assigned successfully";
        }

        else{
            System.out.println("In the else");
            List<countOfUnmade> unmades=makeFormRepository.findCheckersPerformance(getBeginningOfTheMorning(),ROLE);
            if(unmades.isEmpty()){
                throw new EntityNotFoundException("No available checkers");
            }
            else{
                List<countOfUnmade> correctList= new ArrayList<>(unmades.stream().filter((u) -> presentCheckers.contains(u.getId())).toList());
                correctList.sort(Comparator.comparingLong(countOfUnmade::getCount).reversed());
                for(int i=0; i<correctList.size()/2; i++){
                    var change=forEachChecker-correctList.get(i).getCount()/2;
                    if(change<0){
                        correctList.get(i).setCount(forEachChecker-forEachChecker/2);
                        correctList.get(correctList.size() - 1 - i).setCount(forEachChecker + forEachChecker/2);
                    }
                    else{
                        correctList.get(i).setCount(change);
                        correctList.get(correctList.size() - 1 - i).setCount(forEachChecker + correctList.get(i).getCount() / 2);
                    }
                }
                if(correctList.size()%2!=0){
                    correctList.get(correctList.size()/2).setCount(forEachChecker);
                }
                long toBeCapped=0;
                for(countOfUnmade unmade:correctList){
                    toBeCapped=Math.min(indexVariable + unmade.getCount(), makesNotAssignedToday.size());
                    List<Integer> divided=makesNotAssignedToday.subList((int)indexVariable, (int)toBeCapped);
                    indexVariable=toBeCapped;

                    if(!divided.isEmpty()){
                        makeFormRepository.updateHoIdOfaListOfMakeForms(unmade.getId(), divided, Instant.now());
                    }
                }

                if(toBeCapped<makesNotAssignedToday.size()){
                    List<Integer> leftovers=makesNotAssignedToday.subList((int)toBeCapped, makesNotAssignedToday.size());
                    Collections.shuffle(presentCheckers);
                    int i=0;
                    for(Integer left:leftovers){
                        makeFormRepository.updateHoIdOfaListOfMakeForms(
                                presentCheckers.get(i++ % presentCheckers.size()), List.of(left), Instant.now()
                        );
                    }
                }
            }
            return "assigned successfully";
        }
    }

    @Transactional
    public String AssignNight(){
        List<Integer> makesLeft=makeFormRepository.findLeftMakes(getBeginningOfTheMorning());
        makeFormRepository.updateHoIdOfaListOfMakeForms(NIGHT_CHECKER_1, makesLeft.subList(0,makesLeft.size()/2), Instant.now());
        makeFormRepository.updateHoIdOfaListOfMakeForms(NIGHT_CHECKER_2, makesLeft.subList(makesLeft.size()/2,makesLeft.size()), Instant.now());
        return "Night shift started";
    }

    public Instant getBeginningOfTheMorning(){
        LocalDate today = LocalDate.now().minusDays(9);
        LocalTime todayTime = LocalTime.of(8, 0);
        return LocalDateTime.of(today, todayTime).atZone(ZoneId.systemDefault()).toInstant();

    }
}














//                int from=0;
//                int till=forEachChecker;
//                till=Math.min(till, makesNotAssignedToday.size());
//                for(Integer present:numberOfPresent){
//                    Makeassign(present, makesNotAssignedToday.subList(from,till));
//                    from=till;
//                    till+=forEachChecker;
//
//               }
//
//
//
//                    if(unmade.getCount()>forEachChecker){
//        System.out.println("In the second jump");
//                        continue;
//                                }
