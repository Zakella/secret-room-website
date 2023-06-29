import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {GroupListComponent} from "./components/group-list/group-list.component";
import {GroupListBbComponent} from "./components/group-list-bb/group-list-bb.component";


const routes: Routes = [


  { path: '',
    redirectTo: 'vs',
    pathMatch: 'full' },

  {
    path: "bb",
    component: GroupListBbComponent


  },

  {
    path: "vs",
    component: GroupListComponent

  },


  { path: '**',
    redirectTo: 'vs' },



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
