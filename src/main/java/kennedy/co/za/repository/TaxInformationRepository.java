package kennedy.co.za.repository;

import kennedy.co.za.domain.TaxInformation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaxInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxInformationRepository extends JpaRepository<TaxInformation, Long> {

}
