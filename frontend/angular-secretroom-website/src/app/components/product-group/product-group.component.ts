import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-product-group',
  templateUrl: './product-group.component.html',
  styleUrls: ['./product-group.component.css']
})
export class ProductGroupComponent {

  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) {}

  loadProducts() {
    const currentRoute = this.router.url;
    const productsRoute = `${currentRoute}/products`;
    this.router.navigate([productsRoute]);
  }

}
