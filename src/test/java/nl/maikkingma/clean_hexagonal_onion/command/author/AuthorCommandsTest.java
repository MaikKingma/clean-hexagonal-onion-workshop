package nl.maikkingma.clean_hexagonal_onion.command.author;

import nl.maikkingma.clean_hexagonal_onion.domain.author.Author;
import nl.maikkingma.clean_hexagonal_onion.domain.author.AuthorService;
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

    @Test
    void create() {
        // given
        var registerAuthorDTO = new RegisterAuthorDTO("firstName", "lastName");
        Author expected = Author.restore().firstName("firstName").lastName("lastName").build();
        // when
        authorCommands.create(registerAuthorDTO);
        // then
        verify(authorService, times(1)).registerAuthor(assertArg(actual ->
                assertThat(actual).usingRecursiveComparison().isEqualTo(expected)));
    }
}
