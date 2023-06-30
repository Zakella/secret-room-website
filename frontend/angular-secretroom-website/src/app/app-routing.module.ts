import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {GroupListBbComponent} from "./components/group-list-bb/group-list-bb.component";
import {GroupListVsComponent} from "./components/group-list-vs/group-list-vs.component";

const routes: Routes = [


  { path: '',
    redirectTo: 'vs',
    pathMatch: 'full' },

  {
    path: 'vs',
    component: GroupListVsComponent
  },

  {
    path: "bb",
    component: GroupListBbComponent

  },


  { path: '**',
    redirectTo: 'vs' },



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
