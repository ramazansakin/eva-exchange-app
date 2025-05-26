package com.trading.platform.evaexchange.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {

    private String error;
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private String path;

}
