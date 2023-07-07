import { Component, OnInit } from '@angular/core';
import { ProductService } from "../../../services/product.service";
import { Observable } from "rxjs";
import { Product } from "../../../model/product/Product";
import {ActivatedRoute, Router} from "@angular/router";
import {getLocaleExtraDayPeriodRules} from "@angular/common";

interface size {
  label: string;
  value: string;
}

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css']
})
export class ProductCardComponent implements OnInit {
  product: Product = new Product();
  mainImage:string | undefined = '';
  currentSize:string | undefined = '';
  quantity: number = 1; // Updated to string type
  constructor(private productService: ProductService,
              private route: ActivatedRoute) {}

  ngOnInit(): void {

    const productId: string = this.route.snapshot.paramMap.get('id')!;
    this.productService.getProductById(productId).subscribe(
      {
        next: (data)=>{
          this.product = data;
          this.mainImage = this.product.imageURL;
          console.log(this.product.productSizes)
          if (this.product.productSizes && this.product.productSizes.length > 0) {
            this.currentSize = this.product.productSizes[0].sizeType;
          } else {
            this.currentSize = ''; // Assign an empty string if productSizes is empty
          }

          // console.log(this.product.productImages)


        },
        error:(err)=>{console.log(err)}
      }
    )


  }

  protected readonly Product = Product;
}
