package eu.javaland.clean_hexagonal_onion.command.author;

import eu.javaland.clean_hexagonal_onion.domaininteraction.author.AuthorFlow;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("authors/{id}/commands")
public class AuthorActionCommands {

    private final AuthorFlow authorFlow;

    public AuthorActionCommands(AuthorFlow authorFlow) {
        this.authorFlow = authorFlow;
    }

    @PostMapping("/writeBook")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void writeBook(@PathVariable("id") Long authorId, @RequestBody WriteBookPayload writeBookPayload) {
        authorFlow.writeManuscript(authorId, writeBookPayload.title(), writeBookPayload.genre());
    }
}
