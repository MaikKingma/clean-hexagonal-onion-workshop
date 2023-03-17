package eu.javaland.clean_hexagonal_onion.command.book;

import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookDTO;
import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookDataService;
import eu.javaland.clean_hexagonal_onion.domaininteraction.publisher.PublisherAppService;
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

    private final PublisherAppService publisherAppService;

    private final BookDataService bookDataService;

    public BookCommands(PublisherAppService publisherAppService, BookDataService bookDataService) {
        this.publisherAppService = publisherAppService;
        this.bookDataService = bookDataService;
    }

    @PostMapping("/publish")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void publishBook(@PathVariable("id") Long bookId, @RequestBody PublishBookPayload publishBookPayload) {
        var publisher = publisherAppService.getPublisherById(publishBookPayload.publisherId());
        BookDTO bookDTO = bookDataService.findById(bookId);

//        if (!bookDTO.()) {
//            throw new BookService.BookAlreadyInPublishingException("Book already in publishing!");
//        }

    }
}
