import {Component, OnDestroy, OnInit} from '@angular/core';
import {CartItem} from "../../model/cart-item";
import {CartService} from "../../services/cart.service";
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";
import {Router} from "@angular/router";

interface ShippingOption {
  id: string;
  name: string;
  cost: number;
  description: string;
}

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})


export class CheckoutComponent implements OnInit , OnDestroy{

  // добавляем новое свойство selectedOption
  selectedOption: string | null = null;

  cartItems: CartItem[] = [];

  cartIsEmpty: boolean = false;

  totalAmount: number = 0;

  private ngUnsubscribe = new Subject<void>();

  nameValidator: Validators[] = [
    Validators.required,
    Validators.minLength(3),
    Validators.pattern('^[A-Za-z\\s]*$')
  ];

  shippingOptions: ShippingOption[] = [
    {
      id: "CR",
      name: 'Curier Rapid',
      cost: 100,
      description: "Delivery:" + this.getDeliveryDate(1, 3)
    },
    {
      id: "PM",
      name: 'Posta Moldovei',
      cost: 35,
      description: "Delivery:" + this.getDeliveryDate(7, 10)
    },
    {
      id: "PU",
      name: 'Pickup',
      cost: 0,
      description: "Chisinau, Tudor Strisca 8/1"
    },
  ]

  shippingCost:number = 0;

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

  constructor(private cartService: CartService, private fb: FormBuilder, private router: Router) {}

  ngOnInit(): void {
    this.cartItems = this.cartService.loadCartItemsFromStorage();
    this.cardModified();
    this.getTotalAmount();
    this.subscribeToCartItems();
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
    const startDate = new Date();
    const endDate = new Date();
    startDate.setDate(startDate.getDate() + minDays);
    endDate.setDate(endDate.getDate() + maxDays);

    const startDeliveryDate = `${this.pad(startDate.getDate())}.${this.pad(startDate.getMonth() + 1)}.${startDate.getFullYear()}`;
    const endDeliveryDate = `${this.pad(endDate.getDate())}.${this.pad(endDate.getMonth() + 1)}.${endDate.getFullYear()}`;

    return `${startDeliveryDate} - ${endDeliveryDate}`;
  }

  pad(n: number) {
    return n < 10 ? '0' + n : n;
  }

  placeOrder() {

    this.router.navigate(['order-success/1']);

  }

  setShippingOptions(id: string) {
    console.log(id);
    this.form.controls.selectedValue.setValue(id);

    let selectedOption = this.shippingOptions.find(option => option.id === id);

    this.shippingCost = selectedOption?.cost || 0;

  }

  private subscribeToCartItems() {
    this.cartService.cartItems.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (items) => {
        if (items.length === 0) {
          this.router.navigate(['empty-cart']);

        }

      }
    );
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}
