package nl.maikkingma.clean_hexagonal_onion.query.publisher;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.maikkingma.clean_hexagonal_onion.domaininteraction.publisher.PublisherDTO;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.springtest.MockServerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.json.Json;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockServerTest
@SpringBootTest
@AutoConfigureMockMvc
class PublisherQueryTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MockServerClient mockServerClient;

    @Test
    void shouldGetAllPublishers() throws Exception {
        configureMockGetAllPublishers();

        var expectedPublisher1 =
                new PublisherDTO(UUID.fromString("55699ecc-42dc-42cf-8290-1207655140e2"), "the/experts");
        var expectedPublisher2 =
                new PublisherDTO(UUID.fromString("5e659183-411c-42af-8bed-2225a2165c59"), "Heise");
        var expectedPublisher3 =
                new PublisherDTO(UUID.fromString("8fb2e701-b086-415c-bf16-1b3096d355df"), "PubIT");

        // when
        MvcResult result = mockMvc.perform(get("/publishers").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        // then
        var publisherList = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<PublisherDTO>>() {
                });
        assertThat(publisherList).hasSize(3);
        assertThat(publisherList).usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedPublisher1, expectedPublisher2, expectedPublisher3);
    }

    private void configureMockGetAllPublishers() {
        var responseBody = Json.createArrayBuilder()
                .add(Json.createObjectBuilder()
                        .add("id", "55699ecc-42dc-42cf-8290-1207655140e2")
                        .add("name", "the/experts")
                        .add("taxNumber", "VAT12345")
                        .add("numberOfEmployees", 30)
                        .add("yearlyRevenueInMillions", 99)
                        .add("amountOfBooksPublished", 20)
                        .build())
                .add(Json.createObjectBuilder()
                        .add("id", "5e659183-411c-42af-8bed-2225a2165c59")
                        .add("name", "Heise")
                        .add("taxNumber", "VAT32723")
                        .add("numberOfEmployees", 3333)
                        .add("yearlyRevenueInMillions", 432)
                        .add("amountOfBooksPublished", 453)
                        .build())
                .add(Json.createObjectBuilder()
                        .add("id", "8fb2e701-b086-415c-bf16-1b3096d355df")
                        .add("name", "PubIT")
                        .add("taxNumber", "VAT4242111")
                        .add("numberOfEmployees", 56)
                        .add("yearlyRevenueInMillions", 21)
                        .add("amountOfBooksPublished", 4)
                        .build())
                .build().toString();

        mockServerClient.when(request().withMethod("GET").withPath("/publishers"), exactly(1)).respond(
                response()
                        .withStatusCode(200)
                        .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(responseBody)
                        .withDelay(TimeUnit.SECONDS, 1)
        );
    }
}

