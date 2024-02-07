import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule} from '@angular/forms';


import { WrapperComponent } from './wrapper.component';
import { ProductListComponent } from './product-list/product-list.component';
import { CartComponent } from './cart/cart.component';
import { AddProductComponent } from './add-product/add-product.component';
import { Routes, RouterModule } from '@angular/router';



const routes: Routes = [
  { path: '',component:WrapperComponent}



];

@NgModule({
  declarations: [WrapperComponent,
    ProductListComponent,
    CartComponent,
    AddProductComponent],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(routes)

  ],
  exports:[WrapperComponent]
})
export class WrapperModule { }
