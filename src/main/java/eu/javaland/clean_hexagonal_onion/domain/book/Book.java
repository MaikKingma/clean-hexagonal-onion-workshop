package eu.javaland.clean_hexagonal_onion.domain.book;

import eu.javaland.clean_hexagonal_onion.domain.DomainEvent;
import eu.javaland.clean_hexagonal_onion.domain.author.Author;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Maik Kingma
 */

@Builder(builderMethodName = "restore")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Book {

    @Getter
    private Long id;

    @Getter
    private Author author;

    @Getter
    private String title;

    @Getter
    private Genre genre;

    @Getter
    private UUID publisherId;

    @Getter
    private boolean published;

    @Getter
    private String isbn;

    @Getter
    @Builder.Default
    private List<DomainEvent> domainEvents = new ArrayList<>();

    public static Book createManuscript(String title, Genre genre, Author author) {
        return new Book(null, author, title, genre, null, false, null, new ArrayList<>());
    }

    public void requestPublishing(UUID publisherId) {
        this.publisherId = publisherId;
        domainEvents.add(new RequestPublishingEvent(this.id, this.publisherId));
    }

    public boolean canBePublished() {
        return publisherId == null && !published;
    }

    public void clearDomainEvents() {
        this.domainEvents = new ArrayList<>();
    }

    public void updatePublishingInfo(String isbn) {
        this.isbn = isbn;
        this.published = true;
    }

    @Value
    public static class RequestPublishingEvent extends DomainEvent {
        Long bookId;
        UUID publisherId;
    }
}
