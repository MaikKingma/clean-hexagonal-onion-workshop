package eu.javaland.clean_hexagonal_onion.query.book;

import eu.javaland.clean_hexagonal_onion.domaininteraction.author.AuthorDTO;
import eu.javaland.clean_hexagonal_onion.domaininteraction.author.AuthorDataService;
import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookDTO;
import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Maik Kingma
 */

@RestController
@RequestMapping(value = "/books")
public class BookQueries {

    private final BookDataService bookDataService;

    private final AuthorDataService authorDataService;

    public BookQueries(BookDataService bookDataService, AuthorDataService authorDataService) {
        this.bookDataService = bookDataService;
        this.authorDataService = authorDataService;
    }

    @GetMapping
    public List<BookView> findBooks(@RequestParam(value = "title", required = false) String title) {
        if(title == null || title.isEmpty()) {
            return bookDataService.findAll().stream()
                    .map(bookDTO -> new BookView(bookDTO, getBookAuthor(bookDTO)))
                    .toList();
        } else {
            return bookDataService.findByPartialTitle(title).stream()
                    .map(bookDTO -> new BookView(bookDTO, getBookAuthor(bookDTO)))
                    .toList();
        }
    }

    private AuthorDTO getBookAuthor(BookDTO book) {
        return authorDataService.findById(book.authorDTO().id());
    }
}