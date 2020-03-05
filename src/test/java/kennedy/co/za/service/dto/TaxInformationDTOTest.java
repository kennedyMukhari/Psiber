package kennedy.co.za.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kennedy.co.za.web.rest.TestUtil;

public class TaxInformationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxInformationDTO.class);
        TaxInformationDTO taxInformationDTO1 = new TaxInformationDTO();
        taxInformationDTO1.setId(1L);
        TaxInformationDTO taxInformationDTO2 = new TaxInformationDTO();
        assertThat(taxInformationDTO1).isNotEqualTo(taxInformationDTO2);
        taxInformationDTO2.setId(taxInformationDTO1.getId());
        assertThat(taxInformationDTO1).isEqualTo(taxInformationDTO2);
        taxInformationDTO2.setId(2L);
        assertThat(taxInformationDTO1).isNotEqualTo(taxInformationDTO2);
        taxInformationDTO1.setId(null);
        assertThat(taxInformationDTO1).isNotEqualTo(taxInformationDTO2);
    }
}
