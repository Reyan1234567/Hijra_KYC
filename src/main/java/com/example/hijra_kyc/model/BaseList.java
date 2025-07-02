package com.example.hijra_kyc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseList<T> {
    private List<T> list;
    private boolean status;
    private long count;
    private String message;
}
