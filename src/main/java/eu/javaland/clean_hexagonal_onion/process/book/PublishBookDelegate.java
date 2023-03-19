package eu.javaland.clean_hexagonal_onion.process.book;

import eu.javaland.clean_hexagonal_onion.domain.book.Book;
import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookFlow;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Maik Kingma
 */

@Service
public class PublishBookDelegate {
    private final BookFlow bookFlow;

    public PublishBookDelegate(BookFlow bookFlow) {
        this.bookFlow = bookFlow;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void publishBook(Book.RequestPublishingEvent event) {
        bookFlow.requestPublishingAtPublisher(event.getPublisherId(), event.getBookId());
    }
}
