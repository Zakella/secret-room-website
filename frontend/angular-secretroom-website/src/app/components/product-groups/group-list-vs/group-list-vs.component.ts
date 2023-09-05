import {Component, OnInit} from '@angular/core';
import {ProductCategoryService} from "../../../services/product-category.service";
import {ProductCategory} from "../../../model/product-category";
import {LanguageService} from "../../../services/language.service";

@Component({
  selector: 'app-group-list-vs',
  templateUrl: './group-list-vs.component.html',
  styleUrls: ['./group-list-vs.component.css']
})
export class GroupListVsComponent implements OnInit {
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
