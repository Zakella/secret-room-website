  import {Product} from "./product/Product";
  import {Size} from "./product/Sizes";
  import {ShippingOption} from "./shipping-option";

  export class Order {

    constructor(
      public placementDate: Date,
      public firstName: string,
      public lastName: string,
      public email: string,
      public phoneNumber: string,
      public deliveryAddress: string,
      public items: OrderItem[],
      public shippingOption?: ShippingOption,
      public delivery?: number,
      public totalQuantity?: number,
      public totalAmount?: number,
      public totalAmountOrder?: number
    )
    {
      this.placementDate = placementDate;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.phoneNumber = phoneNumber;
      this.deliveryAddress = deliveryAddress;
      this.items = items;
      this.shippingOption = shippingOption;
      this.delivery = delivery;
      this.totalQuantity = totalQuantity;
      this.totalAmount = totalAmount;
      this.totalAmountOrder = totalAmountOrder;

    }
  }

  export interface OrderItem {
    product: Product;
    size?:Size;
    amount: number;
    quantity: number;
  }


