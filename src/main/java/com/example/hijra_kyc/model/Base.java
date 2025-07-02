package com.example.hijra_kyc.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Base<T> {
    private T t;
    private boolean status;
    private  String message;
}
