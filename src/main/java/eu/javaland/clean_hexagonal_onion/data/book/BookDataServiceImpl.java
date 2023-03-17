package eu.javaland.clean_hexagonal_onion.data.book;

import eu.javaland.clean_hexagonal_onion.domaininteraction.author.AuthorDTO;
import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookDTO;
import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Maik Kingma
 */

@Slf4j
@Service
public class BookDataServiceImpl implements BookDataService {

    private final BookRepository bookRepository;

    public BookDataServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void save(BookDTO bookDTO) {
        bookRepository.save(BookJPAMapper.mapToJPA(bookDTO));
    }

    @Override
    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(this::getDTOFromJPA)
                .toList();
    }

    @Override
    public List<BookDTO> findByPartialTitle(String title) {
        return bookRepository.findByTitleContains(title).stream()
                .map(this::getDTOFromJPA)
                .toList();
    }

    private BookDTO getDTOFromJPA(BookJPA bookJPA) {
        var authorJPA = bookJPA.getAuthor();
        var authorDTO = new AuthorDTO(authorJPA.getId(), authorJPA.getFirstName(), authorJPA.getLastName());
        return new BookDTO(bookJPA.getId(), authorDTO, bookJPA.getTitle(), bookJPA.getGenre(),
                bookJPA.getPublisherId(), bookJPA.isPublished(), bookJPA.getIsbn());
    }
}
