package nl.maikkingma.clean_hexagonal_onion.acl.publisher;

import nl.maikkingma.clean_hexagonal_onion.domaininteraction.publisher.PublisherAppService;
import nl.maikkingma.clean_hexagonal_onion.domaininteraction.publisher.PublisherDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Maik Kingma
 */

@Service
public class PublisherAppServiceImpl implements PublisherAppService {

    private static final String SUB_PATH_PUBLISHERS = "/publishers";

    private final RestTemplate restTemplate = new RestTemplate();
    
    @Value("${publisher.service.host}")
    private String publisherServiceBaseUri;

    @Override
    public List<PublisherDTO> getAllPublishers() {
        String uri = getUri(SUB_PATH_PUBLISHERS);
        var result = restTemplate.getForObject(uri, PublisherPayload[].class);
        return Arrays.stream(Objects.requireNonNull(result))
                .map(publisherPayload -> new PublisherDTO(publisherPayload.id, publisherPayload.name))
                .toList();
    }

    private String getUri(String subPath) {
        return publisherServiceBaseUri + subPath;
    }

    private record PublisherPayload(UUID id, String name) {
    }
}
