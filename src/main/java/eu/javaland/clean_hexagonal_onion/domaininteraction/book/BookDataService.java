package eu.javaland.clean_hexagonal_onion.domaininteraction.book;

import java.util.List;

/**
 * @author Maik Kingma
 */

public interface BookDataService {
    void save(BookDTO bookDTO);

    List<BookDTO> findAll();

    List<BookDTO> findByPartialTitle(String title);

    BookDTO findById(Long bookId);
}
