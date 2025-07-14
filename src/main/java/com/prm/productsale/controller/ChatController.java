package com.prm.productsale.controller;

import com.prm.productsale.dto.ChatMessDTO;
import com.prm.productsale.dto.response.BaseResponse;
import com.prm.productsale.services.ChatMessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/mobile/chatMess")
@CrossOrigin
@Tag(name = "Chat Message API", description = "API for Chatting")
public class ChatController {
    @Autowired
    private ChatMessService chatMessService;
    @Operation(summary = "get all chat")
    @GetMapping
    public ResponseEntity<?> getAllMessages() {
        return ResponseEntity.ok(BaseResponse.getResponse("Success",chatMessService.getAllMessages()));
    }
    @Operation(summary = "send Message")
    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody ChatMessDTO dto) {
        return ResponseEntity.ok(BaseResponse.getResponse("Success",chatMessService.saveMessage(dto)));
    }
}
