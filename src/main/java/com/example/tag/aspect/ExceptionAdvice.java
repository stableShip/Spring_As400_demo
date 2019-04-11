package com.example.tag.aspect;

import com.example.tag.exception.BaseException;
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
