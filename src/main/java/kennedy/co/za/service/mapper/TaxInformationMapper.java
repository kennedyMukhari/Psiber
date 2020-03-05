package kennedy.co.za.service.mapper;


import kennedy.co.za.domain.*;
import kennedy.co.za.service.dto.TaxInformationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaxInformation} and its DTO {@link TaxInformationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaxInformationMapper extends EntityMapper<TaxInformationDTO, TaxInformation> {



    default TaxInformation fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaxInformation taxInformation = new TaxInformation();
        taxInformation.setId(id);
        return taxInformation;
    }
}
