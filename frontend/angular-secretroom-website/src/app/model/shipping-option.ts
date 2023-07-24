export class ShippingOption {
  constructor(
    public id: string,
    public name: string,
    public cost: number,
    public description: string,
    public expectedDeliveryFrom: number,
    public expectedDeliveryTo: number
  ) {
    this.id = id;
    this.name = name;
    this.cost = cost;
    this.description = description;
    this.expectedDeliveryFrom = expectedDeliveryFrom;
    this.expectedDeliveryTo = expectedDeliveryTo;
  }
}
