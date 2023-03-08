package eu.javaland.clean_hexagonal_onion.data.author;

import eu.javaland.clean_hexagonal_onion.domain.author.Author;
import eu.javaland.clean_hexagonal_onion.domaininteraction.author.AuthorDataService;
import eu.javaland.clean_hexagonal_onion.domaininteraction.author.AuthorMapper;
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
    public void save(Author author) {
        log.info("registering author {}", author.getFullName());
        authorRepository.save(AuthorMapper.mapToJPA(author));
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll().stream()
                .map(AuthorMapper::mapToDomain)
                .toList();
    }
}
