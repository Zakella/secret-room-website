import {Component, Input, OnInit, OnDestroy} from '@angular/core';
import {ProductService} from '../../../services/product.service';
import {Product} from '../../../model/product';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {PaginatorState} from 'primeng/paginator';
import {LanguageService} from "../../../services/language.service";


@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})

export class ProductListComponent implements OnInit {

  products!: Product[];

  brand: string = '';

  currentPage: number = 0;
  rows: number = 10;
  totalRecords: number = 0;

  groupId: string | null = "";
  currentLang: string;

  constructor(
    private productService: ProductService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private languageService: LanguageService
  ) {
    this.currentLang = 'Ro';
  }

  ngOnInit(): void {
    this.subscribeToLanguageChanges();
    this.loadProducts();
  }

  private loadProducts(): void {
    this.brand = this.getBrandFromUrl();

    this.groupId = this.activatedRoute.snapshot.paramMap.get('groupId')
    this.groupId ? this.loadProductsByGroupId(this.groupId) : this.loadAllProductsByBrandRoute();

    const keyword = this.activatedRoute.snapshot.paramMap.get('keyword')
    if (keyword) {
      this.searchForProductsByKeyword(keyword);
    }
  }

  private getBrandFromUrl(): string {
    return this.router.url.includes('vs') ? 'vs' : 'bb';
  }
  private subscribeToLanguageChanges(): void {
    this.languageService.language$.subscribe(lang => {
      this.currentLang = lang;
    });
  }

  searchForProductsByKeyword(keyword: string): void {
    const brand = this.router.url.includes('vs') ? 'vs' : 'bb';
    if (keyword) {
      this.productService.search(keyword, brand, this.currentPage, this.rows).subscribe(
        (data) => {
          if (data) {
            this.products = data.content;
            this.totalRecords = data.totalElements;
          } else {
            this.router.navigate([brand + '/product-not-found']);
          }

        })
    } else {
      this.products = [];
    }


  }

  loadAllProductsByBrandRoute(): void {

    this.activatedRoute.snapshot.url.map(currentUrl => {
        const currentPath = currentUrl.path;

        if (currentPath) {
          if (currentPath === 'all-vs-products') {
            this.getAllProducts("vs")
          }
        }

        if (currentPath) {
          if (currentPath === 'all-bb-products') {
            this.getAllProducts("bb")
          }
        }

      }
    )
  }

  getAllProducts(brand: string) {
    this.productService.getAllProductsByBrand(brand, this.currentPage, this.rows)
      .subscribe((data) => {
        this.products = data.content;
        this.totalRecords = data.totalElements;

      });
  }


  onPageChange(event: PaginatorState) {
    this.currentPage = event.page || 0;
    this.rows = event.rows || 10;

    this.groupId ? this.loadProductsByGroupId(this.groupId) : this.loadAllProductsByBrandRoute();
  }

  private loadProductsByGroupId(groupId: string) {

    this.productService.getProductsByGroupId(groupId, this.currentPage, this.rows)
      .subscribe(
        (data) => {
          this.products = data.content;
          this.totalRecords = data.totalElements;
        }
      );
  }
}
