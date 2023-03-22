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
class AuthorActionCommandsTest {

    @Mock
    private AuthorFlow authorFlow;

    @InjectMocks
    private AuthorActionCommands authorActionCommands;

    @Test
    void writeBook() {
        // given
        var writeBookPayload = new WriteBookPayload("title", "genre");
        // when
        authorActionCommands.writeBook(1L, writeBookPayload);
        // then
        verify(authorFlow, times(1)).writeManuscript(1L, "title", "genre");
    }
}