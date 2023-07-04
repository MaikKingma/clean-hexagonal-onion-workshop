package nl.maikkingma.clean_hexagonal_onion.domaininteraction.author;

import nl.maikkingma.clean_hexagonal_onion.domain.author.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorFlow {

    private final AuthorDataService authorDataService;

    public AuthorFlow(AuthorDataService authorDataService) {
        this.authorDataService = authorDataService;
    }

    public void registerAuthorByName(String firstName, String lastName) {
        Author author = Author.create(firstName, lastName);
        authorDataService.save(new AuthorDTO(author));
    }

    public List<AuthorDTO> getListOfAllAuthors() {
        return authorDataService.findAll();
    }
}
