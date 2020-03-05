import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'tax-information',
        loadChildren: () => import('./tax-information/tax-information.module').then(m => m.PsiberAssessmentTaxInformationModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PsiberAssessmentEntityModule {}
