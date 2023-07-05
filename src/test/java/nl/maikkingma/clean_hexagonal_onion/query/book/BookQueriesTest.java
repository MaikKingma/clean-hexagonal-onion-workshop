package nl.maikkingma.clean_hexagonal_onion.query.book;

import nl.maikkingma.clean_hexagonal_onion.domaininteraction.author.AuthorDTO;
import nl.maikkingma.clean_hexagonal_onion.domaininteraction.author.AuthorFlow;
import nl.maikkingma.clean_hexagonal_onion.domaininteraction.book.BookDTO;
import nl.maikkingma.clean_hexagonal_onion.domaininteraction.book.BookFlow;
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
class BookQueriesTest {

    @Mock
    private BookFlow bookFlow;

    @Mock
    private AuthorFlow authorFlow;

    @InjectMocks
    private BookQueries bookQueries;

    @Test
    void findBooks_noTitle() {
        // given
        AuthorDTO authorDTO = new AuthorDTO(1L, null, null);
        BookDTO bookDTO = new BookDTO(1L, authorDTO, "title1", "genre1", null, false, null);
        when(bookFlow.findAllBooks()).thenReturn(List.of(bookDTO));
        when(authorFlow.findById(1L)).thenReturn(new AuthorDTO(1L, "firstName", "lastName"));
        // when
        bookQueries.findBooks(null);
        // then
        verify(bookFlow, times(1)).findAllBooks();
        verify(authorFlow, times(1)).findById(1L);
    }

    @Test
    void findBooks_givenTitle() {
        // given
        AuthorDTO authorDTO = new AuthorDTO(2L, null, null);
        BookDTO bookDTO = new BookDTO(2L, authorDTO, "title2", "genre2", null, false, null);
        when(bookFlow.findAllBooksWithMatchingTitle("title2")).thenReturn(List.of(bookDTO));
        when(authorFlow.findById(2L)).thenReturn(new AuthorDTO(2L, "firstName2", "lastName2"));
        // when
        List<BookView> books = bookQueries.findBooks("title2");
        // then
        verify(bookFlow, times(1)).findAllBooksWithMatchingTitle("title2");
        verify(authorFlow, times(1)).findById(2L);
        assertThat(books).containsExactly(new BookView("title2", "genre2", "firstName2 lastName2"));
    }
}