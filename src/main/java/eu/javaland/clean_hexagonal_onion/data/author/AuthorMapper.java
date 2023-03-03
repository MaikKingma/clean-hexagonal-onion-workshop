package eu.javaland.clean_hexagonal_onion.data.author;

import eu.javaland.clean_hexagonal_onion.domain.author.Author;

public class AuthorMapper {
    public static AuthorJPA mapToJPA(Author author){
        return new AuthorJPA.AuthorJPABuilder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }
}
