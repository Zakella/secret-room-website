import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { ProductListComponent } from './components/products/product-list/product-list.component';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {ProductService} from "./services/product.service";
import { HeaderComponent } from './components/headers/header/header.component';
import { SubHeaderComponent } from './components/headers/sub-header/sub-header.component';
import {LanguagePanelComponent} from "./components/headers/language-pannel/language-panel.component";
import {BrowserAnimationsModule, NoopAnimationsModule} from "@angular/platform-browser/animations";
import {DropdownModule} from "primeng/dropdown";
import {ButtonModule} from "primeng/button";
import {FormGroup, FormsModule} from "@angular/forms";
import {PanelComponent} from "./components/headers/panel/panel.component";
import {BbLogoComponent} from "./components/headers/bb-logo/bb-logo.component";
import {VsLogoComponent} from "./components/headers/vs-logo/vs-logo.component";
import {ImageModule} from "primeng/image";
import {DividerModule} from "primeng/divider";
import {StyleClassModule} from "primeng/styleclass";
import {RippleModule} from "primeng/ripple";
import { FooterComponent } from './components/footer/footer.component';
import { GroupListVsComponent } from './components/product-groups/group-list-vs/group-list-vs.component';
import {GroupListBbComponent} from "./components/product-groups/group-list-bb/group-list-bb.component";
import {AppRoutingModule} from "./app-routing.module";
import {SearchComponent} from "./components/headers/search/search.component";
import { ProductNotFoundComponent } from './components/products/product-not-found/product-not-found.component';
import { ProductCardComponent } from './components/products/product-card/product-card.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import {InputNumberModule} from "primeng/inputnumber";
import {PaginatorModule} from "primeng/paginator";
import { CartStatusComponent } from './components/cart/cart-status/cart-status.component';
import { LoginComponent } from './components/login/login.component';
import {SidebarModule} from "primeng/sidebar";
import {BadgeModule} from "primeng/badge";
import {CardModule} from "primeng/card";
import {PanelModule} from "primeng/panel";
import { CheckoutComponent } from './components/checkout/checkout.component';
import {RadioButtonModule} from "primeng/radiobutton";
import {CheckboxModule} from "primeng/checkbox";
import {PasswordModule} from "primeng/password";
import { MyCartComponent } from './components/cart/my-cart/my-cart.component';
import {SidebarComponent} from "./components/sidebar/sidebar.component";
import {ReactiveFormsModule} from "@angular/forms";
import {InputMaskModule} from "primeng/inputmask";
import {MessageModule} from "primeng/message";
import {MessagesModule} from "primeng/messages";
import { EmptyCartComponent } from './components/cart/empty-cart/empty-cart.component';
import {ToolbarModule} from "primeng/toolbar";
import {MenuModule} from "primeng/menu";
import { OrderSummaryComponent } from './components/order/order-summary/order-summary.component';
import {MessageService} from "primeng/api";
import {ToastModule} from "primeng/toast";
import { LoginPageComponent } from './components/login-page/login-page.component';
import { RegistrationComponent } from './components/registration/registration.component';





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
    ProductListComponent,
    GroupListVsComponent,
    GroupListBbComponent,
    SearchComponent,
    ProductNotFoundComponent,
    ProductCardComponent,
    PageNotFoundComponent,
    CartStatusComponent,
    LoginComponent,
    CheckoutComponent,
    MyCartComponent,
    SidebarComponent,
    EmptyCartComponent,
    OrderSummaryComponent,
    LoginPageComponent,
    RegistrationComponent,

  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    DropdownModule,
    ButtonModule,
    FormsModule,
    ImageModule,
    DividerModule,
    StyleClassModule,
    RippleModule,
    AppRoutingModule,
    InputNumberModule,
    PaginatorModule,
    SidebarModule,
    BadgeModule,
    CardModule,
    PanelModule,
    RadioButtonModule,
    CheckboxModule,
    PasswordModule,
    ReactiveFormsModule,
    InputMaskModule,
    MessageModule,
    MessagesModule,
    ToolbarModule,
    MenuModule,
    ToastModule


  ],
  providers: [ProductService, MessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
