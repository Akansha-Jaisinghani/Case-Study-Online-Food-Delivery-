import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../admin-services/admin.service';
import { NzMessageService } from 'ng-zorro-antd';
import { element } from 'protractor';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  restaurants: any;
  validateForm: FormGroup;
  isSpinning: boolean;
 
  constructor(private service: AdminService,
           private fb: FormBuilder,
           private message:NzMessageService,
           private dialog: MatDialog) { }

  ngOnInit() {
    this.validateForm = this.fb.group({
      title: [null, [Validators.required]]
    });
    this.getAllRestaurants();
  }

  
  openConfirmationDialog(restaurantId: number): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '350px',
      data: { message: 'Are you sure you want to delete this restaurant?' }
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // User clicked "Yes", proceed with the deletion
        this.deleteRestaurant(restaurantId);
      }
    });
  }

  deleteRestaurant(restaurantId:number){
    console.log(restaurantId)
    this.service.deleteRestaurant(restaurantId).subscribe((res) => {
     if(res == null) {
      this.getAllRestaurants();
      this.message.success(`Restaurant Deregistered successfully`,{ nzDuration :5000});
     }else{
      this.message.error(`Something went wrong`,{ nzDuration :5000});
     }

    })
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
