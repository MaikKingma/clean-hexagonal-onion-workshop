package nl.maikkingma.clean_hexagonal_onion.domaininteraction.author;

import nl.maikkingma.clean_hexagonal_onion.domaininteraction.author.AuthorDTO;
import nl.maikkingma.clean_hexagonal_onion.domain.author.Author;
import org.junit.jupiter.api.Test;

class AuthorDomainMapperTest {

    @Test
    void mapToDomain() {
        // given
        var input = new AuthorDTO(1L, "first", "last");
        var expectedOutput = Author.restore()
                .id(1L)
                .firstName("first")
                .lastName("last")
                .build();
        // when
        var result = AuthorDomainMapper.mapToDomain(input);
        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedOutput);
    }
}
