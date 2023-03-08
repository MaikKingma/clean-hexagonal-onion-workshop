package eu.javaland.clean_hexagonal_onion.data.author;

import eu.javaland.clean_hexagonal_onion.domaininteraction.author.AuthorDTO;
import eu.javaland.clean_hexagonal_onion.domaininteraction.author.AuthorDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AuthorDataServiceImpl implements AuthorDataService {

    private final AuthorRepository authorRepository;

    public AuthorDataServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void save(AuthorDTO authorDTO) {
        log.info("registering authorDTO {}", authorDTO.getFullName());
        authorRepository.save(AuthorJPAMapper.mapToJPA(authorDTO));
    }

    @Override
    public List<AuthorDTO> findAll() {
        return authorRepository.findAll().stream()
                .map(authorJPA -> new AuthorDTO(authorJPA.getId(), authorJPA.getFirstName(), authorJPA.getLastName()))
                .toList();
    }
}
