package kr.gg.lol.common.exception;

import static kr.gg.lol.common.util.ApiUtils.*;

import io.swagger.models.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import static kr.gg.lol.common.util.ApiUtils.error;

@ControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> handleHttpClientErrorException(Exception e){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.BAD_GATEWAY);
    }
}
