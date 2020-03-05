import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaxInformation } from 'app/shared/model/tax-information.model';
import { TaxInformationService } from './tax-information.service';

@Component({
  templateUrl: './tax-information-delete-dialog.component.html'
})
export class TaxInformationDeleteDialogComponent {
  taxInformation?: ITaxInformation;

  constructor(
    protected taxInformationService: TaxInformationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taxInformationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('taxInformationListModification');
      this.activeModal.close();
    });
  }
}
