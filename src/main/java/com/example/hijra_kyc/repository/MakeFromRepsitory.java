package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MakeFromRepsitory extends JpaRepository<Message, Integer> {
}
