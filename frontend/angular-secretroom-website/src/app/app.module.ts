import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {ProductService} from "./services/product.service";
import { HeaderComponent } from './components/header/header.component';
import { SubHeaderComponent } from './components/sub-header/sub-header.component';
import {LanguagePanelComponent} from "./components/language-pannel/language-panel.component";
import {BrowserAnimationsModule, NoopAnimationsModule} from "@angular/platform-browser/animations";
import {DropdownModule} from "primeng/dropdown";
import {ButtonModule} from "primeng/button";
import {FormsModule} from "@angular/forms";
import {PanelComponent} from "./components/panel/panel.component";
import {BbLogoComponent} from "./components/bb-logo/bb-logo.component";
import {VsLogoComponent} from "./components/vs-logo/vs-logo.component";
import { GroupListComponent } from './components/group-list/group-list.component';
import { GroupListBbComponent } from './components/group-list-bb/group-list-bb.component';
import {AnimateModule} from "primeng/animate";

@NgModule({
  declarations: [
    AppComponent,
    ProductListComponent,
    HeaderComponent,
    SubHeaderComponent,
    LanguagePanelComponent,
    PanelComponent,
    BbLogoComponent,
    VsLogoComponent,
    GroupListComponent,
    GroupListBbComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    NoopAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    DropdownModule,
    ButtonModule,
    FormsModule,
    AnimateModule
  ],
  providers: [ProductService],
  bootstrap: [AppComponent]
})
export class AppModule { }
