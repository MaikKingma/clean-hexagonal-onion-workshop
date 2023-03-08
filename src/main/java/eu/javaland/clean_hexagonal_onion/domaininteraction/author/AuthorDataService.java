package eu.javaland.clean_hexagonal_onion.domaininteraction.author;

import eu.javaland.clean_hexagonal_onion.domain.author.Author;

import java.util.List;

public interface AuthorDataService {
    void save(Author author);

    List<Author> findAll();
}
