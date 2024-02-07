import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomerRoutingModule } from './customer-routing.module';
import { DashboardComponent } from './customer-components/dashboard/dashboard.component';
import { NgZorroAntdModule, NzButtonModule } from 'ng-zorro-antd';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ViewDishesByRestaurantComponent } from './customer-components/view-dishes-by-restaurant/view-dishes-by-restaurant.component';


@NgModule({
  declarations: [DashboardComponent, ViewDishesByRestaurantComponent],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    NzButtonModule,ReactiveFormsModule,FormsModule,NgZorroAntdModule
  ]
})
export class CustomerModule { }
