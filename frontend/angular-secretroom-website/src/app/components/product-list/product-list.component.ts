import {Component, Input, OnInit} from '@angular/core';
import {ProductService} from "../../services/product.service";
import {Product} from "../../model/product/product";
import {ActivatedRoute, Router} from "@angular/router";
import {Observable, of, Subscription, switchMap, throwError} from "rxjs";


@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  @Input() products!: Observable<Product[]>;
  private searchSubscription: Subscription | undefined;

  searchMode!: boolean;
  constructor(private productService: ProductService,
              private activatedRoute: ActivatedRoute,
              private router:Router) {
  }

  ngOnInit(): void {

    this.searchMode = this.activatedRoute.snapshot.paramMap.has('keyword');
    if (this.searchMode){
      this.loadProductsByKeyword()
    }
    else this.loadAllProducts();


  }


  loadProductsByKeyword(): void {
    const brand = this.router.url.includes('vs') ? 'vs' : 'bb';
    const keyword = this.activatedRoute.snapshot.paramMap.get("keyword")
    if (keyword){
     this.products = this.productService.search(keyword, brand)
    };

    }


  private loadAllProducts() {

      this.products = this.activatedRoute.paramMap.pipe(
        switchMap(params => {
          const categoryIdParam = params.get('id');

          if (categoryIdParam === null) {
            return of([]);
          }

          if (categoryIdParam === "all-vs-products") {
            return this.productService.getAllProductsByBrand('vs');
          }

          if (categoryIdParam === "all-bb-products") {
            return this.productService.getAllProductsByBrand('bb');
          }

          return this.productService.getProductsByGroupId(categoryIdParam);
        })
      );
    }


}
