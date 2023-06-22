package nl.maikkingma.clean_hexagonal_onion.query.author;

import nl.maikkingma.clean_hexagonal_onion.domain.author.AuthorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Maik Kingma
 */

@RestController
@RequestMapping(value = "/authors")
public class AuthorQueries {

    private final AuthorService authorService;

    public AuthorQueries(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorView> getAll() {
        return authorService.findAll().stream()
                .map(AuthorView::new)
                .collect(toList());
    }
}
