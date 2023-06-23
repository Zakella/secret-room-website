import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AppComponent} from "./app.component";


const routes: Routes = [


  { path: '',
    redirectTo: 'vs',
    pathMatch: 'full' },

  {
    path: "bb",
    component: AppComponent,

  },

  {
    path: "vs",
    component: AppComponent,

  },

  { path: '**',
    redirectTo: 'vs' },



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
