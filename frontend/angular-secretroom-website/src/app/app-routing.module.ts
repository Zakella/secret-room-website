import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VsLogoComponent } from "./components/vs-logo/vs-logo.component";
import { BbLogoComponent } from "./components/bb-logo/bb-logo.component";
import {GroupProductVsComponent} from "./components/group-product-vs/group-product-vs.component";
import {ProductListComponent} from "./components/product-list/product-list.component";


const routes: Routes = [
  {
    path: '',
    redirectTo: 'vs',
    pathMatch: 'full'
  },
  {
    path: 'bb',
    component: BbLogoComponent,

    children: [
      {
        path: '',
        component: GroupProductVsComponent
      }
    ]
  },
  {
    path: 'vs',
    component: VsLogoComponent,
    children: [
      {
        path: '',
        component: GroupProductVsComponent,
      }
    ]

  },

  {
    path: '**',
    redirectTo: 'vs'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
