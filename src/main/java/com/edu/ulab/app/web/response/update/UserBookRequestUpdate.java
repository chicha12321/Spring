package com.edu.ulab.app.web.response.update;

import lombok.Data;

import java.util.List;

@Data
public class UserBookRequestUpdate {
    private UserRequestUpdate userRequest;
    private List<BookRequestUpdate> bookRequests;
}
