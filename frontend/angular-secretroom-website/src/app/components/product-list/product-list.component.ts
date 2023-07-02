import {Component, Input, OnInit} from '@angular/core';
import {ProductService} from "../../services/product.service";
import {Product} from "../../model/product/product";
import {ActivatedRoute} from "@angular/router";
import {Observable, switchMap} from "rxjs";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit{

  @Input()
  // categoryName:String;
  products!: Observable<Product[]>;
  constructor(private productService : ProductService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.products = this.route.paramMap.pipe(
      switchMap(params => {
        const categoryIdParam = params.get('id');
        if (categoryIdParam==="all-vs-products"){
          return this.productService.getProductAllProducts('vs');
        }

        if (categoryIdParam==="all-bb-products"){
          return this.productService.getProductAllProducts('bb');
        }

        return this.productService.getProductByGroupId(categoryIdParam);
      })
    );
  }


}
