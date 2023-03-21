package eu.javaland.clean_hexagonal_onion.command.author;

import eu.javaland.clean_hexagonal_onion.domain.author.Author;
import eu.javaland.clean_hexagonal_onion.domain.author.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
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
        ArgumentCaptor<Author> argumentCaptor = ArgumentCaptor.forClass(Author.class);
        verify(authorService, times(1)).registerAuthor(argumentCaptor.capture());
        Author actual = argumentCaptor.getValue();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

}
