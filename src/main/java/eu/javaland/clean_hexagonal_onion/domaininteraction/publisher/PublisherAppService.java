package eu.javaland.clean_hexagonal_onion.domaininteraction.publisher;

import java.util.List;
import java.util.UUID;

/**
 * @author Maik Kingma
 */

public interface PublisherAppService {
    List<PublisherDTO> getAllPublishers();

    PublisherDTO getPublisherById(String publisherId);

    String requestPublishing(UUID publisherId, String fullName, String title);

    class PublisherNotFoundException extends RuntimeException {
        public PublisherNotFoundException(String errorMessage) {
            super(errorMessage);
        }
    }

    class RequestPublishingException extends RuntimeException {
        public RequestPublishingException(String errorMessage) {
            super(errorMessage);
        }
    }
}
