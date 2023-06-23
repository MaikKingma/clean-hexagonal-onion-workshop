package nl.maikkingma.clean_hexagonal_onion.domaininteraction.author;

import nl.maikkingma.clean_hexagonal_onion.domain.author.Author;

class AuthorDomainMapper {
    // we keep this method because we will need it later when actual business logic needs to be triggered from the
    // domain core
    static Author mapToDomain(AuthorDTO authorJPA) {
        return Author.restore()
                .id(authorJPA.id())
                .firstName(authorJPA.firstName())
                .lastName(authorJPA.lastName())
                .build();
    }
}
