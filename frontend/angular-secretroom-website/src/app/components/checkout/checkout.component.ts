import {Component, OnInit} from '@angular/core';
import {CartItem} from "../../model/cart-item";
import {CartService} from "../../services/cart.service";

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent  implements OnInit{

  selectedValue: string = '';

  quantities1: number[] = [1, 1, 1];

  checked: boolean = true;

  cartItems: CartItem[] = [];

  totalAmount: number = 0;

  constructor(private cartService :  CartService) {

  }

  ngOnInit(): void {
    this.cartItems = this.cartService.loadCartItemsFromStorage();

    this.cardModified();
    this.getTotalAmount();
  }


  getTotalAmount(){
    this.cartService.totalAmount.subscribe(
      (data) =>{
        this.totalAmount = data;
      }
    )
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


}


