package com.edu.ulab.app.web.response.update;

import lombok.Data;

@Data
public class UserRequestUpdate {
    private Long id;
    private String fullName;
    private String title;
    private int age;
}
