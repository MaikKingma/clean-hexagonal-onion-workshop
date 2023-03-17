package eu.javaland.clean_hexagonal_onion.domaininteraction.publisher;

import eu.javaland.clean_hexagonal_onion.domain.book.Book;
import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookDTO;
import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookDataService;
import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookDomainMapper;
import org.springframework.stereotype.Service;

/**
 * @author Maik Kingma
 */

@Service
public class PublisherFlow {

    private final BookDataService bookDataService;

    public PublisherFlow(BookDataService bookDataService) {
        this.bookDataService = bookDataService;
    }

    public void publishBook(BookDTO bookDTO, PublisherDTO publisherDTO) {
        Book book = BookDomainMapper.mapToDomain(bookDTO);
        if (!book.canBePublished()) {
            throw new BookAlreadyInPublishingException("Book already in publishing!");
        }
        book.requestPublishing(publisherDTO.id());
        BookDTO updatedBookDto = new BookDTO(book);
        bookDataService.save(updatedBookDto);
    }

    static class BookAlreadyInPublishingException  extends RuntimeException{
        public BookAlreadyInPublishingException(String errorMessage) {
            super(errorMessage);
        }
    }
}
