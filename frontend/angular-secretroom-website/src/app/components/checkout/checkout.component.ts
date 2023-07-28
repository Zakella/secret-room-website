import {Component, OnDestroy, OnInit} from '@angular/core';
import {CartItem} from "../../model/cart-item";
import {CartService} from "../../services/cart.service";
import {FormBuilder, ValidatorFn, Validators} from "@angular/forms";
import {combineLatest, Subject, throwError} from "rxjs";
import {takeUntil} from "rxjs/operators";
import {Router} from "@angular/router";
import {ShippingOption} from "../../model/shipping-option";
import {ShippingService} from "../../services/shipping.service";
import {Purchase} from "../../model/purchase";
import {PurchaseService} from "../../services/purchase.service";
import {Customer} from "../../model/customer";
import {Address} from "../../model/address";
import {Order} from "../../model/order";
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})


export class CheckoutComponent implements OnInit, OnDestroy {


  cartItems: CartItem[] = [];

  totalAmount: number = 0;

  private ngUnsubscribe = new Subject<void>();

  nameValidator: ValidatorFn[] = [
    Validators.required,
    Validators.minLength(3),
    Validators.pattern('^[A-Za-z\\s]*$')
  ];

  shippingOptions: ShippingOption[] = [];

  shippingCost: number = 0;

  form = this.fb.group({
    email: ["", [Validators.pattern(/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/)]],
    country: {value: "Moldova, Republic of", disabled: true},
    name: ["", this.nameValidator],
    checked: this.fb.control(true),
    selectedValue: ["", Validators.required],
    lastname: ["", this.nameValidator],
    address: ["", [Validators.required, Validators.minLength(3)]],
    city: ["", this.nameValidator],
    zip: ["", [Validators.required, Validators.minLength(4)]],
    phone: ["", [Validators.required, Validators.pattern(/^(06|07)\d{7}$/)]],
    payment: "",
    delivery: "",
    comment: ""
  });


  constructor(private cartService: CartService,
              private fb: FormBuilder,
              private router: Router,
              private purchaseService: PurchaseService,
              private shippingService: ShippingService,
              private messageService: MessageService
  ) {
  }

  ngOnInit(): void {
    this.cartItems = this.cartService.loadCartItemsFromStorage();
    this.cardModified();
    this.getTotalAmount();
    this.subscribeToCartItems();
    this.subscribeToShippingOption();
  }

  getTotalAmount() {
    this.cartService.totalAmount.subscribe(
      (data) => {
        this.totalAmount = data;
      }
    )
  }

  cardModified() {
    this.cartService.cartModified.subscribe(
      (data) => {
        if (data) {
          this.cartItems = this.cartService.cartItems.value;
        }
      }
    )
  }

  getDeliveryDate(minDays: number, maxDays: number): string {
    const current = new Date();
    const formatter = new Intl.DateTimeFormat('en', {day: '2-digit', month: '2-digit', year: 'numeric'});

    const startDeliveryDate = formatter.format(new Date(current.getFullYear(), current.getMonth(), current.getDate() + minDays));
    const endDeliveryDate = formatter.format(new Date(current.getFullYear(), current.getMonth(), current.getDate() + maxDays));

    return `${startDeliveryDate} - ${endDeliveryDate}`;
  }


  placeOrder() {


    let purchase = new Purchase(
      this.createCustomer(),
      this.createShippingAddress(),
      this.createOrder(),
      this.createOrderItems()
    );

    // const order = this.createOrder();

    console.log(purchase);

    this.purchaseService.placeOrder(purchase).subscribe(
      {
        next: response => {
          console.log(response.orderTrackingNumber);
          // this.fb.group({).reset();
          this.cartService.clearCart();
          this.router.navigate([`/order-success/${response.orderTrackingNumber}`]);
        },
        error: error => {
          if (error.status === 0) {
            // A client-side or network error occurred. Handle it accordingly.
            console.error('Server is not responding. Please try again later.');
          } else {
            // The backend returned an unsuccessful response code.
            // The response body may contain clues as to what went wrong.
            console.error(
              `Backend returned code ${error.status}, body was: `, error.error);
            this.showError("Server error, try to refresh the page or contact us");
          }
          // Return an observable with a user-facing error message.
          return throwError(() => new Error('Something bad happened; please try again later.'));
        }

      });


  }


  showError(summary: string) {
    this.messageService.add(
      {
        severity: 'error',
        summary: summary,
        icon: "pi pi-times-circle"
      }
    );
  }


  private createOrder() {

    // Calculate the total amount and total quantity from the cart items
    const totalAmount = this.totalAmount;
    const orderQuantity = this.cartItems.reduce(
      (total, cartItem) => total + cartItem.quantity, 0);

    // Get the selected shipping option
    const selectedShippingOptionId = this.form.get('selectedValue')?.value ?? '';
    const selectedOption = this.shippingOptions.find(option => option.id === selectedShippingOptionId);

    const delivery = selectedOption?.cost || 0
    // Create a default shipping option if selectedOption is undefined


    // Create the order object
    return new Order(
      new Date(), // placementDate (current date)
      selectedOption,
      delivery,
      orderQuantity,
      totalAmount,
      totalAmount + delivery,
    );

  }


  setShippingOptions(id: string) {

    this.form.controls.selectedValue.setValue(id);

    let selectedOption = this.shippingOptions.find(option => option.id === id);

    this.shippingCost = selectedOption?.cost || 0;

  }

  private subscribeToCartItems() {
    combineLatest([this.cartService.totalAmount, this.cartService.cartModified]).pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      ([totalAmount, cartModified]) => {
        if (cartModified) {
          this.cartItems = this.cartService.cartItems.value;
        }
        this.totalAmount = totalAmount;
        if (this.cartItems.length === 0) {
          this.router.navigate(['empty-cart']);
        }
      }
    );
  }


  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  onSubmit($event: any) {
    console.log(this.form.value);
    if (this.form.valid) {
      this.placeOrder();
    }
  }


  private subscribeToShippingOption() {
    this.shippingService.getShippingOptions()
      .pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      {
        next: response => {
          this.shippingOptions = response.map(option => {
            option.description = "Delivery: " + this.getDeliveryDate(option.expectedDeliveryFrom, option.expectedDeliveryTo);
            return option;

          })

        },
        error: error => {
          console.log(error);
        }
      }
    );
  }

  private createOrderItems() {

    return this.cartItems.map(cartItem => {
        const product = cartItem.product;
        const unitPrice = product?.unitPrice ?? 0;
        return {
          product,
          size: cartItem.size?.sizeType,
          amount: unitPrice * cartItem.quantity,
          quantity: cartItem.quantity
        };
      }
    );
  }

  private createCustomer() {

    return new Customer(
      this.form.get('name')?.value ?? '',
      this.form.get('lastname')?.value ?? '',
      this.form.get('email')?.value ?? '',
      this.form.get('phone')?.value ?? '',
    );
  }

  private createShippingAddress() {
    return new Address(
      this.form.get('address')?.value ?? '',
      this.form.get('city')?.value ?? '',
      "Moldova, Republic of",
      this.form.get('zip')?.value ?? '',
    );
  }
}
