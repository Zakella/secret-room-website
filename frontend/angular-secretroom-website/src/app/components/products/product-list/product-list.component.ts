import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { ProductService } from '../../../services/product.service';
import { Product } from '../../../model/product/product';
import { ActivatedRoute, Router } from '@angular/router';
import {isEmpty, Observable, of, Subscription, tap} from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { SearchService } from '../../headers/search/search.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit, OnDestroy {
  @Input() products!: Observable<Product[]>;

  private searchSubscription: Subscription | undefined;
  searchResults: string = '';

  searchMode!: boolean;

  constructor(
    private productService: ProductService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private searchService: SearchService
  ) {}

  ngOnInit(): void {
    this.searchSubscription = this.searchService.getSearchResults().subscribe(results => {
      this.searchResults = results;
      this.searchForProductsByKeyword();
    });

    this.searchMode = this.activatedRoute.snapshot.paramMap.has('keyword');
    if (this.searchMode) {
      this.searchForProductsByKeyword();
    } else {
      this.loadAllProducts();
    }
  }

  ngOnDestroy(): void {
    this.searchSubscription?.unsubscribe();
  }

  searchForProductsByKeyword(): void {
    const brand = this.router.url.includes('vs') ? 'vs' : 'bb';
    const keyword = this.activatedRoute.snapshot.paramMap.get('keyword');
    if (keyword) {
      this.products = this.productService.search(keyword, brand).pipe(
        tap((result) => {
          if (result.length === 0) {
            this.router.navigate(['product-not-found']);
          }
        })
      );
    } else {
      this.products = of([]);
    }
  }

  loadAllProducts(): void {
    this.products = this.activatedRoute.paramMap.pipe(
      switchMap(params => {
        const categoryIdParam = params.get('id');

        if (categoryIdParam === null) {
          return of([]);
        }

        if (categoryIdParam === 'all-vs-products') {
          return this.productService.getAllProductsByBrand('vs');
        }

        if (categoryIdParam === 'all-bb-products') {
          return this.productService.getAllProductsByBrand('bb');
        }

        return this.productService.getProductsByGroupId(categoryIdParam);
      })
    );
  }
}
