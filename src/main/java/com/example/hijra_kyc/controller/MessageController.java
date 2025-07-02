package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.ConversationRequestDto;
import com.example.hijra_kyc.dto.MessageInDto;
import com.example.hijra_kyc.model.Base;
import com.example.hijra_kyc.model.BaseList;
import com.example.hijra_kyc.model.Message;
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
        Base<?> save=messageService.save(message);
        return baseService.rest(save);
    }

    @PostMapping("/getConvo")
    public ResponseEntity<?> getMessages(@RequestBody ConversationRequestDto dto){
        BaseList<?> getConversation=messageService.getConversation(dto.getUser1(), dto.getUser2());
        return baseService.rest(getConversation);
    }

}
