package eu.javaland.clean_hexagonal_onion.data.book;

import eu.javaland.clean_hexagonal_onion.domaininteraction.author.AuthorDTO;
import eu.javaland.clean_hexagonal_onion.domaininteraction.book.BookDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookDataServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookDataServiceImpl bookDataServiceImpl;

    @Test
    void save() {
        // when
        bookDataServiceImpl.save(new BookDTO(1L, new AuthorDTO(1L, null, null), "title", "genre", null, false, null, new ArrayList<>()));
        // then
        verify(bookRepository, times(1)).save(any(BookJPA.class));
    }

    @Test
    void findAll() {
        // when
        bookDataServiceImpl.findAll();
        // then
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void findByPartialTitle() {
        // when
        bookDataServiceImpl.findByPartialTitle("title");
        // then
        verify(bookRepository, times(1)).findByTitleContains("title");
    }
}