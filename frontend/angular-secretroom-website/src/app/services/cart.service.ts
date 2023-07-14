import {Injectable} from '@angular/core';
import {CartItem} from "../model/cart-item";
import {BehaviorSubject, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  public cartItems: CartItem[] = [];

  public totalAmount: Subject<number> = new Subject<number>();
  public totalQuantity: Subject<number> = new Subject<number>();

  public cartModified: Subject<boolean> = new Subject<boolean>();

  private readonly storageKey = 'cartItems';



  constructor() {
    this.cartItems = this.loadCartItemsFromStorage();
  }

  addToCart(theCartItem: CartItem) {
    this.cartItems.push(theCartItem);
    this.saveCartItemsToStorage();
    this.computeCartTotals();

  }


  computeCartTotals() {
    let totalAmountValue: number = 0;
    let totalQuantityValue: number = 0;

    for (let currentCartItem of this.cartItems) {
      totalAmountValue += currentCartItem.quantity * (currentCartItem.product.unitPrice !== undefined ? currentCartItem.product.unitPrice : 0);
      totalQuantityValue += currentCartItem.quantity;
    }

    this.totalAmount.next(totalAmountValue);
    this.totalQuantity.next(totalQuantityValue);
  }

  private saveCartItemsToStorage() {
    try {
      const cartItemsJson = JSON.stringify(this.cartItems);
      localStorage.setItem(this.storageKey, cartItemsJson);
      this.cartModified.next(true);
    } catch (e) {
      console.error("Error saving cart items to storage:", e);
    }
  }

  loadCartItemsFromStorage(): CartItem[] {
    try {
      const cartItemsJson = localStorage.getItem(this.storageKey);
      if (cartItemsJson) {
        return JSON.parse(cartItemsJson);
      }
    } catch (e) {
      console.error("Error loading cart items from storage:", e);
    }
    return [];
  }

  deleteItemFromCart(cartItem: CartItem) {
    this.cartItems = this.cartItems.filter(item => item !== cartItem);
    this.saveCartItemsToStorage();
    this.computeCartTotals();
    this.cartModified.next(true);
  }

  recalculateCartItem(cartItem: CartItem) {
    const updatedItem = {
      ...cartItem,
      amount: cartItem.quantity * (cartItem.product.unitPrice !== undefined ? cartItem.product.unitPrice : 0)
    };
    this.cartItems = this.cartItems.map(item => item === cartItem ? updatedItem : item);
    this.saveCartItemsToStorage();
    this.computeCartTotals();
    this.cartModified.next(true);
  }


}
