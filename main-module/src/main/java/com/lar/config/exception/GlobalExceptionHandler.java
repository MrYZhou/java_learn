package com.lar.config.exception;

import com.lar.enums.AppResultCode;
import com.lar.vo.AppResult;
import org.noear.snack.ONode;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestController
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AppResult<Object> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> map = new HashMap<>(16);
        String message = e.getMessage();
        String json = ONode.stringify(message);
        AppResult<Object> result = AppResult.fail(AppResultCode.ValidateError.getCode(), json);
        return result;
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public AppResult runTimeException(RuntimeException e) {
        Map<String, String> map = new HashMap<>(16);
        String message = e.getMessage();
        String json = ONode.stringify(message);

        AppResult result = AppResult.fail(AppResultCode.ValidateError.getCode(), json);
        return result;
    }
}
