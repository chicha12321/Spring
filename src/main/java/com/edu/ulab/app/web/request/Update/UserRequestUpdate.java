package com.edu.ulab.app.web.request.Update;

import lombok.Data;

@Data
public class UserRequestUpdate {
    private Long id;
    private String fullName;
    private String title;
    private int age;
}
