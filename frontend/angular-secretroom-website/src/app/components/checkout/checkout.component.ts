import {Component, OnInit} from '@angular/core';
import {CartItem} from "../../model/cart-item";
import {CartService} from "../../services/cart.service";
import {FormBuilder, FormControl, Validators} from "@angular/forms";


@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {



  cartItems: CartItem[] = [];

  totalAmount: number = 0;

  nameValidator: Validators[] = [
    Validators.required,
    Validators.minLength(3),
    Validators.pattern('^[A-Za-z]*$')
  ];

  shippingOptions = [
    {
      name: 'Curier Rapid',
      cost: 35,
      delivery: this.getDeliveryDate(1, 3)
    },
    {
      name: 'Posta Moldovei',
      cost: 35,
      delivery: this.getDeliveryDate(7, 10)
    },
    {
      name: 'Pickup'
    },
  ];


  form = this.fb.group({
    email: ["", [Validators.required,
      Validators.pattern(/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/)]],
    country: {value: "Moldova, Republic of", disabled: true},
    name: ["", this.nameValidator],
    checked: this.fb.control(true),
    selectedValue:  this.fb.control(null as string | null),
    lastname: ["", this.nameValidator],
    address: ["", [Validators.required, Validators.minLength(3)]],
    city: ["", this.nameValidator],
    zip: ["", [Validators.required, Validators.minLength(4)]],
    phone: ["", [Validators.required, Validators.pattern(/^(06|07)\d{7}$/)]],
    payment: "",
    delivery: "",
    comment: ""
  });

  constructor(private cartService: CartService, private fb: FormBuilder) {

  }

  ngOnInit(): void {
    this.cartItems = this.cartService.loadCartItemsFromStorage();

    this.cardModified();
    this.getTotalAmount();

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
          this.cartItems = this.cartService.cartItems;
        }
      }
    )
  }


  getDeliveryDate(minDays: number, maxDays: number): string {
    const startDate = new Date();
    const endDate = new Date();
    startDate.setDate(startDate.getDate() + minDays);
    endDate.setDate(endDate.getDate() + maxDays);

    // format date as 'dd.mm.yyyy'
    const startDeliveryDate = `${this.pad(startDate.getDate())}.${this.pad(startDate.getMonth() + 1)}.${startDate.getFullYear()}`;
    const endDeliveryDate = `${this.pad(endDate.getDate())}.${this.pad(endDate.getMonth() + 1)}.${endDate.getFullYear()}`;

    return `${startDeliveryDate} - ${endDeliveryDate}`;
  }

// Function to pad single digit numbers with leading zero
  pad(n: number) {
    return n < 10 ? '0' + n : n;
  }

  placeOrder() {

  }
}


