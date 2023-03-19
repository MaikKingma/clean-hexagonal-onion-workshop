package eu.javaland.clean_hexagonal_onion.domaininteraction.publisher;

import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookDTO;
import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookDataService;
import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookDomainMapper;
import org.springframework.stereotype.Service;

/**
 * @author Maik Kingma
 */

@Service
public class PublisherFlow {

    private final PublisherAppService publisherAppService;
    private final BookDataService bookDataService;

    public PublisherFlow(PublisherAppService publisherAppService, BookDataService bookDataService) {
        this.publisherAppService = publisherAppService;
        this.bookDataService = bookDataService;
    }

    public void publishBook(Long bookId, String publisherId) {
        var publisherDTO = publisherAppService.getPublisherById(publisherId);
        var bookDTO = bookDataService.findById(bookId);
        var book = BookDomainMapper.mapToDomain(bookDTO);

        if (!book.canBePublished()) {
            throw new BookAlreadyInPublishingException("Book already in publishing!");
        }
        book.requestPublishing(publisherDTO.id());
        var updatedBookDto = new BookDTO(book);
        bookDataService.save(updatedBookDto);
    }

    static class BookAlreadyInPublishingException  extends RuntimeException{
        public BookAlreadyInPublishingException(String errorMessage) {
            super(errorMessage);
        }
    }
}
