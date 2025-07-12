package com.example.hijra_kyc.service;

import com.example.hijra_kyc.model.Base;
import com.example.hijra_kyc.model.MakeForm;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.MakeFormRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class DistributorService {

    private final MakeFormRepository makeFormRepository;
    private final UserProfileRepository userProfileRepository;

    final Long NIGHT_CHECKER_1=19L;
    final Long NIGHT_CHECKER_2=25L;

    final Long ROLE=3L;

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
            return makeFormRepository.findMakeForms(getTime());
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    public void changeStatus(List<Long> ids){
        try{
            for (Long id : ids) {
                UserProfile user = userProfileRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User with id: " + id + " not found"));
                user.setStatus(2);
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
        List<Long> PresentCheckers=whoIsPresent();
        List<Integer> makesNotAssignedToday=makesNotAssignedToday();
        long indexVariable=0;
        if(PresentCheckers.size()==0){
           throw new EntityNotFoundException("No available checkers");
        }
        if(makesNotAssignedToday.isEmpty()){
            throw new EntityNotFoundException("Nothing to assign");
        }

        int forEachChecker = makesNotAssignedToday.size() / PresentCheckers.size();
        System.out.println("forEachChecker"+forEachChecker);
        if(forEachChecker==0){
            List<Long> shuffledCheckers=new ArrayList<>(PresentCheckers);
            Collections.shuffle(shuffledCheckers);

            for (int i=0; i<makesNotAssignedToday.size(); i++) {
                makeFormRepository.updateHoIdOfaListOfMakeForms(shuffledCheckers.get(i), List.of(makesNotAssignedToday.get(i)), getTime());
            }
            return "assigned successfully";
        }

        else{
            System.out.println("In the else");
            List<countOfUnmade> unmades=makeFormRepository.findCheckersPerformance(getTime(),ROLE);
            if(unmades.isEmpty()){
                throw new EntityNotFoundException("No available checkers");
            }
            else{
                System.out.println("In the second else");
                for(countOfUnmade unmade:unmades){
                    System.out.println("In the for loop");
                    System.out.println(unmade.getId());
                    System.out.println(unmade.getCount());
                    if(!PresentCheckers.contains(unmade.getId())){
                        System.out.println("In the first jump");
                        continue;
                    }
                    if(unmade.getCount()>forEachChecker){
                        System.out.println("In the second jump");
                        continue;
                    }
                    long comparisonVar=forEachChecker-(unmade.getCount()/2);
                    unmade.setCount(Math.max(comparisonVar, 0));
                    long toBeCapped=Math.min(indexVariable + unmade.getCount(), makesNotAssignedToday.size());
                    List<Integer> divided=makesNotAssignedToday.subList((int)indexVariable, (int)toBeCapped);
                    indexVariable=toBeCapped;
                    makeFormRepository.updateHoIdOfaListOfMakeForms(unmade.getId(), divided, getTime());
                }
            }
            return "assigned successfully";
        }

    }

    @Transactional
    public String AssignNight(){
        List<Integer> makesLeft=makeFormRepository.findLeftMakes(getTime());
        makeFormRepository.updateHoIdOfaListOfMakeForms(NIGHT_CHECKER_1, makesLeft.subList(0,makesLeft.size()/2), getTime());
        makeFormRepository.updateHoIdOfaListOfMakeForms(NIGHT_CHECKER_2, makesLeft.subList(makesLeft.size()/2,makesLeft.size()), getTime());
        return "Night shift started";
    }

    public Instant getTime(){
        LocalDate today = LocalDate.now();
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
//                }
