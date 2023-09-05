import { Component, OnInit } from '@angular/core';
import { ProductService } from "../../../services/product.service";
import { Product } from "../../../model/product";
import {ActivatedRoute} from "@angular/router";
import {CartService} from "../../../services/cart.service";
import {CartItem} from "../../../model/cart-item";
import {Size} from "../../../model/sizes";
import {LocationStrategy} from "@angular/common";
import {LanguageService} from "../../../services/language.service";


@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css']
})
export class ProductCardComponent implements OnInit {
  product: Product = {};
  mainImage:string | undefined;
  currentSize:string | undefined;
  quantity: number = 1;
  currentLang: string;
  constructor(private productService: ProductService,
              private route: ActivatedRoute,
              private cartService: CartService,
              private location: LocationStrategy,
              private languageService: LanguageService) {

    this.currentLang = 'Ro';
  }

  ngOnInit(): void {
    const productId: string = this.route.snapshot.paramMap.get('id')!;
    this.productService.getProductById(productId).subscribe(
      {
        next: (data)=>{
          this.product = data;
          this.mainImage = this.product.imageURL;
          if (this.product.productSizes && this.product.productSizes.length > 0) {
            this.currentSize = this.product.productSizes[0].sizeType;
          }
        },
        error:(err)=> {
          console.log(err);
        }
      }
    )

    this.languageService.language$.subscribe(lang => {
      this.currentLang = lang;
    });
  }

  addProductInCart() {
    let cartItem: CartItem;

    if (this.product.productSizes && this.product.productSizes.length > 0) {
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
    // cartItem.id = uuid();
    this.cartService.addToCart(cartItem);
    this.cartService.setSidebarVisible(true);
  }

  goBack() {
    this.location.back();
  }
}
