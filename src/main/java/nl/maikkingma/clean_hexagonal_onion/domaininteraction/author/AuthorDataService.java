package nl.maikkingma.clean_hexagonal_onion.domaininteraction.author;

import java.util.List;

public interface AuthorDataService {
    void save(AuthorDTO author);

    List<AuthorDTO> findAll();
}
