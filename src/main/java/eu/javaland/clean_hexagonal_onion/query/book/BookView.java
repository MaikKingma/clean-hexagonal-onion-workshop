package eu.javaland.clean_hexagonal_onion.query.book;

import eu.javaland.clean_hexagonal_onion.domaininteraction.author.AuthorDTO;
import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookDTO;

/**
 * @author Maik Kingma
 */

public record BookView(String title, String genre, String authorName) {
    public BookView(BookDTO bookDTO, AuthorDTO authorDTO) {
        this(bookDTO.title(), bookDTO.genre(), authorDTO.getFullName());
    }
}
