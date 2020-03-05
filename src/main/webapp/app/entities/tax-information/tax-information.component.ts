import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaxInformation } from 'app/shared/model/tax-information.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TaxInformationService } from './tax-information.service';
import { TaxInformationDeleteDialogComponent } from './tax-information-delete-dialog.component';

@Component({
  selector: 'jhi-tax-information',
  templateUrl: './tax-information.component.html'
})
export class TaxInformationComponent implements OnInit, OnDestroy {
  taxInformations?: ITaxInformation[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected taxInformationService: TaxInformationService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.taxInformationService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ITaxInformation[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInTaxInformations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITaxInformation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTaxInformations(): void {
    this.eventSubscriber = this.eventManager.subscribe('taxInformationListModification', () => this.loadPage());
  }

  delete(taxInformation: ITaxInformation): void {
    const modalRef = this.modalService.open(TaxInformationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taxInformation = taxInformation;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ITaxInformation[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/tax-information'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.taxInformations = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
