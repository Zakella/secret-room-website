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
import {FormGroup, FormsModule} from "@angular/forms";
import {PanelComponent} from "./components/panel/panel.component";
import {BbLogoComponent} from "./components/bb-logo/bb-logo.component";
import {VsLogoComponent} from "./components/vs-logo/vs-logo.component";
import {ImageModule} from "primeng/image";
import {DividerModule} from "primeng/divider";
import {StyleClassModule} from "primeng/styleclass";
import {RippleModule} from "primeng/ripple";
import { FooterComponent } from './components/footer/footer.component';
import { MainContainerComponent } from './components/main-container/main-container.component';
import { GroupProductVsComponent } from './components/group-product-vs/group-product-vs.component';

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
    FooterComponent,
    MainContainerComponent,
    GroupProductVsComponent,
    ProductListComponent
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
    ImageModule,
    DividerModule,
    StyleClassModule,
    RippleModule


  ],
  providers: [ProductService],
  bootstrap: [AppComponent]
})
export class AppModule { }
