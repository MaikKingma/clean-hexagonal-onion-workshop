package eu.javaland.clean_hexagonal_onion.domain.book;

import eu.javaland.clean_hexagonal_onion.domain.author.Author;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * @author Maik Kingma
 */

@Builder(builderMethodName = "restore")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Book {

    @Getter
    private Long id;

    @Getter
    private Author author;

    @Getter
    private String title;

    @Getter
    private Genre genre;

    @Getter
    private UUID publisherId;

    @Getter
    private boolean published;

    @Getter
    private String isbn;

    public static Book createManuscript(String title, Genre genre, Author author) {
        return new Book(null, author, title, genre, null, false, null);
    }
}
