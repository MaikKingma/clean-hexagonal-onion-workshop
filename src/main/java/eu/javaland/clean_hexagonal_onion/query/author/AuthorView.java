package eu.javaland.clean_hexagonal_onion.query.author;

import eu.javaland.clean_hexagonal_onion.domain.author.Author;

/**
 * @author Maik Kingma
 */

public record AuthorView (Long id, String name) {
    public AuthorView(Author author) {
        this(author.getId(), author.getFullName());
    }
}
