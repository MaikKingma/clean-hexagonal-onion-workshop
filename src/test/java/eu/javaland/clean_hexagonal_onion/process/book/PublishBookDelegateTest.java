package eu.javaland.clean_hexagonal_onion.process.book;

import eu.javaland.clean_hexagonal_onion.domain.book.Book;
import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookDTO;
import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookDataService;
import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookFlow;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.springtest.MockServerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.json.Json;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@MockServerTest
@SpringBootTest
class PublishBookDelegateTest {

    private static final Long BOOK_ID = 1L;
    private static final Long AUTHOR_ID = 2L;

    @Autowired
    private BookFlow bookFlow;

    @Autowired
    private BookDataService bookDataService;

    private MockServerClient mockServerClient;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void shouldCallThePublisherServiceAPIWithCorrectPayload() {
        PublishBookDelegate publishBookDelegate = new PublishBookDelegate(bookFlow);
        UUID publisherUUID = UUID.randomUUID();
        UUID isbnUUID = UUID.randomUUID();
        configureMockPublishersReceiveBookOffer(isbnUUID.toString());

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
        BookDTO checkBook = bookDataService.findById(BOOK_ID);
        assertThat(checkBook.published()).isFalse();
        assertThat(checkBook.isbn()).isNull();
        // when
        publishBookDelegate.publishBook(new Book.RequestPublishingEvent(BOOK_ID, publisherUUID));
        // then
        mockServerClient.verify(request()
                .withPath("/publishers/receiveBookOffer")
                .withMethod("POST")
                .withBody(Json.createObjectBuilder()
                        .add("publisherId", publisherUUID.toString())
                        .add("author", "firstName lastName")
                        .add("title", "title")
                        .build().toString()));
        BookDTO resultBook = bookDataService.findById(BOOK_ID);
        assertThat(resultBook.published()).isTrue();
        assertThat(resultBook.isbn()).isEqualTo(String.format("ISBN-%s", isbnUUID));

    }

    private void configureMockPublishersReceiveBookOffer(String uuid) {
        var responseBody = Json.createObjectBuilder()
                .add("isbn", String.format("ISBN-%s", uuid))
                .build().toString();

        mockServerClient.when(request().withMethod("POST").withPath("/publishers/receiveBookOffer"), exactly(1)).respond(
                response()
                        .withStatusCode(202)
                        .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody(responseBody)
                        .withDelay(TimeUnit.SECONDS,1)
        );
    }
}