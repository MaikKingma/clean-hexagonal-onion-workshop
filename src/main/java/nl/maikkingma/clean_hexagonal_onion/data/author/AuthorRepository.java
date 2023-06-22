package nl.maikkingma.clean_hexagonal_onion.data.author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Maik Kingma
 */

@Repository
public interface AuthorRepository extends JpaRepository<AuthorJPA, Long> {
}
