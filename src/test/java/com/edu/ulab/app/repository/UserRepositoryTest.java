package com.edu.ulab.app.repository;

import com.edu.ulab.app.config.SystemJpaTest;
import com.edu.ulab.app.entity.Person;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import static com.vladmihalcea.sql.SQLStatementCountValidator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Тесты репозитория {@link UserRepository}.
 */
@SystemJpaTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        SQLStatementCountValidator.reset();
    }

    @DisplayName("Сохранить юзера. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void insertPerson_thenAssertDmlCount() {
        //Given
        Person person = new Person();
        person.setAge(111);
        person.setTitle("reader");
        person.setFullName("Test Test");

        //When
        Person result = userRepository.save(person);

        //Then
        assertThat(result.getAge()).isEqualTo(111);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }

    // update
    @DisplayName("Изменить юзера. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void updatePerson_thenAssertDmlCount() {
        //Given
        Person person = new Person();
        person.setId(1001L);
        person.setAge(18);
        person.setTitle("reader");
        person.setFullName("soymun");

        //When
        Person result = userRepository.save(person);

        //Then
        assertThat(result.getAge()).isEqualTo(18);
        assertThat(result.getFullName()).isEqualTo("soymun");
        assertThat(result.getId()).isEqualTo(1001L);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }
    // get
    @DisplayName("Выдать юзера. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void getPerson_thenAssertDmlCount() {
        //Given
        Long personId = 1001L;
        //When
        Person result = userRepository.findById(personId).orElse(new Person());

        //Then
        assertThat(result.getAge()).isEqualTo(55);
        assertThat(result.getFullName()).isEqualTo("default uer");
        assertThat(result.getId()).isEqualTo(1001L);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }
    // delete
    @DisplayName("Удалить юзера. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void deletePerson_thenAssertDmlCount() {
        //Given
        Long personId = 1001L;
        //When
        userRepository.deleteById(personId);
        Person result = userRepository.findById(personId).orElse(new Person());
        //Then
        assertThat(result.getAge()).isEqualTo(0);
        assertThat(result.getFullName()).isEqualTo(null);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }
    // * failed
    @DisplayName("Ошибка при выдаче пользователя. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void failedGetPerson_thenAssertDmlCount() {
        //Given
        Long personId = 1000L;
        //When
        Person result = userRepository.findById(personId).orElse(null);

        //Then
        assertThatThrownBy(() -> result.getAge())
                .isInstanceOf(NullPointerException.class);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }
}
