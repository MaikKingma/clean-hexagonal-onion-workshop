package nl.maikkingma.clean_hexagonal_onion.data.author;

import nl.maikkingma.clean_hexagonal_onion.domain.author.Author;
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
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    void shouldCallRepository() {
        // when
        authorService.registerAuthor(Author.createAuthor("firstName", "lastName"));
        // then
        verify(authorRepository, times(1)).save(assertArg(actual ->
                assertThat(actual).usingRecursiveComparison().isEqualTo(AuthorJPA.builder()
                        .firstName("firstName")
                        .lastName("lastName")
                        .build())));

    }
}
