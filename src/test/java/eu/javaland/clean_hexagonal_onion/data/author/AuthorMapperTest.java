package eu.javaland.clean_hexagonal_onion.data.author;

import eu.javaland.clean_hexagonal_onion.domain.author.Author;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorMapperTest {

    @Test
    void mapToJPA() {
        // given
        AuthorJPA expected = AuthorJPA.builder()
                .firstName("firstName")
                .lastName("lastName")
                .build();
        // when
        AuthorJPA result = AuthorMapper.mapToJPA(Author.createAuthor("firstName", "lastName"));
        assertThat(result).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    @Test
    void mapToDomain() {
        // given
        Author expected = Author.restore()
                .firstName("firstName")
                .lastName("lastName")
                .build();
        // when
        Author result = AuthorMapper.mapToDomain(AuthorJPA.builder()
                .firstName("firstName")
                .lastName("lastName")
                .build());
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }
}