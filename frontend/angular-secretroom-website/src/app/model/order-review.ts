import {Address} from "./address";
import {OrderItem} from "./order";
import {ShippingOption} from "./shipping-option";
import {Customer} from "./customer";

export interface OrderReview {
  orderNumber: string;
  shippingAddress: Address;
  items: OrderItem[];
  subtotal: number;
  totalAmountOrder: number;
  shippingOption: ShippingOption;
  customer: Customer;

}
