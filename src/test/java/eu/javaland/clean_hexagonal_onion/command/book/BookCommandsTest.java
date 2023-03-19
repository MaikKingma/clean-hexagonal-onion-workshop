package eu.javaland.clean_hexagonal_onion.command.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.springtest.MockServerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.json.Json;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockServerTest
@SpringBootTest
@AutoConfigureMockMvc
class BookCommandsTest {

    private static final Long BOOK_ID = 1L;
    private static final Long AUTHOR_ID = 2L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    private MockServerClient mockServerClient;

    @BeforeEach
    void beforeAll() {
        entityManager.createNativeQuery("DELETE FROM author where true; DELETE FROM book where true;")
                .executeUpdate();
    }

    @Test
    @Transactional
    void publishBook() throws Exception {
        // given
        UUID publisherUUID = UUID.randomUUID();
        configureMockGetPublisherById(publisherUUID.toString());
        entityManager.createNativeQuery(
                        "INSERT INTO author (id, first_name, last_name) VALUES (?,?,?)")
                .setParameter(1, AUTHOR_ID)
                .setParameter(2, "firstName")
                .setParameter(3, "lastName")
                .executeUpdate();

        entityManager.createNativeQuery(
                        "INSERT INTO book (id, title, author_id, genre, published, publisher_id, isbn) " +
                                "VALUES (?,?,?,?,?,?,?)")
                .setParameter(1, BOOK_ID)
                .setParameter(2, "title")
                .setParameter(3, AUTHOR_ID)
                .setParameter(4, "HORROR")
                .setParameter(5, false)
                .setParameter(6, null)
                .setParameter(7, null)
                .executeUpdate();

        entityManager.flush();

        var publishBookPayload = objectMapper.writeValueAsString(new PublishBookPayload(publisherUUID.toString()));
        // when
        mockMvc.perform(post(String.format("/books/%d/commands/publish", BOOK_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(publishBookPayload))
                .andExpect(status().isAccepted());
    }

    private void configureMockGetPublisherById(String publisherId) {
        var responseBody = Json.createObjectBuilder()
                .add("id", publisherId)
                .add("name", "the/experts")
                .add("taxNumber", "VAT12345")
                .add("numberOfEmployees", 30)
                .add("yearlyRevenueInMillions", 99)
                .add("amountOfBooksPublished", 20)
                .build().toString();

        mockServerClient.when(request().withMethod("GET").withPath("/publishers/" + publisherId), exactly(1)).respond(
                response()
                        .withStatusCode(200)
                        .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(responseBody)
                        .withDelay(TimeUnit.SECONDS,1)
        );
    }
}