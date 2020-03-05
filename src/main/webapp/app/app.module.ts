import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { PsiberAssessmentSharedModule } from 'app/shared/shared.module';
import { PsiberAssessmentCoreModule } from 'app/core/core.module';
import { PsiberAssessmentAppRoutingModule } from './app-routing.module';
import { PsiberAssessmentHomeModule } from './home/home.module';
import { PsiberAssessmentEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    PsiberAssessmentSharedModule,
    PsiberAssessmentCoreModule,
    PsiberAssessmentHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    PsiberAssessmentEntityModule,
    PsiberAssessmentAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class PsiberAssessmentAppModule {}
