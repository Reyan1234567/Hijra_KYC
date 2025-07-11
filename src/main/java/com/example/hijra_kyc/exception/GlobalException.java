//package com.example.hijra_kyc.exception;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice
//public class GlobalException {
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
//        var errors=new HashMap<String, String>();
//        exception.getBindingResult().getAllErrors().forEach((error)->{errors.put(((FieldError)error).getField(),error.getDefaultMessage());});
//        return ResponseEntity.badRequest().body(errors);
//    }
//}
//
//
//
////exception.getBindingResult():- gives a BindingResult object, which has a method getAllErrors() that returns
////List<ObjectError>
////ObjectError is the parent of FieldError
////we cast it to FieldErrors and iteratively put it to the map we declared, extracting the name and the value of the validation error