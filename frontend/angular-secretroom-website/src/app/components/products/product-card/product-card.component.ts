import { Component, OnInit } from '@angular/core';
import { ProductService } from "../../../services/products-services/product.service";
import { Product } from "../../../model/product/Product";
import {ActivatedRoute} from "@angular/router";


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
            this.currentSize = '';
          }


        },
        error:(err)=>{console.log(err)}
      }
    )

  }

  addProductInCart() {

  }
}
