package com.example.hijra_kyc.service;

import com.example.hijra_kyc.model.Base;
import com.example.hijra_kyc.model.MakeForm;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.MakeFormRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class DistributorService {

    private final BaseService baseService;
    private final MakeFormRepository makeFormRepository;
    private final UserProfileRepository userProfileRepository;

    final int NIGHT_CHECKER_1=19;
    final int NIGHT_CHECKER_2=25;

    final String ROLE="rol/003";

    @Transactional
    public void setPresent(){
        try{
            userProfileRepository.updateUsersAttendance(ROLE);
        }
        catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Integer> whoIsPresent(){
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

    public String Assign(){

        int forEachChecker;
        List<Integer> PresentCheckers=whoIsPresent();
        List<Integer> makesNotAssignedToday=makesNotAssignedToday();
        int indexVariable=0;
        if(PresentCheckers.size()==0){
           throw new EntityNotFoundException("No available checkers");
        }
        if(makesNotAssignedToday.isEmpty()){
            throw new EntityNotFoundException("Nothing to assign");
        }

        forEachChecker = makesNotAssignedToday.size() / PresentCheckers.size();

        if(forEachChecker==0){
            List<Integer> shuffledCheckers=new ArrayList<>(PresentCheckers);
            Collections.shuffle(shuffledCheckers);

            for (int i=0; i<makesNotAssignedToday.size(); i++) {
                makeFormRepository.updateHoIdOfaListOfMakeForms(shuffledCheckers.get(i), List.of(makesNotAssignedToday.get(i)));
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
                for(countOfUnmade unmade:unmades){
                    if(!PresentCheckers.contains(unmade.getId())){
                        continue;
                    }
                    if(unmade.getCount()>forEachChecker){
                        continue;
                    }
                    int comparisonVar=forEachChecker-unmade.getCount();
                    unmade.setCount(Math.max(comparisonVar, 0));
                    int toBeCapped=Math.min(indexVariable + unmade.getCount(), makesNotAssignedToday.size());
                    List<Integer> divided=makesNotAssignedToday.subList(indexVariable, toBeCapped);
                    indexVariable=toBeCapped;
                    makeFormRepository.updateHoIdOfaListOfMakeForms(unmade.getId(), divided);
                }
            }
            return "assigned successfully";
        }

    }

    @Transactional
    public void AssignNight(){
        List<Integer> makesLeft=makeFormRepository.findLeftMakes(getTime());
        makeFormRepository.updateHoIdOfaListOfMakeForms(NIGHT_CHECKER_1, makesLeft.subList(0,makesLeft.size()/2));
        makeFormRepository.updateHoIdOfaListOfMakeForms(NIGHT_CHECKER_2, makesLeft.subList(makesLeft.size()/2,makesLeft.size()));
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
