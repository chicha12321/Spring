package com.edu.ulab.app.web.response.update;

import lombok.Data;

@Data
public class BookRequestUpdate {

    private Long id;
    private String title;
    private String author;
    private long pageCount;
}
