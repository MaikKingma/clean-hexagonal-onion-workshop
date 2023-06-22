package nl.maikkingma.clean_hexagonal_onion.command.author;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.assertArg;
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
    void create() {
        // given
        var registerAuthorDTO = new RegisterAuthorDTO("firstName", "lastName");
        // when
        authorCommands.create(registerAuthorDTO);
        // then
        Author expected = Author.createAuthor(registerAuthorDTO.firstName(),
                registerAuthorDTO.lastName());
        verify(authorService, times(1)).registerAuthor(assertArg(actual ->
                assertThat(actual).usingRecursiveComparison().isEqualTo(expected)));
    }
}
