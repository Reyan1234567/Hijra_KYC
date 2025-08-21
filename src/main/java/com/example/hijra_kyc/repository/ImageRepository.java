package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;



@Service
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Modifying
    @Query("Update Image i set i.imageMake.id=null where i.id=:id")
    void updateImageMakes(@Param("id") Long id);
//    @Query("Select u.firstName From KycUserProfile u Inner Join Image i on :makerId = u.id")
//    String getFistName(@Param int makerId);

//    @Query("Select i From Image i where i.imageMake=:makerId")
//    List<Image> makeImages(@Param ("makerId") int makerId);
}
