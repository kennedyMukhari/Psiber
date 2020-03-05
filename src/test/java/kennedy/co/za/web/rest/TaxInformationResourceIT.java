package kennedy.co.za.web.rest;

import kennedy.co.za.PsiberAssessmentApp;
import kennedy.co.za.domain.TaxInformation;
import kennedy.co.za.repository.TaxInformationRepository;
import kennedy.co.za.service.TaxInformationService;
import kennedy.co.za.service.dto.TaxInformationDTO;
import kennedy.co.za.service.mapper.TaxInformationMapper;
import kennedy.co.za.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static kennedy.co.za.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import kennedy.co.za.domain.enumeration.Frequency;
/**
 * Integration tests for the {@link TaxInformationResource} REST controller.
 */
@SpringBootTest(classes = PsiberAssessmentApp.class)
public class TaxInformationResourceIT {

    private static final Integer DEFAULT_TAX_YEAR = 2017;
    private static final Integer UPDATED_TAX_YEAR = 2018;

    private static final Integer DEFAULT_AGE = 150;
    private static final Integer UPDATED_AGE = 149;

    private static final Long DEFAULT_TOTAL_EARNINGS = 1L;
    private static final Long UPDATED_TOTAL_EARNINGS = 2L;

    private static final Frequency DEFAULT_FREQUENCY = Frequency.ANNUAL;
    private static final Frequency UPDATED_FREQUENCY = Frequency.MONTHLY;

    private static final Integer DEFAULT_NUMBER_OF_MEDICAL_AID_MEMBERS = 30;
    private static final Integer UPDATED_NUMBER_OF_MEDICAL_AID_MEMBERS = 29;

    @Autowired
    private TaxInformationRepository taxInformationRepository;

    @Autowired
    private TaxInformationMapper taxInformationMapper;

    @Autowired
    private TaxInformationService taxInformationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTaxInformationMockMvc;

