import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {GroupListBbComponent} from "./components/product-groups/group-list-bb/group-list-bb.component";
import {GroupListVsComponent} from "./components/product-groups/group-list-vs/group-list-vs.component";
import {ProductListComponent} from "./components/products/product-list/product-list.component";
import {ProductNotFoundComponent} from "./components/products/product-not-found/product-not-found.component";
import {ProductCardComponent} from "./components/products/product-card/product-card.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";
import {CheckoutComponent} from "./components/checkout/checkout.component";

const routes: Routes = [
  {
    path: '',
    redirectTo: 'vs',
    pathMatch: 'full'
  },
  {
    path: 'vs',
    children: [
      { path: '', component: GroupListVsComponent },
      { path: 'product-not-found', component: ProductNotFoundComponent },
      { path: 'search/:keyword', component: ProductListComponent },
      { path: 'category/:groupId', component: ProductListComponent },
      { path: 'all-vs-products', component: ProductListComponent },
      { path: 'product-view/:id', component: ProductCardComponent },
    ]
  },
  {
    path: 'bb',
    children: [
      { path: '', component: GroupListBbComponent },
      { path: 'product-not-found', component: ProductNotFoundComponent },
      { path: 'search/:keyword', component: ProductListComponent },
      { path: 'category/:groupId', component: ProductListComponent },
      { path: 'all-bb-products', component: ProductListComponent },
      { path: 'product-view/:id', component: ProductCardComponent },
    ]
  },

  {
    path: 'checkout',
    component: CheckoutComponent
  },

  { path: '**', component: PageNotFoundComponent },
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
