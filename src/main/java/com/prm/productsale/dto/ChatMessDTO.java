package com.prm.productsale.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessDTO {

    private String mess;
    private LocalDateTime sentDate;


}
