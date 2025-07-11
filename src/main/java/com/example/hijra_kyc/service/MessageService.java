package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.MessageEdit;
import com.example.hijra_kyc.dto.MessageDto.MessageInDto;
import com.example.hijra_kyc.dto.MessageDto.MessageOutDto;
import com.example.hijra_kyc.mapper.MessageMapper;
import com.example.hijra_kyc.model.Base;
import com.example.hijra_kyc.model.BaseList;
import com.example.hijra_kyc.model.Message;
import com.example.hijra_kyc.model.UserProfile;
import com.example.hijra_kyc.repository.MessageRepository;
import com.example.hijra_kyc.repository.UserProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final UserProfileRepository userRepository;
    private final BaseService baseService;

    public MessageOutDto saveMessage(MessageInDto messageInDto){
        Message message = messageMapper.toMessage(messageInDto);
            UserProfile reciever=userRepository.findById(messageInDto.getReceiver())
                    .orElseThrow(()->new RuntimeException("Receiver Not Found"));
            message.setRecieverId(reciever);
            UserProfile sender=userRepository.findById(messageInDto.getSender())
                    .orElseThrow(()->new RuntimeException("Sender Not Found"));
            message.setSenderId(sender);
            var messageField=messageInDto.getMessage();
            if(messageField.isEmpty()){
                throw new RuntimeException("Message Field Empty");
            }
            var response=messageRepository.save(message);
            return messageMapper.messageOutMapper(response);
    }

    public List<MessageOutDto> getConversation(Long user1, Long user2){
           userRepository.findById(user1)
                   .orElseThrow(()->new RuntimeException("User1 Not Found"));
           userRepository.findById(user2)
                   .orElseThrow(()->new RuntimeException("User2 Not Found"));
           var result= messageRepository.findConversationBetweenUsers(user1,user2);
           return result.stream()
                   .map(messageMapper::messageOutMapper)
                   .toList();
    }

    @Transactional
    public String updateStatus(Long senderId, Long receiverId) {
            messageRepository.findById(senderId)
                    .orElseThrow(()-> new RuntimeException("Sender Not Found"));
            messageRepository.findById(receiverId)
                    .orElseThrow(()->new RuntimeException("Receiver Not Found"));
            messageRepository.updateStatus(senderId, receiverId);
            return "Message Updated successfully";
    }

    public Base<?> deleteMessage(Long id, Long senderId) {
        try{
            Message message=messageRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Message Not Found"));
            if(message.getSenderId().getId() != senderId){
                return baseService.error("Unauthorized!");
            }
            messageRepository.deleteById(message.getId());
            return baseService.success("Message Deleted successfully");
        }
        catch(Exception e){
            return baseService.error(e.getMessage());
        }
    }


    public MessageOutDto updateMessage(Long senderId, Long receiverId, MessageEdit message, Long id){
            var messageValue=messageRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("Message Not Found"));
            if(senderId.intValue()!=messageValue.getSenderId().getId()){
                throw new RuntimeException("Unauthorized!");
            }
            var lastMessage=messageRepository.findLatestMessage(receiverId, senderId);
            if(lastMessage==null){
                throw new EntityNotFoundException("Message Not Found");
            }
            System.out.println(lastMessage.get(0).getId());
            if(!lastMessage.get(0).getId().equals(messageValue.getId())){
                throw new RuntimeException("Can't Update Message!");
            }
            messageValue.setMessageBody(message.getMessage());
            var updateResult=messageRepository.save(messageValue);
            return messageMapper.messageOutMapper(updateResult);
    }
}
