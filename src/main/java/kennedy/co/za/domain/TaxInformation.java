package kennedy.co.za.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import kennedy.co.za.domain.enumeration.Frequency;

/**
 * A TaxInformation.
 */
@Entity
@Table(name = "tax_information")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TaxInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 2017)
    @Max(value = 2018)
    @Column(name = "tax_year", nullable = false)
    private Integer taxYear;

    @NotNull
    @Max(value = 150)
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull
    @Column(name = "total_earnings", nullable = false)
    private Long totalEarnings;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "frequency", nullable = false)
    private Frequency frequency;

    @NotNull
    @Max(value = 30)
    @Column(name = "number_of_medical_aid_members", nullable = false)
    private Integer numberOfMedicalAidMembers;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTaxYear() {
        return taxYear;
    }

    public TaxInformation taxYear(Integer taxYear) {
        this.taxYear = taxYear;
        return this;
    }

    public void setTaxYear(Integer taxYear) {
        this.taxYear = taxYear;
    }

    public Integer getAge() {
        return age;
    }

    public TaxInformation age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getTotalEarnings() {
        return totalEarnings;
    }

    public TaxInformation totalEarnings(Long totalEarnings) {
        this.totalEarnings = totalEarnings;
        return this;
    }

    public void setTotalEarnings(Long totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public TaxInformation frequency(Frequency frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public Integer getNumberOfMedicalAidMembers() {
        return numberOfMedicalAidMembers;
    }

    public TaxInformation numberOfMedicalAidMembers(Integer numberOfMedicalAidMembers) {
        this.numberOfMedicalAidMembers = numberOfMedicalAidMembers;
        return this;
    }

    public void setNumberOfMedicalAidMembers(Integer numberOfMedicalAidMembers) {
        this.numberOfMedicalAidMembers = numberOfMedicalAidMembers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaxInformation)) {
            return false;
        }
        return id != null && id.equals(((TaxInformation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TaxInformation{" +
            "id=" + getId() +
            ", taxYear=" + getTaxYear() +
            ", age=" + getAge() +
            ", totalEarnings=" + getTotalEarnings() +
            ", frequency='" + getFrequency() + "'" +
            ", numberOfMedicalAidMembers=" + getNumberOfMedicalAidMembers() +
            "}";
    }
}
