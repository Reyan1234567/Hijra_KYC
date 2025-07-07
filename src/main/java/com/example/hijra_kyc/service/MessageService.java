package com.example.hijra_kyc.service;

import com.example.hijra_kyc.KycUserProfile;
import com.example.hijra_kyc.dto.MessageEdit;
import com.example.hijra_kyc.dto.MessageInDto;
import com.example.hijra_kyc.mapper.MessageMapper;
import com.example.hijra_kyc.mapper.MessageOutMapper;
import com.example.hijra_kyc.model.Base;
import com.example.hijra_kyc.model.BaseList;
import com.example.hijra_kyc.model.Message;
import com.example.hijra_kyc.repository.MessageRepository;
import com.example.hijra_kyc.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final UserRepository userRepository;
    private final BaseService baseService;

    public Base<?> saveMessage(MessageInDto messageInDto){
        Message message = messageMapper.toMessage(messageInDto);

        try{
            KycUserProfile reciever=userRepository.findById((long) messageInDto.getReceiver())
                    .orElseThrow(()->new RuntimeException("Receiver Not Found"));
            message.setRecieverId(reciever);
            KycUserProfile sender=userRepository.findById((long) messageInDto.getSender())
                    .orElseThrow(()->new RuntimeException("Sender Not Found"));
            message.setSenderId(sender);
            var messageField=messageInDto.getMessage();
            if(messageField.isEmpty()){
                return baseService.error("Message Field Empty");
            }
            var response=messageRepository.save(message);
            return baseService.success(messageMapper.messageOutMapper(response));
        }
        catch(Exception e){
            return baseService.error(e.getMessage());
        }
    }

    public BaseList<?> getConversation(Long user1, Long user2){
        try{
           userRepository.findById(user1)
                   .orElseThrow(()->new RuntimeException("User1 Not Found"));
           userRepository.findById(user2)
                   .orElseThrow(()->new RuntimeException("User2 Not Found"));
           var result= messageRepository.findConversationBetweenUsers(user1,user2);
           return baseService.listSuccess(result.stream()
                   .map(messageMapper::messageOutMapper)
                   .toList());
        }
        catch(Exception e){
            return baseService.listError(e.getMessage());
        }
    }
    @Transactional
    public Base<?> updateStatus(Long senderId, Long receiverId) {
        try{
            messageRepository.findById(senderId.intValue())
                    .orElseThrow(()-> new RuntimeException("Sender Not Found"));
            messageRepository.findById(receiverId.intValue())
                    .orElseThrow(()->new RuntimeException("Receiver Not Found"));
            messageRepository.updateStatus(senderId, receiverId);
            return baseService.success("Message Updated successfully");
        }
        catch(Exception e){
            return baseService.error(e.getMessage());
        }
    }

    public Base<?> deleteMessage(int id, int senderId) {
        try{
            Message message=messageRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Message Not Found"));
            if(message.getSenderId().getId()!=senderId){
                return baseService.error("Unauthorized!");
            }
            messageRepository.deleteById(message.getId());
            return baseService.success("Message Deleted successfully");
        }
        catch(Exception e){
            return baseService.error(e.getMessage());
        }
    }


    public Base<?> updateMessage(Long senderId, Long receiverId, MessageEdit message, int id){
        try{
            var messageValue=messageRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("Message Not Found"));
            if(senderId.intValue()!=messageValue.getSenderId().getId()){
                return baseService.error("Unauthorized!");
            }
            var lastMessage=messageRepository.findLatestMessage(receiverId, senderId);
            if(lastMessage==null){
                return baseService.error("Message Not Found");
            }
            System.out.println(lastMessage.get(0).getId());
            if(!lastMessage.get(0).getId().equals(messageValue.getId())){
                return baseService.error("Can't Update Message!");
            }
            messageValue.setMessageBody(message.getMessage());
            var updateResult=messageRepository.save(messageValue);
            return baseService.success(messageMapper.messageOutMapper(updateResult));
        }
        catch(Exception e){
            return baseService.error(e.getMessage());
        }
    }
}
