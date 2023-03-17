package eu.javaland.clean_hexagonal_onion.domaininteraction.book;

import eu.javaland.clean_hexagonal_onion.domain.DomainEvent;
import eu.javaland.clean_hexagonal_onion.domain.book.Book;
import eu.javaland.clean_hexagonal_onion.domaininteraction.author.AuthorDTO;

import java.util.List;
import java.util.UUID;

/**
 * @author Maik Kingma
 */

public record BookDTO(Long id, AuthorDTO authorDTO, String title, String genre, UUID publisherId, boolean published,
                      String isbn, List<DomainEvent> domainEvents) {
    public BookDTO(Book book) {
        this(book.getId(), new AuthorDTO(book.getAuthor()), book.getTitle(), book.getGenre().toString(),
                book.getPublisherId(), book.isPublished(), book.getIsbn(), book.getDomainEvents());
    }
}
