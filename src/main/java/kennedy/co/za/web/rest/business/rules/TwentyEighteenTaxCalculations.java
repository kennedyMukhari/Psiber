package kennedy.co.za.web.rest.business.rules;

import kennedy.co.za.domain.enumeration.Frequency;
import kennedy.co.za.service.dto.TaxInformationDTO;
import kennedy.co.za.web.rest.business.rules.helpers.Percentage;

import static kennedy.co.za.domain.enumeration.Frequency.MONTHLY;

public class TwentyEighteenTaxCalculations extends TaxCalculations {
    public TwentyEighteenTaxCalculations(TaxInformationDTO taxInformationDTO) {
        super(taxInformationDTO);
    }

    @Override
    public void calculateTaxableIncome() {
        Long lowerRangeTaxBracketUpperBound;
        Long taxableIncomeRemainder;
        Percentage percentage;
        if (taxInformationDTO != null) {
            Long totalEarnings = taxInformationDTO.getTotalEarnings();
            Frequency frequency = taxInformationDTO.getFrequency();
            if (frequency == MONTHLY) {
                totalEarnings = totalEarnings * 12;
            }

            if (totalEarnings >= 0L && totalEarnings <= 189880L) {
                percentage = new Percentage(totalEarnings, 18);
                Long percentageValue = percentage.calculatePercentageValue();

                taxableIncome = percentageValue;
            } else if (totalEarnings >= 189881L && totalEarnings <= 296540L) {
                lowerRangeTaxBracketUpperBound = 189880L;
                taxableIncomeRemainder = totalEarnings - lowerRangeTaxBracketUpperBound;
                percentage = new Percentage(taxableIncomeRemainder, 26);
                Long percentageValue = percentage.calculatePercentageValue();

                taxableIncome = percentageValue;
            } else if (totalEarnings >= 296541L && totalEarnings <= 410460L) {
                lowerRangeTaxBracketUpperBound = 296540L;
                taxableIncomeRemainder = totalEarnings - lowerRangeTaxBracketUpperBound;
                percentage = new Percentage(taxableIncomeRemainder, 31);
                Long percentageValue = percentage.calculatePercentageValue();

                taxableIncome = percentageValue;
            } else if (totalEarnings >= 410461L && totalEarnings <= 555600L) {
                lowerRangeTaxBracketUpperBound = 410460L;
                taxableIncomeRemainder = totalEarnings - lowerRangeTaxBracketUpperBound;
                percentage = new Percentage(taxableIncomeRemainder, 36);
                Long percentageValue = percentage.calculatePercentageValue();

                taxableIncome = percentageValue;
            } else if (totalEarnings >= 555601L && totalEarnings <= 708310L) {
                lowerRangeTaxBracketUpperBound = 555600L;
                taxableIncomeRemainder = totalEarnings - lowerRangeTaxBracketUpperBound;
                percentage = new Percentage(taxableIncomeRemainder, 39);
                Long percentageValue = percentage.calculatePercentageValue();

                taxableIncome = percentageValue;
            } else if (totalEarnings >= 708311L && totalEarnings <= 1500000L) {
                lowerRangeTaxBracketUpperBound = 708310L;
                taxableIncomeRemainder = totalEarnings - lowerRangeTaxBracketUpperBound;
                percentage = new Percentage(taxableIncomeRemainder, 41);
                Long percentageValue = percentage.calculatePercentageValue();

                taxableIncome = percentageValue;
            } else if (totalEarnings >= 1500001L) {
                lowerRangeTaxBracketUpperBound = 1500000L;
                taxableIncomeRemainder = totalEarnings - lowerRangeTaxBracketUpperBound;
                percentage = new Percentage(taxableIncomeRemainder, 45);
                Long percentageValue = percentage.calculatePercentageValue();

                taxableIncome = percentageValue;
            }
        }
    }

    @Override
    public void calculateTaxRate() {
        if (taxInformationDTO != null) {
            Long totalEarnings = taxInformationDTO.getTotalEarnings();
            Frequency frequency = taxInformationDTO.getFrequency();
            if (frequency == MONTHLY) {
                totalEarnings = totalEarnings * 12;
            }

            if (totalEarnings >= 0L && totalEarnings <= 189880L) {
                taxRate = taxableIncome;
            } else if (totalEarnings >= 189881L && totalEarnings <= 296540L) {
                taxRate = 34178L + taxableIncome;
            } else if (totalEarnings >= 296541L && totalEarnings <= 410460L) {
                taxRate = 61910 + taxableIncome;
            } else if (totalEarnings >= 410461L && totalEarnings <= 555600L) {
                taxRate = 97225L + taxableIncome;
            } else if (totalEarnings >= 555601L && totalEarnings <= 708310L) {
                taxRate = 149475L + taxableIncome;
            } else if (totalEarnings >= 708311L && totalEarnings <= 1500000L) {
                taxRate = 209032L + taxableIncome;
            } else if (totalEarnings >= 1500001L) {
                taxRate = 533625L + taxableIncome;
            }
        }
    }

    @Override
    public void calculateTaxMedicalAidCost() {
        if (taxInformationDTO != null) {
            Integer numberOfMedicalAidMembers = taxInformationDTO.getNumberOfMedicalAidMembers();
            String taxPayer = String.valueOf(TwentyEighteenTaxCredits.TAX_PAYER);
            String firstDependent = String.valueOf(TwentyEighteenTaxCredits.FIRST_DEPENDENT);
            String additional = String.valueOf(TwentyEighteenTaxCredits.ADDITIONAL);

            Long taxPayerCredit = Long.valueOf(taxPayer);
            Long firstDependentCredit = Long.valueOf(firstDependent);
            Long additionalCredit = Long.valueOf(additional);

            if (numberOfMedicalAidMembers != 0) {
                if (numberOfMedicalAidMembers == 1) {
                    medicalAidCost = taxPayerCredit;
                } else if (numberOfMedicalAidMembers == 2) {
                    medicalAidCost = taxPayerCredit + firstDependentCredit;
                } else {
                    int numberOfAdditional = numberOfMedicalAidMembers - 2;
                    medicalAidCost = taxPayerCredit + firstDependentCredit + (numberOfAdditional * additionalCredit);
                }
            } else {
                medicalAidCost = 0L;
            }
        }
    }
}
