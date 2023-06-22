package nl.maikkingma.clean_hexagonal_onion.domain.author;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Author domain aggregate
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Author {
    @Getter
    private Long id;
    @Getter
    private String firstName;
    @Getter
    private String lastName;

    /**
     * Public factory method for an {@link  Author} Aggregate. This method is essentially part of the Domain interaction layer
     * but resides withing the aggregate itself due to the private access modifier of the all args constructor.
     *
     * @param firstName the first name of the author
     * @param lastName the last name of the author
     * @return the {@link  Author}
     */
    public static Author createAuthor(String firstName, String lastName) {
        return new Author(null, firstName, lastName);
    }

    /**
     * Get Full name
     *
     * @return the full name of the Author
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
