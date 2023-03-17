package eu.javaland.clean_hexagonal_onion.query.author;

import eu.javaland.clean_hexagonal_onion.domain.author.Author;
import eu.javaland.clean_hexagonal_onion.domaininteraction.author.AuthorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Maik Kingma
 */

public record AuthorView (Long id, String name) {
    public AuthorView(AuthorDTO authorDTO) {
        this(author.getId(), author.getFullName());
    }
}
