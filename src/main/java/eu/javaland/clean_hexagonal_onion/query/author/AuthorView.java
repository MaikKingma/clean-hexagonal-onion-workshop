package eu.javaland.clean_hexagonal_onion.query.author;

import eu.javaland.clean_hexagonal_onion.domain.author.Author;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Maik Kingma
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorView {
    public AuthorView(Author author) {
        this.id = author.getId();
        this.name = author.getFullName();
    }

    Long id;
    String name;
}
