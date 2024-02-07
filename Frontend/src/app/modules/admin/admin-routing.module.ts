import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './admin-components/dashboard/dashboard.component';
import { AddRestaurantComponent } from './admin-components/add-restaurant/add-restaurant.component';
import { AddItemComponent } from './admin-components/add-item/add-item.component';
import { ViewDishComponent } from './admin-components/view-dish/view-dish.component';
import { UpdateProductComponent } from './admin-components/update-product/update-product.component';
import { UpdateRestaurantComponent } from './admin-components/update-restaurant/update-restaurant.component';

const routes: Routes = [
  {path:"dashboard",component:DashboardComponent},
  {path:"registerRestaurant",component:AddRestaurantComponent},
  {path:":restaurantId/addDish",component:AddItemComponent},
  {path:":restaurantId/view_dishes",component:ViewDishComponent},
  {path:"dish/:dishId",component:UpdateProductComponent},
  {path:"restaurant/:restaurantId",component:UpdateRestaurantComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
