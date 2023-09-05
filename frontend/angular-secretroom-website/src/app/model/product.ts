  import {ProductImage} from "./product-image";
  import {Size} from "./sizes";
  import {ProductCategory} from "./product-category";

  export class Product {
    constructor(
      public id?: string,
      public sku?: string,
      public name?: string,
      public nameRo?: string,
      public nameRu?: string,
      public description?: string,
      public descriptionRo?: string,
      public descriptionRu?: string,
      public shortDescription?: string,
      public shortDescriptionRo?: string,
      public shortDescriptionRu?: string,
      public category?: ProductCategory,
      public unitPrice?: number |0,
      public imageURL?: string,
      public active?: boolean,
      public unitsInStock?: number,
      public brand?: string,
      public brandAlias?: string,
      public brandShortName?: string,
      public productImages?: ProductImage[],
      public productSizes?: Size[]
    ) {}
  }




