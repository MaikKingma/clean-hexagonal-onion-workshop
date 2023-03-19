package eu.javaland.clean_hexagonal_onion.command.book;

import eu.javaland.clean_hexagonal_onion.domaininteraction.publisher.PublisherFlow;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Maik Kingma
 */

@RestController
@RequestMapping("books/{id}/commands")
public class BookCommands {

    private final PublisherFlow publisherFlow;

    public BookCommands(PublisherFlow publisherFlow) {
        this.publisherFlow = publisherFlow;
    }

    @PostMapping("/publish")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void publishBook(@PathVariable("id") Long bookId, @RequestBody PublishBookPayload publishBookPayload) {
        publisherFlow.publishBook(bookId, publishBookPayload.publisherId());
    }
}
