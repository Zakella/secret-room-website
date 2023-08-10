  import {Product} from "./product";
  import {ShippingOption} from "./shipping-option";

  export class Order {

    constructor(
      public placementDate: Date,
      public shippingOption?: ShippingOption,
      public delivery?: number,
      public totalQuantity?: number,
      public totalAmount?: number,
      public totalAmountOrder?: number,
      public items?: OrderItem[]

  )
    {
      this.placementDate = placementDate;
      this.shippingOption = shippingOption;
      this.delivery = delivery;
      this.totalQuantity = totalQuantity;
      this.totalAmount = totalAmount;
      this.totalAmountOrder = totalAmountOrder;
      this.items = items;

    }
  }

  export interface OrderItem {
    product: Product;
    sizeType?: string;
    amount: number;
    quantity: number;
  }


