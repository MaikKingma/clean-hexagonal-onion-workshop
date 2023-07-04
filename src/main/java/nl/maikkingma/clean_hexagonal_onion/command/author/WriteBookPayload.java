package nl.maikkingma.clean_hexagonal_onion.command.author;

/**
 * @author Maik Kingma
 */

public record WriteBookPayload(String title, String genre) {
}
