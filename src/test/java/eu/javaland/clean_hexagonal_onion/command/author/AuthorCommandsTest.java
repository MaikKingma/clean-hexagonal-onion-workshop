package eu.javaland.clean_hexagonal_onion.command.author;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthorCommandsTest {
    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorCommands authorCommands;

    // This is not a very expressive test, we still lack a lot of functionality to be able to write good tests. We
    // need to make due with what we have for now.
    @Test
    private void create() {
        // given
        var registerAuthorDTO = new RegisterAuthorDTO("firstName", "lastName");
        // when
        authorCommands.create(registerAuthorDTO);
        // then
        Author expectedAuthor = Author.createAuthor(registerAuthorDTO.firstName(),
                registerAuthorDTO.lastName());
        verify(authorService, times(1)).registerAuthor(expectedAuthor);
    }

}
