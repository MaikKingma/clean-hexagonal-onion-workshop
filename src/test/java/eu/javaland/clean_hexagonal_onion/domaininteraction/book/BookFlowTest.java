package eu.javaland.clean_hexagonal_onion.domaininteraction.book;

import eu.javaland.clean_hexagonal_onion.domaininteraction.author.AuthorDTO;
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
class BookFlowTest {

    @Mock
    private BookDataService bookDataService;

    @InjectMocks
    private BookFlow bookFlow;

    @Test
    void findAllBooks() {
        // given
        when(bookDataService.findAll()).thenReturn(List.of(
                new BookDTO(1L, new AuthorDTO(1L, "Tom", "Someone"), "title", "genre", null, false, null),
                new BookDTO(2L, new AuthorDTO(2L, "Hanna", "Banana"), "title2", "genre2", null, false, null)));
        // when
        List<BookDTO> actualResult = bookFlow.findAllBooks();
        // then
        verify(bookDataService, times(1)).findAll();
        assertThat(actualResult).containsExactlyInAnyOrder(
                new BookDTO(1L, new AuthorDTO(1L, "Tom", "Someone"), "title", "genre", null, false, null),
                new BookDTO(2L, new AuthorDTO(2L, "Hanna", "Banana"), "title2", "genre2", null, false, null));
    }

    @Test
    void findAllBooksWithMatchingTitle() {
        // given
        when(bookDataService.findByPartialTitle("title")).thenReturn(List.of(
                new BookDTO(1L, new AuthorDTO(1L, "Tom", "Someone"), "title", "genre", null, false, null)));
        // when
        bookFlow.findAllBooksWithMatchingTitle("title");
        // then
        verify(bookDataService, times(1)).findByPartialTitle("title");
        assertThat(bookFlow.findAllBooksWithMatchingTitle("title")).containsExactlyInAnyOrder(
                new BookDTO(1L, new AuthorDTO(1L, "Tom", "Someone"), "title", "genre", null, false, null));
    }
}