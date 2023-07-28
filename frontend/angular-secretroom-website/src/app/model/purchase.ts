import {Customer} from "./customer";
import {Address} from "./address";
import {Order, OrderItem} from "./order";

export class Purchase {

  customer: Customer;
  shippingAddress: Address;
  order: Order;
  orderItems: OrderItem[];

  constructor(customer: Customer, shippingAddress: Address, order: Order, orderItems: OrderItem[]) {
    this.customer = customer;
    this.shippingAddress = shippingAddress;
    this.order = order;
    this.orderItems = orderItems;
  }

}
