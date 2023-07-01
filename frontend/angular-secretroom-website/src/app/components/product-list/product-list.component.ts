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
          const categoryId = parseInt(params.get('id')!, 10);
        return this.productService.getProductByGroupId(categoryId);
      })
    );
  }


}
