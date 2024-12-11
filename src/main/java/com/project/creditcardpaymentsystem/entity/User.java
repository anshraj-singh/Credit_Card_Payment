package com.project.creditcardpaymentsystem.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private List<String> roles;
    private boolean isActive;
    private String customerId; // Only include customerId
}