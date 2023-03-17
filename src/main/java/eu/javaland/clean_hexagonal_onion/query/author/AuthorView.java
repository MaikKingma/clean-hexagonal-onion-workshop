package eu.javaland.clean_hexagonal_onion.query.author;

import eu.javaland.clean_hexagonal_onion.domaininteraction.author.AuthorDTO;

/**
 * @author Maik Kingma
 */

public record AuthorView (Long id, String name) {
    public AuthorView(AuthorDTO authorDTO) {
        this(authorDTO.id(), authorDTO.getFullName());
    }
}
