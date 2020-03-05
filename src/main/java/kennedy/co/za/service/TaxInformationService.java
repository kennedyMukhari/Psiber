package kennedy.co.za.service;

import kennedy.co.za.service.dto.TaxInformationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link kennedy.co.za.domain.TaxInformation}.
 */
public interface TaxInformationService {

    /**
     * Save a taxInformation.
     *
     * @param taxInformationDTO the entity to save.
     * @return the persisted entity.
     */
    TaxInformationDTO save(TaxInformationDTO taxInformationDTO);

    /**
     * Get all the taxInformations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TaxInformationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" taxInformation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaxInformationDTO> findOne(Long id);

    /**
     * Delete the "id" taxInformation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
