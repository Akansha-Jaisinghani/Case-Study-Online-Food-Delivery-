import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CustomerService } from '../../customer-service/customer.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  restaurants: any;
  validateForm: FormGroup;
  isSpinning: boolean;
 
  constructor(private service: CustomerService,
           private fb: FormBuilder) { }

  ngOnInit() {
    this.validateForm = this.fb.group({
      title: [null, [Validators.required]]
    });
    this.getAllRestaurants();
  }

  submitForm(){
    this.isSpinning = true;
    this.restaurants =[];
    this.service.getAllRestaurantsByTitle(this.validateForm.get('title')!.value).subscribe((res)=>{
      console.log(res);
      res.forEach(element=>{
        this.restaurants.push(element);
        this.isSpinning=false;
      });
    });
  }

  getAllRestaurants(){
    this.restaurants = [];
    this.service.getRestaurant().subscribe((res)=>{
      console.log(res)
      res.forEach(
        element => {
          this.restaurants.push(element);
        });
      });
  }

}
