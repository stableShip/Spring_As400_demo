package com.example.tag.aspect;

import com.example.tag.exception.BaseException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map errorHandler(Exception ex) {
        ex.printStackTrace();
        Map map = new HashMap();
        map.put("code", 100);
        map.put("msg", "系统错误");
        return map;
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map map = new HashMap();
        map.put("code", 300);
        Map data = new HashMap();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            data.put(fieldName, errorMessage);
        });
        map.put("data", data);
        return map;
    }

    /**
     * Catch BaseException.class
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BaseException.class)
    public Map myErrorHandler(BaseException ex) {
        Map map = new HashMap();
        map.put("code", ex.getCode());
        map.put("msg", ex.getMsg());
        return map;
    }
}
