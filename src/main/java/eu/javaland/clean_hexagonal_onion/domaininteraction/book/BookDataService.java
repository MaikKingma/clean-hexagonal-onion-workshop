package eu.javaland.clean_hexagonal_onion.domaininteraction.book;

/**
 * @author Maik Kingma
 */

public interface BookDataService {
    void save(BookDTO bookDTO);
}
