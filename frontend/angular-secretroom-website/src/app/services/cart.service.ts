import {Injectable, OnInit} from '@angular/core';
import {CartItem} from "../model/cart-item";
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  cartItems: CartItem[] = [];

  totalAmount: Subject<number> = new Subject<number>();
  totalQuantity: Subject<number> = new Subject<number>();

  cartModified : Subject<boolean> = new Subject<boolean>();

  private readonly storageKey = 'cartItems';

  constructor() {
    this.cartItems = this.loadCartItemsFromStorage();
  }


  addToCart(theCartItem: CartItem) {

    this.cartItems.push(theCartItem);
    this.saveCartItemsToStorage();
    this.computeCartTotals(this.cartItems);

  }


  computeCartTotals(cartItems: CartItem[]) {

    let totalAmountValue: number = 0;
    let totalQuantityValue: number = 0;


    for (let currentCartItem of cartItems) {
      totalAmountValue += currentCartItem.quantity * (currentCartItem.product.unitPrice || 0);
      totalQuantityValue += currentCartItem.quantity;
    }

    // publish the new values ... all subscribers will receive the new data
    this.totalAmount.next(totalAmountValue);
    this.totalQuantity.next(totalQuantityValue);

  }


  private saveCartItemsToStorage() {
    const cartItemsJson = JSON.stringify(this.cartItems);
    localStorage.setItem(this.storageKey, cartItemsJson);
    this.cartModified.next(true);
  }

  loadCartItemsFromStorage(): CartItem[] {
    const cartItemsJson = localStorage.getItem(this.storageKey);
    if (cartItemsJson) {
      return JSON.parse(cartItemsJson);
    }
    return [];
  }

  deleteItemFromCart(cartItem: CartItem ){
    this.cartItems = this.cartItems.filter(item => item !== cartItem);
    this.saveCartItemsToStorage();
    this.computeCartTotals(this.cartItems);

    this.cartModified.next(true);

  }

  recalculateCartItem(cartItem: CartItem ){

    cartItem.amount = cartItem.quantity * (cartItem.product.unitPrice || 0);
    this.saveCartItemsToStorage();
    this.computeCartTotals(this.cartItems);

    this.cartModified.next(true);

  }


}
