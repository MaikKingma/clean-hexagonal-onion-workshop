package eu.javaland.clean_hexagonal_onion.domain.publisher;

import java.util.UUID;

/**
 * @author Maik Kingma
 */

public record Publisher(UUID id, String name) {
}
