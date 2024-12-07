package id.lariss.repository;

import id.lariss.domain.Connectivity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Connectivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectivityRepository extends JpaRepository<Connectivity, Long> {}
