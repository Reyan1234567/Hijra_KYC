package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.model.Message;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.util.userMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("Select m from Message m where(m.senderId.id=:user1 and m.recieverId.id=:user2)or(m.senderId.id=:user2 and m.recieverId.id=:user1)")
    List<Message> findConversationBetweenUsers(@Param("user1") Long user1, @Param("user2") Long user2);

    @Modifying
    @Query("Update Message m set m.receivedTimestamp=current_timestamp, m.recieverStatus=1 where (m.senderId.id=:senderId and m.recieverId.id=:receiverId and m.recieverStatus=0)")
    void updateStatus(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    @Query("Select m from Message m where(m.senderId.id=:senderId and m.recieverId.id=:recieverId) order by m.id desc")
    List<Message> findLatestMessage(@Param("recieverId") Long recieverId, @Param("senderId") Long senderId);

    @Query("Select NEW com.example.hijra_kyc.util.userMessage(u.id, u.firstName, u.lastName, u.roleId.roleName, u.branch.name, u.status, u.photoUrl, count(m.senderId.id)) from UserProfile u left JOIN Message m on u.id=m.senderId.id and m.recieverId.id=:id and m.recieverStatus=0 where u.id!=:id group by u.id")
    List<userMessage> findUnread(@Param("id") Long id);

    @Query("Select u from UserProfile u where u.id!=:id")
    List<UserProfile> allExceptMe(@Param("id") Long id);

    @Query("Select m from Message m where m.recieverId.id=:id group by m.senderId.id")
    List<Message> messagesSentToMe(@Param("id") Long id);
//    @Modifying
//    @Query("Update Message m set m.messageBody=:messageBody where m.id=:id")
//    void updateMessageBody(@Param("id") Integer id, @Param("messageBody") String messageBody);
}


//NEW com.example.hijra_kyc.util.countOfUnread(m.senderId.id, count(m.id))