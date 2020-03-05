import { Frequency } from 'app/shared/model/enumerations/frequency.model';

export interface ITaxInformation {
  id?: number;
  taxYear?: number;
  age?: number;
  totalEarnings?: number;
  frequency?: Frequency;
  numberOfMedicalAidMembers?: number;
}

export class TaxInformation implements ITaxInformation {
  constructor(
    public id?: number,
    public taxYear?: number,
    public age?: number,
    public totalEarnings?: number,
    public frequency?: Frequency,
    public numberOfMedicalAidMembers?: number
  ) {}
}
