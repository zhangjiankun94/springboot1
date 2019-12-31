package com.seecen.springboot.exception;

import com.seecen.springboot.pojo.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author name
 * @date 2019/12/26 17:04
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /*
    * ExceptionHandler:指定需要处理到的异常类型
    * */
    @ExceptionHandler(NullPointerException.class)
    public String exception(Exception e){
        log.error("全局异常处理，捕获到空指针异常",e);
        return "error/nullPoint.html";
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity validationErrorHandler(MethodArgumentNotValidException ex) {
        // 同样是获取BindingResult对象，然后获取其中的错误信息
        // 如果前面开启了fail_fast，事实上这里只会有一个信息
        //如果没有，则可能又多个
        List<String> errorInformation = ex.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());

        return new ResponseEntity(
                new ResultInfo<>(400, errorInformation.toString(), null),
                HttpStatus.BAD_REQUEST);
    }
}
