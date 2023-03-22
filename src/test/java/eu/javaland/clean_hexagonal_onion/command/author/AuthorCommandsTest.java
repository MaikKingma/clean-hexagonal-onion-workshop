package eu.javaland.clean_hexagonal_onion.command.author;

import eu.javaland.clean_hexagonal_onion.domaininteraction.author.AuthorFlow;
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
    private AuthorFlow authorFlow;

    @InjectMocks
    private AuthorCommands authorCommands;

    @Test
    void create() {
        // given
        var registerAuthorDTO = new RegisterAuthorPayload("firstName", "lastName");
        // when
        authorCommands.create(registerAuthorDTO);
        // then
        verify(authorFlow, times(1)).registerAuthorByName("firstName", "lastName");
    }
}
