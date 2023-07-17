import {Component, OnInit, OnDestroy} from '@angular/core';
import {CartItem} from '../../../model/cart-item';
import {CartService} from '../../../services/cart.service';
import {Subscription, Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';

@Component({
  selector: 'app-my-cart',
  templateUrl: './my-cart.component.html',
  styleUrls: ['./my-cart.component.css']
})
export class MyCartComponent implements OnInit, OnDestroy {
  cartItems: CartItem[] = [];
  totalAmount: number = 0;
  totalQuantity: number = 0;
  private ngUnsubscribe = new Subject<void>();

  constructor(private cartService: CartService) {
  }

  ngOnInit(): void {
    this.loadCartItemsFromStorage();
    this.subscribeToTotalAmount();
    this.subscribeToTotalQuantity();
    this.subscribeToCartModification();
    this.cartService.computeCartTotals();
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  private loadCartItemsFromStorage() {
    this.cartItems = this.cartService.loadCartItemsFromStorage();
  }

  private subscribeToCartModification() {

    this.cartService.cartModified.subscribe(
      (data) => {
        if (data) {
          this.cartItems = this.cartService.cartItems;
        }
      }
    )

  }

  private subscribeToTotalAmount() {
    this.cartService.totalAmount.subscribe(
      (data) => {
        this.totalAmount = data;
      }
    )

  }

  private subscribeToTotalQuantity() {
    this.cartService.totalQuantity.subscribe(
      (data) => {
        this.totalQuantity = data;
      }
    )
  }

  deleteItemFromCart(cartItem: CartItem, index: number) {
    this.cartService.deleteItemFromCart(cartItem, index);
  }

  recalculateCartItem(cartItem: CartItem, index: number) {
    this.cartService.recalculateCartItem(cartItem, index);
  }
}
