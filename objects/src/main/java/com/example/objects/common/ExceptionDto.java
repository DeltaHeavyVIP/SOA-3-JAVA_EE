package com.example.objects.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ExceptionDto {
    private String message;
    private LocalDateTime timeStamp;
}
