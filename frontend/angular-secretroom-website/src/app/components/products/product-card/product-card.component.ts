import { Component, OnInit } from '@angular/core';
import { ProductService } from "../../../services/products-services/product.service";
import { Product } from "../../../model/product/Product";
import {ActivatedRoute} from "@angular/router";
import {CartService} from "../../../services/cart.service";
import {CartItem} from "../../../model/cart-item";
import {Size} from "../../../model/product/Sizes";


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
  product: Product = {};
  mainImage:string | undefined = '';
  currentSize:string | undefined = '';
  quantity: number = 1; // Updated to string type
  visible: boolean = true;
  constructor(private productService: ProductService,
              private route: ActivatedRoute,
              private cartService: CartService ) {}

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

    let cartItem: CartItem;

    if (this.product.productSizes !== undefined && this.product.productSizes.length > 0) {

      if (!this.currentSize || this.currentSize === '') {
        return;
      }

      const size: Size = {
        sizeType: this.currentSize,
        available: true
      };

      cartItem = new CartItem(this.product, this.quantity, size);
    } else {
      cartItem = new CartItem(this.product, this.quantity);
    }

    this.cartService.addToCart(cartItem);
  }


}

