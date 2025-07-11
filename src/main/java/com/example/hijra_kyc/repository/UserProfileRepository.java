package com.example.hijra_kyc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.hijra_kyc.model.UserProfile;

@Service
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{

    @Query("Select u.id from UserProfile u where(u.roleId.roleId=:roleId and u.status=1)")
    List<Integer> findPresentUsers(@Param("roleId") String roleId);

    @Modifying
    @Query("Update UserProfile set status=2 where roleId.roleId=:roleId ")
    void updateUsersAttendance(@Param("roleId") String roleId);

}
