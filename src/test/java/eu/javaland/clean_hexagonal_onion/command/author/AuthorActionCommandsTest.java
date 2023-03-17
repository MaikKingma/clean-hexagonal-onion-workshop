package eu.javaland.clean_hexagonal_onion.command.author;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.javaland.clean_hexagonal_onion.data.author.AuthorJPA;
import eu.javaland.clean_hexagonal_onion.data.book.BookJPA;
import eu.javaland.clean_hexagonal_onion.data.book.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorActionCommandsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void beforeAll() {
        entityManager.createNativeQuery("DELETE FROM author where true; DELETE FROM book where true;")
                .executeUpdate();
    }

    @Test
    @Transactional
    void writeBook() throws Exception {
        // given
        entityManager.createNativeQuery(
                        "INSERT INTO author (id, first_name, last_name) VALUES (?,?,?)")
                .setParameter(1, 1)
                .setParameter(2, "firstName")
                .setParameter(3, "lastName")
                .executeUpdate();

        var writeBookDTOJson = objectMapper.writeValueAsString(new WriteBookPayload("title", "CRIME"));
        var expected = BookJPA.builder()
                .title("title")
                .genre("CRIME")
                .published(false)
                .author(AuthorJPA.builder().id(1L).firstName("firstName").lastName("lastName").build())
                .build();
        // when
        mockMvc.perform(post("/authors/1/commands/writeBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writeBookDTOJson))
                .andExpect(status().isAccepted());
        entityManager.flush();
        // then
        List<BookJPA> books = bookRepository.findAll();
        assertThat(books.size()).isEqualTo(1);
        assertThat(books.get(0)).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }
}