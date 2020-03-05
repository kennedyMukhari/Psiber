package kennedy.co.za.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kennedy.co.za.web.rest.TestUtil;

public class TaxInformationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxInformation.class);
        TaxInformation taxInformation1 = new TaxInformation();
        taxInformation1.setId(1L);
        TaxInformation taxInformation2 = new TaxInformation();
        taxInformation2.setId(taxInformation1.getId());
        assertThat(taxInformation1).isEqualTo(taxInformation2);
        taxInformation2.setId(2L);
        assertThat(taxInformation1).isNotEqualTo(taxInformation2);
        taxInformation1.setId(null);
        assertThat(taxInformation1).isNotEqualTo(taxInformation2);
    }
}
