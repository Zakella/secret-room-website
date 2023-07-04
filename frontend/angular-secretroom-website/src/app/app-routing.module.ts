import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {GroupListBbComponent} from "./components/group-list-bb/group-list-bb.component";
import {GroupListVsComponent} from "./components/group-list-vs/group-list-vs.component";
import {ProductListComponent} from "./components/product-list/product-list.component";

const routes: Routes = [


  { path: '',
    redirectTo: 'vs',
    pathMatch: 'full' },

  {path:'vs/search/:keyword', component: ProductListComponent},
  { path: 'vs/:id', component: ProductListComponent},
  {path: 'vs', component: GroupListVsComponent},


  { path: 'bb/search/:keyword', component: ProductListComponent},
  {path: 'bb/:id', component: ProductListComponent},
  {path: "bb", component: GroupListBbComponent},



  { path: '**',
    redirectTo: 'vs' },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
