import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {GroupListBbComponent} from "./components/product-groups/group-list-bb/group-list-bb.component";
import {GroupListVsComponent} from "./components/product-groups/group-list-vs/group-list-vs.component";
import {ProductListComponent} from "./components/products/product-list/product-list.component";
import {ProductNotFoundComponent} from "./components/products/product-not-found/product-not-found.component";
import {ProductCardComponent} from "./components/products/product-card/product-card.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";

const routes: Routes = [


  {
    path: '',
    redirectTo: 'vs',
    pathMatch: 'full'
  },

  {path: 'vs/search/:keyword', component: ProductListComponent},
  {path: 'vs/:id', component: ProductListComponent},
  {path: 'vs', component: GroupListVsComponent},


  {path: 'bb/search/:keyword', component: ProductListComponent},
  {path: 'bb/:id', component: ProductListComponent},
  {path: "bb", component: GroupListBbComponent},

  {path: 'product-view/:id', component: ProductCardComponent},

  {path: "product-not-found", component: ProductNotFoundComponent},

  {
    path: '**',
    component: PageNotFoundComponent
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
