package eu.javaland.clean_hexagonal_onion.acl.publisher;

import eu.javaland.clean_hexagonal_onion.domaininteraction.publisher.PublisherAppService;
import eu.javaland.clean_hexagonal_onion.domaininteraction.publisher.PublisherDTO;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.json.Json;
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
    private static final String SUB_PATH_PUBLISHERS_ID = "/publishers/%s";
    private static final String SUB_PATH_PUBLISHERS_RECEIVE_BOOK_OFFER = "/publishers/receiveBookOffer";

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

    @Override
    public PublisherDTO getPublisherById(String publisherId) {
        String uri = getUri(String.format(SUB_PATH_PUBLISHERS_ID, publisherId));
        var result = restTemplate.getForObject(uri, PublisherPayload.class);
        Objects.requireNonNull(result);

        return new PublisherDTO(result.id, result.name);
    }

    @Override
    public String requestPublishing(UUID publisherId, String fullName, String title) {
        var uri = getUri(SUB_PATH_PUBLISHERS_RECEIVE_BOOK_OFFER);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var payload = Json.createObjectBuilder()
                .add("publisherId", publisherId.toString())
                .add("author", fullName)
                .add("title", title)
                .build().toString();
        var request = new HttpEntity<>(payload, headers);
        var publishingResponseDTO = restTemplate.postForObject(uri, request,
                RequestPublishingResponsePayload.class);
        var isbn = Objects.requireNonNull(publishingResponseDTO, "PublishingResponseDTO must not be null").isbn();

        if (StringUtils.isBlank(isbn)) {
            throw new RequestPublishingException("ISBN is null or empty");
        }

        return isbn;
    }

    private String getUri(String subPath) {
        return publisherServiceBaseUri + subPath;
    }

    private record PublisherPayload(UUID id, String name) { }

    private record RequestPublishingResponsePayload(String isbn) { }
}
