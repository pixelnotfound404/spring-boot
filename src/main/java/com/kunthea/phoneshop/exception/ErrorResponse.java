package com.kunthea.phoneshop.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@AllArgsConstructor
public class ErrorResponse {
    private HttpStatus status;
    private String message;
}
