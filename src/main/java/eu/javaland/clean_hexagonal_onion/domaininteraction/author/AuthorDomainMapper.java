package eu.javaland.clean_hexagonal_onion.domaininteraction.author;

import eu.javaland.clean_hexagonal_onion.domain.author.Author;

public class AuthorDomainMapper {
    // we keep this method because we will need it later when actual business logic needs to be triggered from the
    // domain core
    public static Author mapToDomain(AuthorDTO authorJPA) {
        return Author.restore()
                .id(authorJPA.id())
                .firstName(authorJPA.firstName())
                .lastName(authorJPA.lastName())
                .build();
    }
}
