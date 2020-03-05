import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITaxInformation, TaxInformation } from 'app/shared/model/tax-information.model';
import { TaxInformationService } from './tax-information.service';
import { TaxInformationComponent } from './tax-information.component';
import { TaxInformationDetailComponent } from './tax-information-detail.component';
import { TaxInformationUpdateComponent } from './tax-information-update.component';

@Injectable({ providedIn: 'root' })
export class TaxInformationResolve implements Resolve<ITaxInformation> {
  constructor(private service: TaxInformationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaxInformation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((taxInformation: HttpResponse<TaxInformation>) => {
          if (taxInformation.body) {
            return of(taxInformation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaxInformation());
  }
}

export const taxInformationRoute: Routes = [
  {
    path: '',
    component: TaxInformationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'TaxInformations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TaxInformationDetailComponent,
    resolve: {
      taxInformation: TaxInformationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TaxInformations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TaxInformationUpdateComponent,
    resolve: {
      taxInformation: TaxInformationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TaxInformations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TaxInformationUpdateComponent,
    resolve: {
      taxInformation: TaxInformationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TaxInformations'
    },
    canActivate: [UserRouteAccessService]
  }
];
