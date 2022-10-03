package com.edu.ulab.app.web.request.Update;

import lombok.Data;

@Data
public class BookRequestUpdate {

    private Long id;
    private String title;
    private String author;
    private long pageCount;
}
