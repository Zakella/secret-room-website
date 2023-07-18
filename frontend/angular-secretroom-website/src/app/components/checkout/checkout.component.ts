import {Component, OnInit} from '@angular/core';
import {CartItem} from "../../model/cart-item";
import {CartService} from "../../services/cart.service";
import {FormBuilder, Validators} from "@angular/forms";


@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  selectedValue: string = '';

  quantities1: number[] = [1, 1, 1];

  checked: boolean = true;

  cartItems: CartItem[] = [];

  totalAmount: number = 0;

  nameValidator: Validators[] = [
    Validators.required,
    Validators.minLength(3),
    Validators.pattern('^[A-Za-z]*$')
  ];

  form = this.fb.group({
    email: ["", [Validators.required,
      Validators.pattern(/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/)]],
    country: {value: "Moldova, Republic of", disabled: true},
    name: ["", this.nameValidator],
    checked: this.fb.control(true),
    selectedValue: this.fb.control(''),
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


  placeOrder() {

  }
}


