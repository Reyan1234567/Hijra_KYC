package com.example.hijra_kyc.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class userMessage {
    Long id;
    String firstName;
    String lastName;
    String roleName;
    String branchName;
    Integer status;
    String photoUrl;
    Long count;
}
