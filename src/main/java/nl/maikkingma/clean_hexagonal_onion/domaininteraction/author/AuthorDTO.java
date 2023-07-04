package nl.maikkingma.clean_hexagonal_onion.domaininteraction.author;

import nl.maikkingma.clean_hexagonal_onion.domain.author.Author;

public record AuthorDTO(Long id, String firstName, String lastName) {
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public AuthorDTO(Author author) {
        this(author.getId(), author.getFirstName(), author.getLastName());
    }
}
