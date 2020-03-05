import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PsiberAssessmentTestModule } from '../../../test.module';
import { TaxInformationDetailComponent } from 'app/entities/tax-information/tax-information-detail.component';
import { TaxInformation } from 'app/shared/model/tax-information.model';

describe('Component Tests', () => {
  describe('TaxInformation Management Detail Component', () => {
    let comp: TaxInformationDetailComponent;
    let fixture: ComponentFixture<TaxInformationDetailComponent>;
    const route = ({ data: of({ taxInformation: new TaxInformation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PsiberAssessmentTestModule],
        declarations: [TaxInformationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TaxInformationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaxInformationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taxInformation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taxInformation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
