package kennedy.co.za.web.rest.business.rules;

import kennedy.co.za.domain.enumeration.Frequency;
import kennedy.co.za.service.dto.TaxInformationDTO;
import kennedy.co.za.web.rest.business.rules.helpers.Percentage;

import static kennedy.co.za.domain.enumeration.Frequency.MONTHLY;

public class TwentySeventeenTaxCalculations extends TaxCalculations {
    public TwentySeventeenTaxCalculations(TaxInformationDTO taxInformationDTO) {
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

            if (totalEarnings >= 0L && totalEarnings <= 188000L) {
                percentage = new Percentage(totalEarnings, 18);
                Long percentageValue = percentage.calculatePercentageValue();

                taxableIncome = percentageValue;
            } else if (totalEarnings >= 188001L && totalEarnings <= 293600L) {
                lowerRangeTaxBracketUpperBound = 188000L;
                taxableIncomeRemainder = totalEarnings - lowerRangeTaxBracketUpperBound;
                percentage = new Percentage(taxableIncomeRemainder, 26);
                Long percentageValue = percentage.calculatePercentageValue();

                taxableIncome = percentageValue;
            } else if (totalEarnings >= 293601L && totalEarnings <= 406400L) {
                lowerRangeTaxBracketUpperBound = 293600L;
                taxableIncomeRemainder = totalEarnings - lowerRangeTaxBracketUpperBound;
                percentage = new Percentage(taxableIncomeRemainder, 31);
                Long percentageValue = percentage.calculatePercentageValue();

                taxableIncome = percentageValue;
            } else if (totalEarnings >= 406401L && totalEarnings <= 550100L) {
                lowerRangeTaxBracketUpperBound = 406400L;
                taxableIncomeRemainder = totalEarnings - lowerRangeTaxBracketUpperBound;
                percentage = new Percentage(taxableIncomeRemainder, 36);
                Long percentageValue = percentage.calculatePercentageValue();

                taxableIncome = percentageValue;
            } else if (totalEarnings >= 550101L && totalEarnings <= 701300L) {
                lowerRangeTaxBracketUpperBound = 550100L;
                taxableIncomeRemainder = totalEarnings - lowerRangeTaxBracketUpperBound;
                percentage = new Percentage(taxableIncomeRemainder, 39);
                Long percentageValue = percentage.calculatePercentageValue();

                taxableIncome = percentageValue;
            } else if (totalEarnings >= 701301L) {
                lowerRangeTaxBracketUpperBound = 701300L;
                taxableIncomeRemainder = totalEarnings - lowerRangeTaxBracketUpperBound;
                percentage = new Percentage(taxableIncomeRemainder, 41);
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

            if (totalEarnings >= 0L && totalEarnings <= 188000L) {
                taxRate = taxableIncome;
            } else if (totalEarnings >= 188001L && totalEarnings <= 293600L) {
                taxRate = 33840L + taxableIncome;
            } else if (totalEarnings >= 293601L && totalEarnings <= 406400L) {
                taxRate = 61296L + taxableIncome;
            } else if (totalEarnings >= 406401L && totalEarnings <= 550100L) {
                taxRate = 96264L + taxableIncome;
            } else if (totalEarnings >= 550101L && totalEarnings <= 701300L) {
                taxRate = 147996L + taxableIncome;
            } else if (totalEarnings >= 701301L) {
                taxRate = 206964L + taxableIncome;
            }
        }
    }

    @Override
    public void calculateTaxMedicalAidCost() {
        if (taxInformationDTO != null) {
            Integer numberOfMedicalAidMembers = taxInformationDTO.getNumberOfMedicalAidMembers();
            String taxPayer = String.valueOf(TwentySeventeenTaxCredits.TAX_PAYER);
            String firstDependent = String.valueOf(TwentySeventeenTaxCredits.FIRST_DEPENDENT);
            String additional = String.valueOf(TwentySeventeenTaxCredits.ADDITIONAL);

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
