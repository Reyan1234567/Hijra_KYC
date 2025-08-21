package com.example.hijra_kyc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.hijra_kyc.model.UserProfile;

@Service
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{

    @Query("Select u.id from UserProfile u where(u.roleId.roleId=:roleId and u.status=1)")
    List<Long> findPresentUsers(@Param("roleId") Long roleId);

    @Modifying
    @Query("Update UserProfile set status=2 where roleId.roleId=:roleId ")
    Optional<UserProfile> findByUserId(String userId);

    @Query("Select u from UserProfile u where u.roleId.roleId=2")
    List<UserProfile> findCheckersPresentToday();

    @Query("Select u from UserProfile u where u.userName=:username")
    Optional<UserProfile> findByUsername(String username);
}
