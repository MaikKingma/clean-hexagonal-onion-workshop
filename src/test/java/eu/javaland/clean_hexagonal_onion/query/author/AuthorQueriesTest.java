package eu.javaland.clean_hexagonal_onion.query.author;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.javaland.clean_hexagonal_onion.data.author.AuthorJPA;
import eu.javaland.clean_hexagonal_onion.data.author.AuthorRepository;
import eu.javaland.clean_hexagonal_onion.domain.author.Author;
import eu.javaland.clean_hexagonal_onion.domaininteraction.author.AuthorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorQueriesTest {

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
    void getAll() throws Exception {
        // given
        var authorJPA = AuthorJPA.builder().firstName("firstName").lastName("lastName").build();
        authorRepository.save(authorJPA);
        authorRepository.flush();
        var expected = new AuthorView(new AuthorDTO("firstName", "lastName"));
        // when
        MvcResult result = mockMvc.perform(get("/authors").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        var resultingAuthorViews = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<AuthorView>>() { });
        // then
        assertThat(resultingAuthorViews)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactly(expected);
    }
}
