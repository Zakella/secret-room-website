import {ProductImage} from "./ProductImage";
import {Size} from "./Sizes";

export class Product {
  constructor(
    public id: string,
    public sku: string,
    public name: string,
    public categoryName: string,
    public description: string,
    public shortDescription: string,
    public unitPrice: number,
    public imageURL: string,
    public active: boolean,
    public unitsInStock: number,

    public productImages: ProductImage[],
    public sizes: Size[]
  ) {}
}




