import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PsiberAssessmentSharedModule } from 'app/shared/shared.module';
import { TaxInformationComponent } from './tax-information.component';
import { TaxInformationDetailComponent } from './tax-information-detail.component';
import { TaxInformationUpdateComponent } from './tax-information-update.component';
import { TaxInformationDeleteDialogComponent } from './tax-information-delete-dialog.component';
import { taxInformationRoute } from './tax-information.route';

@NgModule({
  imports: [PsiberAssessmentSharedModule, RouterModule.forChild(taxInformationRoute)],
  declarations: [
    TaxInformationComponent,
    TaxInformationDetailComponent,
    TaxInformationUpdateComponent,
    TaxInformationDeleteDialogComponent
  ],
  entryComponents: [TaxInformationDeleteDialogComponent]
})
export class PsiberAssessmentTaxInformationModule {}
