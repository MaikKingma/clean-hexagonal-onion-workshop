package nl.maikkingma.clean_hexagonal_onion.data.book;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.maikkingma.clean_hexagonal_onion.data.author.AuthorJPA;

import java.util.UUID;

import static jakarta.persistence.GenerationType.SEQUENCE;


/**
 * @author Maik Kingma
 */

@Entity
@Builder
@NoArgsConstructor()
@AllArgsConstructor
@Table(name = "book")
public class BookJPA{

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "book_seq_gen")
    @SequenceGenerator(name = "book_seq_gen", sequenceName = "book_seq", allocationSize = 1)
    @Getter
    private Long id;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private AuthorJPA author;

    @Getter
    private String title;

    @Getter
    private String genre;

    @Getter
    private UUID publisherId;

    @Getter
    private boolean published;

    @Getter
    private String isbn;
}
