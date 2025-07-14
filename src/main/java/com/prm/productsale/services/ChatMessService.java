package com.prm.productsale.services;

import com.prm.productsale.dto.ChatMessDTO;
import com.prm.productsale.entity.ChatMessEntity;
import com.prm.productsale.entity.UserEntity;
import com.prm.productsale.repository.ChatMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatMessService {
    @Autowired
    private ChatMessageRepo chatMessageRepo;
    @Autowired
    private LoginServices loginServices;
    public List<ChatMessDTO> getAllMessages() {
        return chatMessageRepo.findAllByOrderBySentDateAsc()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ChatMessDTO toDTO(ChatMessEntity entity) {
        ChatMessDTO dto = new ChatMessDTO();
        dto.setMess(entity.getMess());
        dto.setSentDate(entity.getSentDate());
        return dto;
    }

    public ChatMessDTO saveMessage(ChatMessDTO dto) {
        ChatMessEntity entity = new ChatMessEntity();
        entity.setMess(dto.getMess());
        entity.setSentDate(dto.getSentDate());
        entity.setUser(loginServices.getUser());
        ChatMessEntity saved = chatMessageRepo.save(entity);
        return toDTO(saved);
    }

}
