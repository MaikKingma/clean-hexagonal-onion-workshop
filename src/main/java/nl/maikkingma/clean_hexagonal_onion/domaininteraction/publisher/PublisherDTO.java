package nl.maikkingma.clean_hexagonal_onion.domaininteraction.publisher;

import nl.maikkingma.clean_hexagonal_onion.domain.publisher.Publisher;

import java.util.UUID;

/**
 * @author Maik Kingma
 */

public record PublisherDTO(UUID id, String name) {
    public PublisherDTO(Publisher publisher) {
        this(publisher.id(), publisher.name());
    }
}
