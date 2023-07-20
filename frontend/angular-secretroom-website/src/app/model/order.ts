import {Product} from "./product/Product";
import {Size} from "./product/Sizes";

export class Order {
  placementDate: Date;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  deliveryAddress: string;
  items: OrderItem[];
  totalAmount: number;
  totalQuantity: number;

  constructor(
    placementDate: Date,
    firstName: string,
    lastName: string,
    email: string,
    phoneNumber: string,
    deliveryAddress: string,
    totalAmount: number,
    totalQuantity: number,
    items: OrderItem[]
  ) {
    this.placementDate = placementDate;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.deliveryAddress = deliveryAddress;
    this.items = items;
    this.totalAmount = totalAmount;
    this.totalQuantity = totalAmount;
  }
}

export interface OrderItem {
  product: Product;
  size?:Size;
  amount: number;
  quantity: number;
}


