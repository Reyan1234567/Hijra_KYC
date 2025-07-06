package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("Select m from Message m where(m.senderId.id=:user1 and m.recieverId.id=:user2)or(m.senderId.id=:user2 and m.recieverId.id=:user1)")
    List<Message> findConversationBetweenUsers(@Param("user1") Long user1, @Param("user2") Long user2);

    @Modifying
    @Query("Update Message m set m.receivedTimestamp=current_timestamp, m.recieverStatus=1 where (m.senderId.id=:senderId and m.recieverId.id=:receiverId and m.recieverStatus=0)")
    void updateStatus(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    @Query("Select m from Message m where(m.senderId.id=:senderId and m.recieverId.id=:recieverId) order by m.id desc")
    List<Message> findLatestMessage(@Param("recieverId") Long recieverId, @Param("senderId") Long senderId);

//    @Modifying
//    @Query("Update Message m set m.messageBody=:messageBody where m.id=:id")
//    void updateMessageBody(@Param("id") Integer id, @Param("messageBody") String messageBody);
}
