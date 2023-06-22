package eu.javaland.clean_hexagonal_onion.domaininteraction.author;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
        var expectedAuthor = new AuthorDTO(null, "firstName", "lastName");
        ArgumentCaptor<AuthorDTO> argumentCaptor = ArgumentCaptor.forClass(AuthorDTO.class);
        // when
        authorFlow.registerAuthorByName("firstName", "lastName");
        // then
        verify(authorDataService, times(1)).save(argumentCaptor.capture());
        AuthorDTO actualAuthor = argumentCaptor.getValue();
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    void getListOfAllAuthors() {
        // given
        var authorsData = List.of(
                new AuthorDTO(1L, "firstName1", "lastName1"),
                new AuthorDTO(2L, "firstName2", "lastName2"));
        when(authorDataService.findAll()).thenReturn(authorsData);
        // when
        List<AuthorDTO> actualResult = authorFlow.getListOfAllAuthors();
        // then
        assertThat(actualResult).containsExactlyInAnyOrder(new AuthorDTO(1L, "firstName1", "lastName1"),
                new AuthorDTO(2L, "firstName2", "lastName2"));
    }
}
