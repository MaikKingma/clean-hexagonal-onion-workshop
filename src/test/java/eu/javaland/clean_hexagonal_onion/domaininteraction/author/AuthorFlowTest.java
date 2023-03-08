package eu.javaland.clean_hexagonal_onion.domaininteraction.author;

import eu.javaland.clean_hexagonal_onion.domain.author.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.lang.model.util.Types;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorFlowTest {

    @Mock
    private AuthorDataService authorDataService;

    @InjectMocks
    private AuthorFlow authorFlow;

    @Test
    void registerAuthorByName() {
        // given
        var expectedAuthor = Author.create("firstName", "lastName");
        ArgumentCaptor<Author> argumentCaptor = ArgumentCaptor.forClass(Author.class);
        // when
        authorFlow.registerAuthorByName("firstName", "lastName");
        // then
        verify(authorDataService, times(1)).save(argumentCaptor.capture());
        Author actualAuthor = argumentCaptor.getValue();
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    void getListOfAllAuthors() {
        // given
        var authorsData = List.of(
                Author.create("firstName1", "lastName1"),
                Author.create("firstName2", "lastName2"));
        when(authorDataService.findAll()).thenReturn(authorsData);
        // when
        List<AuthorDTO> actualResult = authorFlow.getListOfAllAuthors();
        // then
        assertThat(actualResult).containsExactlyInAnyOrder(new AuthorDTO("firstName1", "lastName1"),
                new AuthorDTO("firstName2", "lastName2"));
    }
}
