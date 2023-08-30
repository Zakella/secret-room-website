import {Customer} from "./customer";
import {Address} from "./address";
import {Order, OrderItem} from "./order";

export class Purchase {

  customer: Customer;
  shippingAddress: Address;
  order: Order;
  orderItems: OrderItem[];
  comment: string;

  constructor(customer: Customer, shippingAddress: Address, order: Order, orderItems: OrderItem[], comment: string) {
    this.customer = customer;
    this.shippingAddress = shippingAddress;
    this.order = order;
    this.orderItems = orderItems;
    this.comment = comment;
  }

}
