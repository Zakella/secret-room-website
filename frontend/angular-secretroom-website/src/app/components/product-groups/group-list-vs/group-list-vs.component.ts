import {Component, OnInit} from '@angular/core';
import {ProductCategory} from "../../../model/product/Product-category";
import {ProductCategoryService} from "../../../services/product-category.service";

@Component({
  selector: 'app-group-list-vs',
  templateUrl: './group-list-vs.component.html',
  styleUrls: ['./group-list-vs.component.css']
})
export class GroupListVsComponent implements OnInit {
  categories: Array<ProductCategory> = [];

  constructor(private categoryService: ProductCategoryService) {
  }

  ngOnInit(): void {
    this.categoryService.getProductList().subscribe(
      {
        next: (data) => {
          this.categories = data.filter(category => category.brand === 'VictoriasSecret');
        }
      }
    )
  }


}
