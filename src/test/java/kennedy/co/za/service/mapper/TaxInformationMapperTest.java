package kennedy.co.za.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TaxInformationMapperTest {

    private TaxInformationMapper taxInformationMapper;

    @BeforeEach
    public void setUp() {
        taxInformationMapper = new TaxInformationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(taxInformationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taxInformationMapper.fromId(null)).isNull();
    }
}
