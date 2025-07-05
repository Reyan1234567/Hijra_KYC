package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.*;
import com.example.hijra_kyc.model.Base;
import com.example.hijra_kyc.model.BaseList;
import com.example.hijra_kyc.service.BaseService;
import com.example.hijra_kyc.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final BaseService baseService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody MessageInDto message){
        Base<?> save=messageService.saveMessage(message);
        return baseService.rest(save);
    }

    @PostMapping("/getConvo/{id}")
    public ResponseEntity<?> getMessages(@PathVariable Long id){
        BaseList<?> getConversation=messageService.getConversation(id);
        return baseService.rest(getConversation);
    }

    @PatchMapping("/updateSeen/{id}")
    public ResponseEntity<?> update(@PathVariable Long id){
        Base<?> updateResult=messageService.updateStatus(id);
        return baseService.rest(updateResult);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteMessage(@RequestBody MessageDelete message){
        Base<?> deleteMessage=messageService.deleteMessage(message);
        return baseService.rest(deleteMessage);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editMessage(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestBody MessageEdit message){
        Base<?> messageEdit=messageService.updateMessage(senderId, receiverId,message);
        return baseService.rest(messageEdit);
    }

}
