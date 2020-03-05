package kennedy.co.za.web.rest;

import kennedy.co.za.service.TaxInformationService;
import kennedy.co.za.web.rest.business.rules.TwentyEighteenTaxCalculations;
import kennedy.co.za.web.rest.business.rules.TwentySeventeenTaxCalculations;
import kennedy.co.za.web.rest.errors.BadRequestAlertException;
import kennedy.co.za.service.dto.TaxInformationDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link kennedy.co.za.domain.TaxInformation}.
 */
@RestController
@RequestMapping("/api")
public class TaxInformationResource {

    private final Logger log = LoggerFactory.getLogger(TaxInformationResource.class);

    private static final String ENTITY_NAME = "taxInformation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxInformationService taxInformationService;

    public TaxInformationResource(TaxInformationService taxInformationService) {
        this.taxInformationService = taxInformationService;
    }

    /**
     * {@code POST  /tax-informations} : Create a new taxInformation.
     *
     * @param taxInformationDTO the taxInformationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxInformationDTO, or with status {@code 400 (Bad Request)} if the taxInformation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tax-informations")
    public ResponseEntity<TaxInformationDTO> createTaxInformation(@Valid @RequestBody TaxInformationDTO taxInformationDTO) throws URISyntaxException {
        log.debug("REST request to save TaxInformation : {}", taxInformationDTO);
        if (taxInformationDTO.getId() != null) {
            throw new BadRequestAlertException("A new taxInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }

        // TODO: Call Tax Calculation Function Here
        Integer taxYear = taxInformationDTO.getTaxYear();

        Long annualTax, monthlyTax;
        if (taxYear == 2017) {
            TwentySeventeenTaxCalculations twentySeventeenTaxCalculations = new TwentySeventeenTaxCalculations(taxInformationDTO);
            twentySeventeenTaxCalculations.calculateTaxableIncome();
            twentySeventeenTaxCalculations.calculateTaxRate();
            twentySeventeenTaxCalculations.calculateTaxMedicalAidCost();
            annualTax = twentySeventeenTaxCalculations.calculateAnnualTax();
            monthlyTax = twentySeventeenTaxCalculations.calculateMonthlyTax();
        } else {
            TwentyEighteenTaxCalculations twentyEighteenTaxCalculations = new TwentyEighteenTaxCalculations(taxInformationDTO);
            twentyEighteenTaxCalculations.calculateTaxableIncome();
            twentyEighteenTaxCalculations.calculateTaxRate();
            twentyEighteenTaxCalculations.calculateTaxMedicalAidCost();
            annualTax = twentyEighteenTaxCalculations.calculateAnnualTax();
            monthlyTax = twentyEighteenTaxCalculations.calculateMonthlyTax();
        }

        TaxInformationDTO result = taxInformationService.save(taxInformationDTO);
        return ResponseEntity.created(new URI("/api/tax-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tax-informations} : Updates an existing taxInformation.
     *
     * @param taxInformationDTO the taxInformationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxInformationDTO,
     * or with status {@code 400 (Bad Request)} if the taxInformationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxInformationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tax-informations")
    public ResponseEntity<TaxInformationDTO> updateTaxInformation(@Valid @RequestBody TaxInformationDTO taxInformationDTO) throws URISyntaxException {
        log.debug("REST request to update TaxInformation : {}", taxInformationDTO);
        if (taxInformationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        TaxInformationDTO result = taxInformationService.save(taxInformationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taxInformationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tax-informations} : get all the taxInformations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxInformations in body.
     */
    @GetMapping("/tax-informations")
    public ResponseEntity<List<TaxInformationDTO>> getAllTaxInformations(Pageable pageable) {
        log.debug("REST request to get a page of TaxInformations");
        Page<TaxInformationDTO> page = taxInformationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tax-informations/:id} : get the "id" taxInformation.
     *
     * @param id the id of the taxInformationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxInformationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tax-informations/{id}")
    public ResponseEntity<TaxInformationDTO> getTaxInformation(@PathVariable Long id) {
        log.debug("REST request to get TaxInformation : {}", id);
        Optional<TaxInformationDTO> taxInformationDTO = taxInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taxInformationDTO);
    }

    /**
     * {@code DELETE  /tax-informations/:id} : delete the "id" taxInformation.
     *
     * @param id the id of the taxInformationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tax-informations/{id}")
    public ResponseEntity<Void> deleteTaxInformation(@PathVariable Long id) {
        log.debug("REST request to delete TaxInformation : {}", id);
        taxInformationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
