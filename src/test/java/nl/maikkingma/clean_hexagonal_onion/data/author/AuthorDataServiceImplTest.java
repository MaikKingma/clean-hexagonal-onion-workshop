package nl.maikkingma.clean_hexagonal_onion.data.author;

import nl.maikkingma.clean_hexagonal_onion.domaininteraction.author.AuthorDTO;
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
class AuthorDataServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorDataServiceImpl authorService;

    @Test
    void shouldCallRepository() {
        // when
        authorService.save(new AuthorDTO(1L, "firstName", "lastName"));
        // then
        ArgumentCaptor<AuthorJPA> argumentCaptor = ArgumentCaptor.forClass(AuthorJPA.class);
        verify(authorRepository, times(1)).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(
                AuthorJPA.builder()
                        .id(1L)
                        .firstName("firstName")
                        .lastName("lastName")
                        .build());
    }
}