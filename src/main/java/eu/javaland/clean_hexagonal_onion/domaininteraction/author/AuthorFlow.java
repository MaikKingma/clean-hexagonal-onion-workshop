package eu.javaland.clean_hexagonal_onion.domaininteraction.author;

import eu.javaland.clean_hexagonal_onion.domain.author.Author;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorFlow {

    private final AuthorDataService authorDataService;

    public AuthorFlow(AuthorDataService authorDataService) {
        this.authorDataService = authorDataService;
    }

    public void registerAuthorByName(String firstName, String lastName) {
        Author author = Author.create(firstName, lastName);
        authorDataService.save(author);
    }

    public List<AuthorDTO> getListOfAllAuthors() {
        return authorDataService.findAll().stream()
                .map(author -> new AuthorDTO(author.getFirstName(), author.getLastName()))
                .collect(Collectors.toList());
    }
}
