import {RouterModule, Routes} from "@angular/router";
import {ProductNotFoundComponent} from "../components/products/product-not-found/product-not-found.component";
import {ProductListComponent} from "../components/products/product-list/product-list.component";
import {ProductCardComponent} from "../components/products/product-card/product-card.component";
import {NgModule} from "@angular/core";

const sharedRoutes: Routes = [
  { path: 'product-not-found', component: ProductNotFoundComponent },
  { path: 'search/:keyword', component: ProductListComponent },
  { path: 'category/:groupId', component: ProductListComponent },
  { path: 'product-view/:id', component: ProductCardComponent },
];

@NgModule({
  imports: [RouterModule.forChild(sharedRoutes)],
  exports: [RouterModule]
})
export class SharedModule {
}
