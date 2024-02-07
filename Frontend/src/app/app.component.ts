import { Component, ViewChild } from '@angular/core';
import { StorageService } from './auth-services/storage-services/storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'restaurantManagement';

  isAdminLoggedIn: boolean = StorageService.isAdminLoggedIn();
  isCustomerLoggedIn: boolean=StorageService.isCustomerLoggedIn();

  constructor(private router: Router){}

  showSidebar = false;

  // Define the toggleSidebar method
  toggleSidebar() {
    this.showSidebar = !this.showSidebar;
  }

  ngOnInit(){
    this.router.events.subscribe(event =>{
      if(event.constructor.name === "NavigationEnd"){
        this.isAdminLoggedIn = StorageService.isAdminLoggedIn();
        this.isCustomerLoggedIn= StorageService.isCustomerLoggedIn();
      }
    })
  }

logout(){
  StorageService.signout();
  this.router.navigateByUrl("/login");
}
}



