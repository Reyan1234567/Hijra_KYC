package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.MessageDto.MessageEdit;
import com.example.hijra_kyc.dto.MessageDto.MessageInDto;
import com.example.hijra_kyc.dto.MessageDto.MessageMapperDto;
import com.example.hijra_kyc.dto.MessageDto.MessageOutDto;
import com.example.hijra_kyc.mapper.UserPrincipal;
import com.example.hijra_kyc.service.BaseService;
import com.example.hijra_kyc.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final BaseService baseService;

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody MessageInDto message){
        MessageOutDto save=messageService.saveMessage(message);
        return ResponseEntity.ok(save);
    }

    @PreAuthorize("#id==authentication.principal.userId||#id1==authentication.principal.userId")
    @GetMapping("/getConvo")
    public ResponseEntity<?> getMessages(@RequestParam("user1") Long id, @RequestParam("user2") Long id1){
        List<MessageOutDto> getConversation=messageService.getConversation(id, id1);
        return ResponseEntity.ok(getConversation);
    }

    @PatchMapping("/updateSeen")
    public ResponseEntity<?> update(@RequestParam("senderId") Long senderId, @RequestParam("recieverId") Long recieverId ){
        String updateResult=messageService.updateStatus(senderId, recieverId);
        return ResponseEntity.ok(updateResult);
    }

    @PatchMapping("/messageEdit/{id}")
    public ResponseEntity<?> editMessage(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestBody MessageEdit message, @PathVariable("id") Long id){
        MessageOutDto messageEdit=messageService.updateMessage(senderId, receiverId,message, id);
        return ResponseEntity.ok(messageEdit);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUserMessages(@RequestParam("receiverId") Long receiverId){
        List<MessageMapperDto> messages=messageService.getAllUsers(receiverId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/unread")
    public ResponseEntity<?> getUnreadMessages(){
        UserPrincipal principal= (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer unread=messageService.getUnreadMessages(principal);
        return ResponseEntity.ok(unread);
    }



//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteMessage(@PathVariable("id") int id, @RequestParam("senderId") int senderId){
//        Base<?> deleteMessage=messageService.deleteMessage(id, senderId);
//        return baseService.rest(deleteMessage);
//    }
}
