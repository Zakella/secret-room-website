import { Component } from '@angular/core';
import {ProductCategory} from "../../../model/product-category";
import {ProductCategoryService} from "../../../services/product-category.service";
import {LanguageService} from "../../../services/language.service";

@Component({
  selector: 'app-group-list-bb',
  templateUrl: './group-list-bb.component.html',
  styleUrls: ['./group-list-bb.component.css']
})
export class GroupListBbComponent {
  categories: Array<ProductCategory> = [];
  currentLang: string;

  constructor(private categoryService: ProductCategoryService,
              private languageService: LanguageService) {
    this.currentLang = 'Ro';
  }

  ngOnInit(): void {
    this.categoryService.getProductList().subscribe(
      {
        next: (data) => {
          this.categories = data.filter(category => category.brand === 'BathAndBody');
        }
      })

    this.languageService.language$.subscribe(lang => {
      this.currentLang = lang;
    });

  }


}
