package eu.javaland.clean_hexagonal_onion.query.author;

import eu.javaland.clean_hexagonal_onion.domain.author.Author;
import eu.javaland.clean_hexagonal_onion.domain.author.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorQueriesTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorQueries authorQueries;

    @Test
    void getAll() {
        // given
        List<Author> mockedAuthorListResponse = List.of(Author.restore().id(1L).firstName("firstName").lastName(
                "lastName").build());
        when(authorService.findAll()).thenReturn(mockedAuthorListResponse);
        // when then
        List<AuthorView> result = authorQueries.getAll();
        verify(authorService, times(1)).findAll();
        assertThat(result).containsExactly(new AuthorView(1L, "firstName lastName"));
    }
}