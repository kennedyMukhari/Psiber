import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaxInformation } from 'app/shared/model/tax-information.model';

@Component({
  selector: 'jhi-tax-information-detail',
  templateUrl: './tax-information-detail.component.html'
})
export class TaxInformationDetailComponent implements OnInit {
  taxInformation: ITaxInformation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taxInformation }) => (this.taxInformation = taxInformation));
  }

  previousState(): void {
    window.history.back();
  }
}
