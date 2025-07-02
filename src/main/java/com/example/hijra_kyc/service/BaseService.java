package com.example.hijra_kyc.service;

import com.example.hijra_kyc.model.Base;
import com.example.hijra_kyc.model.BaseList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseService {

    public Base<?> success(Object o) {
        return new Base<>(o, true, "Success!");
    }

    public Base<?> error(){
        return new Base<>(null, false, "Unknown Error Occured!");
    }

    public Base<?> error(String message){
        return new Base<>(null, false, message);
    }

    public BaseList<?> ListSuccess(List<?> list){
        return new BaseList<>(list, true, list.size(), "Success!");
    }

    public BaseList<?> success(List<?> list, Long count){
        return new BaseList<>(list, true, count, "Success!");
    }

    public BaseList<?> listError(){
        return new BaseList<>(null, false, 0, "List is empty");
    }

    public BaseList<?> listError(String message){
        return new BaseList<>(null, false, 0, message);
    }

    public ResponseEntity<?> rest(Base<?> base){
        return new ResponseEntity<>(base, base.isStatus()? HttpStatus.OK: HttpStatus.CONFLICT);
    }

    public ResponseEntity<?> rest(BaseList<?> list){
        return new ResponseEntity<>(list, list.isStatus()? HttpStatus.OK: HttpStatus.CONFLICT);
    }
}
