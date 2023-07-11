import {Component, OnDestroy, OnInit} from '@angular/core';
import {CartService} from "../../../services/cart.service";
import {takeUntil} from "rxjs/operators";
import {Subject} from "rxjs";
import {CartItem} from "../../../model/cart-item";

@Component({
  selector: 'app-product-preview',
  templateUrl: './product-preview.component.html',
  styleUrls: ['./product-preview.component.css']
})
export class ProductPreviewComponent implements OnInit{
  visible: boolean = true;
  cartItems: CartItem[] ;
  totalAmount: number = 0;

  // cartItem: CartItem = {}


  constructor(private cartService:CartService) {
    this.cartItems = cartService.loadCartItemsFromStorage();
  }

  ngOnInit(): void {

    this.cardModified();
    this.getTotalAmount();

  }


  cardModified(){
    this.cartService.cartModified.subscribe(
      (data) =>{
        if(data) {
          this.cartItems = this.cartService.cartItems;
        }
        }
    )
  }



  getTotalAmount(){
    this.cartService.totalAmount.subscribe(
      (data) =>{

          this.totalAmount = data;


      }
    )
  }


  deleteItemFromCart(cartItem:CartItem) {
    this.cartService.deleteItemFromCart(cartItem);

  }

  recalculateCartItem(cartItem:CartItem) {
    this.cartService.recalculateCartItem(cartItem);

  }
}
