package nl.maikkingma.clean_hexagonal_onion.command.author;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorCommandsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void beforeAll() {
        authorRepository.deleteAll();
    }

    @Test
    void registerAndGet() throws Exception {
        //given
        var registerAuthorDTOJson = objectMapper.writeValueAsString(new RegisterAuthorDTO("firstName", "lastName"));
        var expected = AuthorJPA.builder().firstName("firstName").lastName("lastName").build();
        //when
        mockMvc.perform(post("/authors/commands/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerAuthorDTOJson))
                .andExpect(status().isAccepted());
        authorRepository.flush();
        // then
        assertThat(authorRepository.findAll().size()).isEqualTo(1);
        assertThat(authorRepository.findAll().get(0)).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }
}
