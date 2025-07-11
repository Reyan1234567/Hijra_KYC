package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.service.countOfUnmade;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hijra_kyc.model.UserProfile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer>{

    @Query("Select u.id from UserProfile u where(u.roleId.roleId=:roleId and u.status=1)")
    List<Integer> findPresentUsers(@Param("roleId") String roleId);

    @Modifying
    @Query("Update UserProfile set status=2 where roleId.roleId=:roleId ")
    void updateUsersAttendance(@Param("roleId") String roleId);

}
