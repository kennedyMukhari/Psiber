import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITaxInformation, TaxInformation } from 'app/shared/model/tax-information.model';
import { TaxInformationService } from './tax-information.service';

@Component({
  selector: 'jhi-tax-information-update',
  templateUrl: './tax-information-update.component.html'
})
export class TaxInformationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    taxYear: [null, [Validators.required, Validators.min(2017), Validators.max(2018)]],
    age: [null, [Validators.required, Validators.max(150)]],
    totalEarnings: [null, [Validators.required]],
    frequency: [null, [Validators.required]],
    numberOfMedicalAidMembers: [null, [Validators.required, Validators.max(30)]]
  });

  constructor(protected taxInformationService: TaxInformationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taxInformation }) => {
      this.updateForm(taxInformation);
    });
  }

  updateForm(taxInformation: ITaxInformation): void {
    this.editForm.patchValue({
      id: taxInformation.id,
      taxYear: taxInformation.taxYear,
      age: taxInformation.age,
      totalEarnings: taxInformation.totalEarnings,
      frequency: taxInformation.frequency,
      numberOfMedicalAidMembers: taxInformation.numberOfMedicalAidMembers
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taxInformation = this.createFromForm();
    if (taxInformation.id !== undefined) {
      this.subscribeToSaveResponse(this.taxInformationService.update(taxInformation));
    } else {
      this.subscribeToSaveResponse(this.taxInformationService.create(taxInformation));
    }
  }

  private createFromForm(): ITaxInformation {
    return {
      ...new TaxInformation(),
      id: this.editForm.get(['id'])!.value,
      taxYear: this.editForm.get(['taxYear'])!.value,
      age: this.editForm.get(['age'])!.value,
      totalEarnings: this.editForm.get(['totalEarnings'])!.value,
      frequency: this.editForm.get(['frequency'])!.value,
      numberOfMedicalAidMembers: this.editForm.get(['numberOfMedicalAidMembers'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaxInformation>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
