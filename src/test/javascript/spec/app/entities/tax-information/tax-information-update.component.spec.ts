import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PsiberAssessmentTestModule } from '../../../test.module';
import { TaxInformationUpdateComponent } from 'app/entities/tax-information/tax-information-update.component';
import { TaxInformationService } from 'app/entities/tax-information/tax-information.service';
import { TaxInformation } from 'app/shared/model/tax-information.model';

describe('Component Tests', () => {
  describe('TaxInformation Management Update Component', () => {
    let comp: TaxInformationUpdateComponent;
    let fixture: ComponentFixture<TaxInformationUpdateComponent>;
    let service: TaxInformationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PsiberAssessmentTestModule],
        declarations: [TaxInformationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TaxInformationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaxInformationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaxInformationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaxInformation(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaxInformation();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
