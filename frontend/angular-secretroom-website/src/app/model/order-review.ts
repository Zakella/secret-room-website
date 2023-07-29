import {Address} from "./address";
import {OrderItem} from "./order";
import {ShippingOption} from "./shipping-option";

export interface OrderReview {
  orderNumber: string;
  shippingAddress: Address;
  items: OrderItem[];
  totalAmountOrder: number;
  shippingOption: ShippingOption;
}
