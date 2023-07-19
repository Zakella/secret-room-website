import {CartItem} from "../model/cart-item";
import {BehaviorSubject, Subject} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  public cartItems: BehaviorSubject<CartItem[]> = new BehaviorSubject<CartItem[]>([]);

  public totalAmount: Subject<number> = new Subject<number>();
  public totalQuantity: Subject<number> = new Subject<number>();

  public cartModified: Subject<boolean> = new Subject<boolean>();

  private readonly storageKey = 'cartItems';
  public sidebarVisible: Subject<boolean> = new Subject<boolean>();

  constructor() {
    this.cartItems.next(this.loadCartItemsFromStorage());
  }

  getCartItems(): CartItem[] {
    return [...this.cartItems.value];
  }

  addToCart(theCartItem: CartItem) {
    const currentValue = this.cartItems.value;
    this.cartItems.next([...currentValue, theCartItem]);
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

    for (let currentCartItem of this.cartItems.value) {
      totalAmountValue += this.calculateCartItemAmount(currentCartItem);
      totalQuantityValue += currentCartItem.quantity;
      currentCartItem.amount = this.calculateCartItemAmount(currentCartItem);
    }

    this.totalAmount.next(totalAmountValue);
    this.totalQuantity.next(totalQuantityValue);
  }

  private calculateCartItemAmount(cartItem: CartItem): number {
    return cartItem.quantity * (cartItem.product.unitPrice !== undefined ? cartItem.product.unitPrice : 0);
  }

  private saveCartItemsToStorage() {
    try {
      const cartItemsJson = JSON.stringify(this.cartItems.value);
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

  deleteItemFromCart(cartItem: CartItem,  index:number) {
    const currentValue = this.cartItems.value;
    currentValue.splice(index, 1);
    this.cartItems.next(currentValue);
    this.saveCartItemsToStorage();
    this.computeCartTotals();
    this.cartModified.next(true);
  }

  recalculateCartItem(cartItem: CartItem, index: number) {
    const currentValue = this.cartItems.value;
    currentValue[index] = cartItem;
    this.cartItems.next(currentValue);
    this.saveCartItemsToStorage();
    this.computeCartTotals();
    this.cartModified.next(true);
  }
}
