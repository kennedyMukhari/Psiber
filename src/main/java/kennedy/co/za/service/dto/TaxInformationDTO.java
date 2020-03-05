package kennedy.co.za.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import kennedy.co.za.domain.enumeration.Frequency;

/**
 * A DTO for the {@link kennedy.co.za.domain.TaxInformation} entity.
 */
public class TaxInformationDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 2017)
    @Max(value = 2018)
    private Integer taxYear;

    @NotNull
    @Max(value = 150)
    private Integer age;

    @NotNull
    private Long totalEarnings;

    @NotNull
    private Frequency frequency;

    @NotNull
    @Max(value = 30)
    private Integer numberOfMedicalAidMembers;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(Integer taxYear) {
        this.taxYear = taxYear;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(Long totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public Integer getNumberOfMedicalAidMembers() {
        return numberOfMedicalAidMembers;
    }

    public void setNumberOfMedicalAidMembers(Integer numberOfMedicalAidMembers) {
        this.numberOfMedicalAidMembers = numberOfMedicalAidMembers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaxInformationDTO taxInformationDTO = (TaxInformationDTO) o;
        if (taxInformationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taxInformationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaxInformationDTO{" +
            "id=" + getId() +
            ", taxYear=" + getTaxYear() +
            ", age=" + getAge() +
            ", totalEarnings=" + getTotalEarnings() +
            ", frequency='" + getFrequency() + "'" +
            ", numberOfMedicalAidMembers=" + getNumberOfMedicalAidMembers() +
            "}";
    }
}
