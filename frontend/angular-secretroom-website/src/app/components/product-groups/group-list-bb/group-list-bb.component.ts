import { Component } from '@angular/core';
import {ProductCategory} from "../../../model/product-category";
import {ProductCategoryService} from "../../../services/product-category.service";

@Component({
  selector: 'app-group-list-bb',
  templateUrl: './group-list-bb.component.html',
  styleUrls: ['./group-list-bb.component.css']
})
export class GroupListBbComponent {
  categories: Array<ProductCategory> = [];

  constructor(private categoryService: ProductCategoryService) {
  }

  ngOnInit(): void {
    this.categoryService.getProductList().subscribe(
      {
        next: (data) => {
          this.categories = data.filter(category => category.brand === 'BathAndBody');
        }
      }
    )
  }
}
