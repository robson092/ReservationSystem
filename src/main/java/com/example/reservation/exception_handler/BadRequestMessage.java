package com.example.reservation.exception_handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BadRequestMessage {
    private LocalDateTime time;
    private String message;

}
