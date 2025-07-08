package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageRepository extends JpaRepository<Image, Integer> {
//    @Query("Select u.firstName From KycUserProfile u Inner Join Image i on :makerId = u.id")
//    String getFistName(@Param int makerId);

//    @Query("Select i From Image i where i.imageMake=:makerId")
//    List<Image> makeImages(@Param ("makerId") int makerId);
}
