package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.*;
import com.example.hijra_kyc.model.Base;
import com.example.hijra_kyc.model.BaseList;
import com.example.hijra_kyc.service.BaseService;
import com.example.hijra_kyc.service.MessageService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> save(@Valid @RequestBody MessageInDto message){
        Base<?> save=messageService.saveMessage(message);
        return baseService.rest(save);
    }

    @GetMapping("/getConvo")
    public ResponseEntity<?> getMessages(@RequestParam("user1") Long id, @RequestParam("user2") Long id1){
        BaseList<?> getConversation=messageService.getConversation(id, id1);
        return baseService.rest(getConversation);
    }

    @PatchMapping("/updateSeen")
    public ResponseEntity<?> update(@RequestParam("senderId") Long senderId, @RequestParam("recieverId") Long recieverId ){
        Base<?> updateResult=messageService.updateStatus(senderId, recieverId);
        return baseService.rest(updateResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable("id") Long id, @RequestParam("senderId") Long senderId){
        Base<?> deleteMessage=messageService.deleteMessage(id, senderId);
        return baseService.rest(deleteMessage);
    }

    @PatchMapping("/messageEdit/{id}")
    public ResponseEntity<?> editMessage(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestBody MessageEdit message, @PathVariable("id") Long id){
        Base<?> messageEdit=messageService.updateMessage(senderId, receiverId,message, id);
        return baseService.rest(messageEdit);
    }

}
