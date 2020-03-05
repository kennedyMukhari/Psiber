import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITaxInformation } from 'app/shared/model/tax-information.model';

type EntityResponseType = HttpResponse<ITaxInformation>;
type EntityArrayResponseType = HttpResponse<ITaxInformation[]>;

@Injectable({ providedIn: 'root' })
export class TaxInformationService {
  public resourceUrl = SERVER_API_URL + 'api/tax-informations';

  constructor(protected http: HttpClient) {}

  create(taxInformation: ITaxInformation): Observable<EntityResponseType> {
    return this.http.post<ITaxInformation>(this.resourceUrl, taxInformation, { observe: 'response' });
  }

  update(taxInformation: ITaxInformation): Observable<EntityResponseType> {
    return this.http.put<ITaxInformation>(this.resourceUrl, taxInformation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITaxInformation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITaxInformation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
