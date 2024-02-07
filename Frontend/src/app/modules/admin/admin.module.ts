import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgZorroAntdModule, NzButtonModule, NzIconModule, NzUploadModule } from 'ng-zorro-antd';
import { AdminRoutingModule } from './admin-routing.module';
import { DashboardComponent } from './admin-components/dashboard/dashboard.component';
import { AdminComponentsComponent } from './admin-components/admin-components.component';
import { AddRestaurantComponent } from './admin-components/add-restaurant/add-restaurant.component';
import { ReactiveFormsModule,FormsModule } from '@angular/forms';
import { AddItemComponent } from './admin-components/add-item/add-item.component';
import { ViewDishComponent } from './admin-components/view-dish/view-dish.component';
import { UpdateProductComponent } from './admin-components/update-product/update-product.component';
import { UpdateRestaurantComponent } from './admin-components/update-restaurant/update-restaurant.component';
import { MatDialogModule } from '@angular/material/dialog';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';

@NgModule({
  declarations: [DashboardComponent, AdminComponentsComponent, AddRestaurantComponent, AddItemComponent, ViewDishComponent, UpdateProductComponent, UpdateRestaurantComponent, ConfirmDialogComponent],
  entryComponents: [ConfirmDialogComponent],
  imports: [
    CommonModule,
    NzUploadModule,
    NzButtonModule,
    MatDialogModule,
    NzIconModule,
    AdminRoutingModule,ReactiveFormsModule,FormsModule,NgZorroAntdModule
  ]
})
export class AdminModule { }
