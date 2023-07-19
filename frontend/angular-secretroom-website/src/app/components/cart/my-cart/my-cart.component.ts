import {Component, OnInit, OnDestroy, Input } from '@angular/core';
import {CartItem} from '../../../model/cart-item';
import {CartService} from '../../../services/cart.service';
import {Subject} from 'rxjs';
import { takeUntil } from 'rxjs/operators';

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

  @Input()
  shippingCost: number = 0;

  constructor(private cartService: CartService) {
  }

  ngOnInit(): void {
    this.subscribeToCartItems();
    this.subscribeToTotalAmount();
    this.subscribeToTotalQuantity();
    this.subscribeToCartModification();
    this.cartService.computeCartTotals();
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  private subscribeToCartItems() {
    this.cartService.cartItems.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (items) => {
        this.cartItems = items;
      }
    );
  }

  private subscribeToCartModification() {
    this.cartService.cartModified.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (data) => {
        if (data) {
          this.cartService.computeCartTotals();
        }
      }
    )
  }

  private subscribeToTotalAmount() {
    this.cartService.totalAmount.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (data) => {
        this.totalAmount = data;
      }
    )

  }

  private subscribeToTotalQuantity() {
    this.cartService.totalQuantity.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
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
