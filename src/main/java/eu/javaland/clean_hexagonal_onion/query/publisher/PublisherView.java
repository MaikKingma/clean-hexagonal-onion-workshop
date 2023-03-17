package eu.javaland.clean_hexagonal_onion.query.publisher;

import eu.javaland.clean_hexagonal_onion.domaininteraction.publisher.PublisherDTO;

import java.util.UUID;

/**
 * @author Maik Kingma
 */

public record PublisherView(UUID id, String name) {
    public PublisherView(PublisherDTO publisherDTO) {
        this(publisherDTO.id(), publisherDTO.name());
    }
}
