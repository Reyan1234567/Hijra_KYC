package com.example.hijra_kyc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hijra_kyc.model.MakeForm;

@Repository
public interface MakeFormRepository extends JpaRepository<MakeForm, Long> {

     @Query("SELECT m FROM MakeForm m WHERE m.maker.id = :makerId")
    List<MakeForm> findByMaker(@Param("makerId") Long makerId);

    @Query("SELECT m FROM MakeForm m WHERE m.maker.id = :makerId AND m.status = :status")
    List<MakeForm> findByMakerAndStatus(@Param("makerId") Long makerId, @Param("status") Long status);

    @Query("SELECT m FROM MakeForm m WHERE m.id = :id")
    MakeForm findMakeFormById(@Param("id") Long id);

}