    private TaxInformation taxInformation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaxInformationResource taxInformationResource = new TaxInformationResource(taxInformationService);
        this.restTaxInformationMockMvc = MockMvcBuilders.standaloneSetup(taxInformationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxInformation createEntity(EntityManager em) {
        TaxInformation taxInformation = new TaxInformation()
            .taxYear(DEFAULT_TAX_YEAR)
            .age(DEFAULT_AGE)
            .totalEarnings(DEFAULT_TOTAL_EARNINGS)
            .frequency(DEFAULT_FREQUENCY)
            .numberOfMedicalAidMembers(DEFAULT_NUMBER_OF_MEDICAL_AID_MEMBERS);
        return taxInformation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxInformation createUpdatedEntity(EntityManager em) {
        TaxInformation taxInformation = new TaxInformation()
            .taxYear(UPDATED_TAX_YEAR)
            .age(UPDATED_AGE)
            .totalEarnings(UPDATED_TOTAL_EARNINGS)
            .frequency(UPDATED_FREQUENCY)
            .numberOfMedicalAidMembers(UPDATED_NUMBER_OF_MEDICAL_AID_MEMBERS);
        return taxInformation;
    }

    @BeforeEach
    public void initTest() {
        taxInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaxInformation() throws Exception {
        int databaseSizeBeforeCreate = taxInformationRepository.findAll().size();

        // Create the TaxInformation
        TaxInformationDTO taxInformationDTO = taxInformationMapper.toDto(taxInformation);
        restTaxInformationMockMvc.perform(post("/api/tax-informations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the TaxInformation in the database
        List<TaxInformation> taxInformationList = taxInformationRepository.findAll();
        assertThat(taxInformationList).hasSize(databaseSizeBeforeCreate + 1);
        TaxInformation testTaxInformation = taxInformationList.get(taxInformationList.size() - 1);
        assertThat(testTaxInformation.getTaxYear()).isEqualTo(DEFAULT_TAX_YEAR);
        assertThat(testTaxInformation.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testTaxInformation.getTotalEarnings()).isEqualTo(DEFAULT_TOTAL_EARNINGS);
        assertThat(testTaxInformation.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
        assertThat(testTaxInformation.getNumberOfMedicalAidMembers()).isEqualTo(DEFAULT_NUMBER_OF_MEDICAL_AID_MEMBERS);
    }

    @Test
    @Transactional
    public void createTaxInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxInformationRepository.findAll().size();

        // Create the TaxInformation with an existing ID
        taxInformation.setId(1L);
        TaxInformationDTO taxInformationDTO = taxInformationMapper.toDto(taxInformation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxInformationMockMvc.perform(post("/api/tax-informations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaxInformation in the database
        List<TaxInformation> taxInformationList = taxInformationRepository.findAll();
        assertThat(taxInformationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTaxYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxInformationRepository.findAll().size();
        // set the field null
        taxInformation.setTaxYear(null);

        // Create the TaxInformation, which fails.
        TaxInformationDTO taxInformationDTO = taxInformationMapper.toDto(taxInformation);

        restTaxInformationMockMvc.perform(post("/api/tax-informations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxInformationDTO)))
            .andExpect(status().isBadRequest());

        List<TaxInformation> taxInformationList = taxInformationRepository.findAll();
        assertThat(taxInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxInformationRepository.findAll().size();
        // set the field null
        taxInformation.setAge(null);

        // Create the TaxInformation, which fails.
        TaxInformationDTO taxInformationDTO = taxInformationMapper.toDto(taxInformation);

        restTaxInformationMockMvc.perform(post("/api/tax-informations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxInformationDTO)))
            .andExpect(status().isBadRequest());

        List<TaxInformation> taxInformationList = taxInformationRepository.findAll();
        assertThat(taxInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalEarningsIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxInformationRepository.findAll().size();
        // set the field null
        taxInformation.setTotalEarnings(null);

        // Create the TaxInformation, which fails.
        TaxInformationDTO taxInformationDTO = taxInformationMapper.toDto(taxInformation);

        restTaxInformationMockMvc.perform(post("/api/tax-informations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxInformationDTO)))
            .andExpect(status().isBadRequest());

        List<TaxInformation> taxInformationList = taxInformationRepository.findAll();
        assertThat(taxInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFrequencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxInformationRepository.findAll().size();
        // set the field null
        taxInformation.setFrequency(null);

        // Create the TaxInformation, which fails.
        TaxInformationDTO taxInformationDTO = taxInformationMapper.toDto(taxInformation);

        restTaxInformationMockMvc.perform(post("/api/tax-informations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxInformationDTO)))
            .andExpect(status().isBadRequest());

        List<TaxInformation> taxInformationList = taxInformationRepository.findAll();
        assertThat(taxInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberOfMedicalAidMembersIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxInformationRepository.findAll().size();
        // set the field null
        taxInformation.setNumberOfMedicalAidMembers(null);

        // Create the TaxInformation, which fails.
        TaxInformationDTO taxInformationDTO = taxInformationMapper.toDto(taxInformation);

        restTaxInformationMockMvc.perform(post("/api/tax-informations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxInformationDTO)))
            .andExpect(status().isBadRequest());

        List<TaxInformation> taxInformationList = taxInformationRepository.findAll();
        assertThat(taxInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaxInformations() throws Exception {
        // Initialize the database
        taxInformationRepository.saveAndFlush(taxInformation);

        // Get all the taxInformationList
        restTaxInformationMockMvc.perform(get("/api/tax-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].taxYear").value(hasItem(DEFAULT_TAX_YEAR)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].totalEarnings").value(hasItem(DEFAULT_TOTAL_EARNINGS.intValue())))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].numberOfMedicalAidMembers").value(hasItem(DEFAULT_NUMBER_OF_MEDICAL_AID_MEMBERS)));
    }
    
    @Test
    @Transactional
    public void getTaxInformation() throws Exception {
        // Initialize the database
        taxInformationRepository.saveAndFlush(taxInformation);

        // Get the taxInformation
        restTaxInformationMockMvc.perform(get("/api/tax-informations/{id}", taxInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taxInformation.getId().intValue()))
            .andExpect(jsonPath("$.taxYear").value(DEFAULT_TAX_YEAR))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.totalEarnings").value(DEFAULT_TOTAL_EARNINGS.intValue()))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY.toString()))
            .andExpect(jsonPath("$.numberOfMedicalAidMembers").value(DEFAULT_NUMBER_OF_MEDICAL_AID_MEMBERS));
    }

    @Test
    @Transactional
    public void getNonExistingTaxInformation() throws Exception {
        // Get the taxInformation
        restTaxInformationMockMvc.perform(get("/api/tax-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxInformation() throws Exception {
        // Initialize the database
        taxInformationRepository.saveAndFlush(taxInformation);

        int databaseSizeBeforeUpdate = taxInformationRepository.findAll().size();

        // Update the taxInformation
        TaxInformation updatedTaxInformation = taxInformationRepository.findById(taxInformation.getId()).get();
        // Disconnect from session so that the updates on updatedTaxInformation are not directly saved in db
        em.detach(updatedTaxInformation);
        updatedTaxInformation
            .taxYear(UPDATED_TAX_YEAR)
            .age(UPDATED_AGE)
            .totalEarnings(UPDATED_TOTAL_EARNINGS)
            .frequency(UPDATED_FREQUENCY)
            .numberOfMedicalAidMembers(UPDATED_NUMBER_OF_MEDICAL_AID_MEMBERS);
        TaxInformationDTO taxInformationDTO = taxInformationMapper.toDto(updatedTaxInformation);

        restTaxInformationMockMvc.perform(put("/api/tax-informations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxInformationDTO)))
            .andExpect(status().isOk());

        // Validate the TaxInformation in the database
        List<TaxInformation> taxInformationList = taxInformationRepository.findAll();
        assertThat(taxInformationList).hasSize(databaseSizeBeforeUpdate);
        TaxInformation testTaxInformation = taxInformationList.get(taxInformationList.size() - 1);
        assertThat(testTaxInformation.getTaxYear()).isEqualTo(UPDATED_TAX_YEAR);
        assertThat(testTaxInformation.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testTaxInformation.getTotalEarnings()).isEqualTo(UPDATED_TOTAL_EARNINGS);
        assertThat(testTaxInformation.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testTaxInformation.getNumberOfMedicalAidMembers()).isEqualTo(UPDATED_NUMBER_OF_MEDICAL_AID_MEMBERS);
    }

    @Test
    @Transactional
    public void updateNonExistingTaxInformation() throws Exception {
        int databaseSizeBeforeUpdate = taxInformationRepository.findAll().size();

        // Create the TaxInformation
        TaxInformationDTO taxInformationDTO = taxInformationMapper.toDto(taxInformation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxInformationMockMvc.perform(put("/api/tax-informations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaxInformation in the database
        List<TaxInformation> taxInformationList = taxInformationRepository.findAll();
        assertThat(taxInformationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaxInformation() throws Exception {
        // Initialize the database
        taxInformationRepository.saveAndFlush(taxInformation);

        int databaseSizeBeforeDelete = taxInformationRepository.findAll().size();

        // Delete the taxInformation
        restTaxInformationMockMvc.perform(delete("/api/tax-informations/{id}", taxInformation.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaxInformation> taxInformationList = taxInformationRepository.findAll();
        assertThat(taxInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
