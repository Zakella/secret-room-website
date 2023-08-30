  import {Product} from "./product";
  import {ShippingOption} from "./shipping-option";
  import {Customer} from "./customer";

  export class Order {

    constructor(
      public id: number | null,
      public placementDate: Date,
      public shippingOption?: ShippingOption,
      public shippingAddress?: ShippingAddress, // добавьте это свойство
      public customer? : Customer,
      public delivery?: number,
      public totalQuantity?: number,
      public totalAmount?: number,
      public totalAmountOrder?: number,
      public items?: OrderItem[],
      public comment?: string

  )
    {
      this.id = id;
      this.placementDate = placementDate;
      this.shippingOption = shippingOption;
      this.shippingAddress = shippingAddress; // и это
      this.customer = customer;
      this.delivery = delivery;
      this.totalQuantity = totalQuantity;
      this.totalAmount = totalAmount;
      this.totalAmountOrder = totalAmountOrder;
      this.items = items;
      this.comment = comment;

    }
  }

  export interface OrderItem {
    product: Product;
    sizeType?: string;
    amount: number;
    quantity: number;
  }


  export class ShippingAddress {
    constructor(
      public street?: string,
      public city?: string,
      public country?: string,
      public zipCode?: string,
      public phoneNumber?: string,
      public email?: string
    ) {
      this.street = street;
      this.city = city;
      this.country = country;
      this.zipCode = zipCode;
    }
  }
