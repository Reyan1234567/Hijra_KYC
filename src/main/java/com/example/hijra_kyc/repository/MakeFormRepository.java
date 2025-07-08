package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.model.MakeForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
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

    @Query("Select m from MakeForm m where m.maker=:maker_Id")
    MakeForm findMakeFormById(@Param("maker_Id") int id);
}
