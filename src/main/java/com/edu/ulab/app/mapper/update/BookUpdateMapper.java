package com.edu.ulab.app.mapper.update;


import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.web.response.update.BookRequestUpdate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookUpdateMapper {

    BookDto bookRequestUpdateToBookDto(BookRequestUpdate bookRequestUpdate);

    BookRequestUpdate bookDtoToBookRequestUpdate(BookDto bookDto);
}
