package eu.javaland.clean_hexagonal_onion.domaininteraction.publisher;

import java.util.List;

/**
 * @author Maik Kingma
 */

public interface PublisherAppService {
    List<PublisherDTO> getAllPublishers();
}
