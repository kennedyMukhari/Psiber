import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TaxInformationService } from 'app/entities/tax-information/tax-information.service';
import { ITaxInformation, TaxInformation } from 'app/shared/model/tax-information.model';
import { Frequency } from 'app/shared/model/enumerations/frequency.model';

describe('Service Tests', () => {
  describe('TaxInformation Service', () => {
    let injector: TestBed;
    let service: TaxInformationService;
    let httpMock: HttpTestingController;
    let elemDefault: ITaxInformation;
    let expectedResult: ITaxInformation | ITaxInformation[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TaxInformationService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TaxInformation(0, 0, 0, 0, Frequency.ANNUAL, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TaxInformation', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TaxInformation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TaxInformation', () => {
        const returnedFromService = Object.assign(
          {
            taxYear: 1,
            age: 1,
            totalEarnings: 1,
            frequency: 'BBBBBB',
            numberOfMedicalAidMembers: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TaxInformation', () => {
        const returnedFromService = Object.assign(
          {
            taxYear: 1,
            age: 1,
            totalEarnings: 1,
            frequency: 'BBBBBB',
            numberOfMedicalAidMembers: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TaxInformation', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
