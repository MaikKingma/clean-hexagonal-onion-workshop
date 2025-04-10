package nl.maikkingma.clean_hexagonal_onion.data.book;

import nl.maikkingma.clean_hexagonal_onion.data.author.AuthorJPAMapper;
import nl.maikkingma.clean_hexagonal_onion.domaininteraction.book.BookDTO;

public class BookJPAMapper {
    static BookJPA mapToJPA(BookDTO bookDTO) {
        return BookJPA.builder()
                .id(bookDTO.id())
                .title(bookDTO.title())
                .author(AuthorJPAMapper.mapToJPA(bookDTO.authorDTO()))
                .genre(bookDTO.genre())
                .isbn(bookDTO.isbn())
                .published(bookDTO.published())
                .publisherId(bookDTO.publisherId())
                .build();
    }
}
