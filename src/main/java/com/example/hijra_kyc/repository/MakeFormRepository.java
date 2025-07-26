package com.example.hijra_kyc.repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hijra_kyc.model.MakeForm;
import com.example.hijra_kyc.util.countOfUnmade;

@Repository
public interface MakeFormRepository extends JpaRepository<MakeForm, Long> {

    @Query("SELECT m FROM MakeForm m WHERE m.maker.id = :makerId AND m.status = :status")
    List<MakeForm> findByMakerAndStatus(@Param("makerId") Long makerId, @Param("status") Long status);

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

    @Query("Select NEW com.example.hijra_kyc.util.countOfUnmade(u.id, count(u.id)) as count from UserProfile u left join MakeForm m on m.ho.id=u.id and m.hoActionTime is null and m.makeTime>:day where u.roleId.roleId=:role group by u.id")
    List<countOfUnmade> findCheckersPerformance(@Param("day") Instant day, @Param("role") Long role);

    @Query("Select m.id from MakeForm m where m.hoActionTime is null and m.makeTime>:day")
    List<Integer> findLeftMakes(@Param("day") Instant day);

    @Modifying
    @Query("UPDATE MakeForm m set m.ho.id=:hoId, m.hoAssignTime=:time where m.id in :ids ")
    void updateHoIdOfaListOfMakeForms(@Param("hoId") Long id, @Param("ids") List<Integer> ids, @Param("time") Instant now);

    @Query("Select m from MakeForm m where m.ho.id=:id")
    List<MakeForm> getMakeFormByHoId(@Param("id") Long id);
}
