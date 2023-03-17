package eu.javaland.clean_hexagonal_onion.data.book;

import eu.javaland.clean_hexagonal_onion.data.author.AuthorJPAMapper;
import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookDTO;

public class BookJPAMapper {
    static BookJPA mapToJPA(BookDTO bookDTO) {
        BookJPA bookJPA = BookJPA.builder()
                .id(bookDTO.id())
                .title(bookDTO.title())
                .author(AuthorJPAMapper.mapToJPA(bookDTO.authorDTO()))
                .genre(bookDTO.genre())
                .isbn(bookDTO.isbn())
                .published(bookDTO.published())
                .publisherId(bookDTO.publisherId())
                .build();
        bookJPA.registerDomainEvents(bookDTO.domainEvents());
        return bookJPA;
    }
}
