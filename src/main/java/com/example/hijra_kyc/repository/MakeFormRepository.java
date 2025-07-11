package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.model.MakeForm;
import com.example.hijra_kyc.service.countOfUnmade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface MakeFormRepository extends JpaRepository<MakeForm, Integer> {
    @Query("Select m from MakeForm m where m.maker.id=:makerId")
    List<MakeForm> findByMaker(Long makerId);

    @Query("Select m from MakeForm m where m.maker.id=:makerId and m.status=:status")
    List<MakeForm> findByMakerAndStatus(Long makerId, Long status);

    @Modifying
    @Query("Update MakeForm set status=:number where id=:id")
    void updateMakeFormStatus(@Param("number") int id, @Param("id") int status);

    @Query("Select m from MakeForm m where m.maker.id=:maker_Id")
    MakeForm findMakeFormById(@Param("maker_Id") int id);

    @Query("Select m from MakeForm m where m.customerAccount=:account")
    MakeForm findMakeFormByAccount(@Param("account") String account);

    @Query("Select m.id from MakeForm m where m.ho is null and m.makeTime > :todayHourMin")
    List<Integer> findMakeForms(Instant todayHourMin);

    @Query("Select m.ho.id, count(m.id) as count from MakeForm m where (m.ho.id is not null and m.hoActionTime is null and m.makeTime>:day) group by m.ho.id")
    List<countOfUnmade> findUnmade(@Param("day") Instant day);

    @Modifying
    @Query("Update MakeForm set ho.id=:id where (ho is null and makeTime > :todayHourMin)")
    void nightAssign(@Param("id") int id, @Param("todayHourMin") LocalDateTime todayHourMin);

    @Query("Select u.id, count(u.id) as count from UserProfile u left join MakeForm m on m.ho.id=u.id and m.hoActionTime is null and m.makeTime>:day where u.roleId=:role group by u.id")
    List<countOfUnmade> findCheckersPerformance(@Param("day") Instant day, @Param("rol/003") String role);

    @Query("Select m.id from MakeForm m where m.hoActionTime is null and m.makeTime>:day")
    List<Integer> findLeftMakes(@Param("day") Instant day);

    @Modifying
    @Query("UPDATE MakeForm m set m.ho.id=:hoId where m.id in :ids")
    void updateHoIdOfaListOfMakeForms(@Param("hoId") int id, @Param("ids") List<Integer> ids);
}
