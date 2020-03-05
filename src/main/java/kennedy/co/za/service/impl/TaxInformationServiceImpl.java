package kennedy.co.za.service.impl;

import kennedy.co.za.service.TaxInformationService;
import kennedy.co.za.domain.TaxInformation;
import kennedy.co.za.repository.TaxInformationRepository;
import kennedy.co.za.service.dto.TaxInformationDTO;
import kennedy.co.za.service.mapper.TaxInformationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TaxInformation}.
 */
@Service
@Transactional
public class TaxInformationServiceImpl implements TaxInformationService {

    private final Logger log = LoggerFactory.getLogger(TaxInformationServiceImpl.class);

    private final TaxInformationRepository taxInformationRepository;

    private final TaxInformationMapper taxInformationMapper;

    public TaxInformationServiceImpl(TaxInformationRepository taxInformationRepository, TaxInformationMapper taxInformationMapper) {
        this.taxInformationRepository = taxInformationRepository;
        this.taxInformationMapper = taxInformationMapper;
    }

    /**
     * Save a taxInformation.
     *
     * @param taxInformationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TaxInformationDTO save(TaxInformationDTO taxInformationDTO) {
        log.debug("Request to save TaxInformation : {}", taxInformationDTO);
        TaxInformation taxInformation = taxInformationMapper.toEntity(taxInformationDTO);
        taxInformation = taxInformationRepository.save(taxInformation);
        return taxInformationMapper.toDto(taxInformation);
    }

    /**
     * Get all the taxInformations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaxInformationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaxInformations");
        return taxInformationRepository.findAll(pageable)
            .map(taxInformationMapper::toDto);
    }

    /**
     * Get one taxInformation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TaxInformationDTO> findOne(Long id) {
        log.debug("Request to get TaxInformation : {}", id);
        return taxInformationRepository.findById(id)
            .map(taxInformationMapper::toDto);
    }

    /**
     * Delete the taxInformation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaxInformation : {}", id);
        taxInformationRepository.deleteById(id);
    }
}
