import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './customer-components/dashboard/dashboard.component';
import { ViewDishesByRestaurantComponent } from './customer-components/view-dishes-by-restaurant/view-dishes-by-restaurant.component';


const routes: Routes = [
  {path:"dashboard",component:DashboardComponent},
  {path:":restaurantId/view_dishes",component:ViewDishesByRestaurantComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
