package nl.maikkingma.clean_hexagonal_onion.command.author;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import nl.maikkingma.clean_hexagonal_onion.data.author.AuthorJPA;
import nl.maikkingma.clean_hexagonal_onion.data.author.AuthorRepository;
import nl.maikkingma.clean_hexagonal_onion.data.book.BookJPA;
import nl.maikkingma.clean_hexagonal_onion.data.book.BookRepository;
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
class AuthorActionCommandsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void beforeEach() {
        entityManager.createNativeQuery("DELETE FROM author where true; DELETE FROM book where true;")
                .executeUpdate();
    }

    @Test
    @Transactional
    void registerAndGet() throws Exception {
        //given
        var registerAuthorPayloadJson = objectMapper.writeValueAsString(new RegisterAuthorPayload("firstName", "lastName"));
        var expected = AuthorJPA.builder().firstName("firstName").lastName("lastName").build();
        //when
        mockMvc.perform(post("/authors/commands/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerAuthorPayloadJson))
                .andExpect(status().isAccepted());
        authorRepository.flush();
        // then
        assertThat(authorRepository.findAll().size()).isEqualTo(1);
        assertThat(authorRepository.findAll().get(0)).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
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
