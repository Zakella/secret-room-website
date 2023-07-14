import {CartItem} from "../model/cart-item";
import {Subject} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  public cartItems: CartItem[] = [];

  public totalAmount: Subject<number> = new Subject<number>();
  public totalQuantity: Subject<number> = new Subject<number>();

  public cartModified: Subject<boolean> = new Subject<boolean>();

  private readonly storageKey = 'cartItems';
  public sidebarVisible: Subject<boolean> = new Subject<boolean>();

  constructor() {
    this.cartItems = this.loadCartItemsFromStorage();
  }

  getCartItems(): CartItem[] {
    return [...this.cartItems];
  }

  addToCart(theCartItem: CartItem) {
    this.cartItems.push(theCartItem);
    this.saveCartItemsToStorage();
    this.computeCartTotals();
    this.setSidebarVisible(true);
  }

  setSidebarVisible(visible: boolean) {
    this.sidebarVisible.next(visible);
  }

  computeCartTotals() {
    let totalAmountValue: number = 0;
    let totalQuantityValue: number = 0;

    for (let currentCartItem of this.cartItems) {
      totalAmountValue += this.calculateCartItemAmount(currentCartItem);
      totalQuantityValue += currentCartItem.quantity;
    }

    this.totalAmount.next(totalAmountValue);
    this.totalQuantity.next(totalQuantityValue);
  }

  private calculateCartItemAmount(cartItem: CartItem): number {
    return cartItem.quantity * (cartItem.product.unitPrice !== undefined ? cartItem.product.unitPrice : 0);
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
      amount: this.calculateCartItemAmount(cartItem)
    };
    this.cartItems = this.cartItems.map(item => item === cartItem ? updatedItem : item);
    this.saveCartItemsToStorage();
    this.computeCartTotals();
    this.cartModified.next(true);
  }
}
