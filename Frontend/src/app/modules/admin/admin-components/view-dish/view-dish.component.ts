import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../admin-services/admin.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { ConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-view-dish',
  templateUrl: './view-dish.component.html',
  styleUrls: ['./view-dish.component.css']
})
export class ViewDishComponent implements OnInit {

  dishForm: FormGroup;
  dishes:any[];
  isSpinning: boolean;

  ngOnInit():void {
    this.dishForm = this.fb.group({
      title: [null, [Validators.required]]
      });
    this.getAllDishes()
  }

  constructor(private service: AdminService,
              private activatedroute: ActivatedRoute,
              private fb: FormBuilder,
              private message:NzMessageService,
              private sanitizer: DomSanitizer, private dialog: MatDialog) {
  }
  restaurantId: any = this.activatedroute.snapshot.params['restaurantId'];

  submitForm(){
    this.isSpinning = true;
    this.dishes =[];
    this.service.getAllRestaurantsByRestaurantAndTitle(this.restaurantId,this.dishForm.get('title')!.value).subscribe((res)=>{
      console.log(res);
      res.forEach(element=>{
        const imgSrc = 'data:image;base64,' + element.returnedImg;
        // Sanitize the image source URL
        const sanitizedImgSrc: SafeResourceUrl = this.sanitizer.bypassSecurityTrustResourceUrl(imgSrc);
        
        // Add sanitized image source to the dish object
        element.img = sanitizedImgSrc;
        // element.img = 'data:image;base64,' + element.returnedImg;
        this.dishes.push(element);
        this.isSpinning=false;
      });
    });
  }

  openConfirmationDialog(dishId: number): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '350px',
      data: { message: 'Are you sure you want to delete this restaurant?' }
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // User clicked "Yes", proceed with the deletion
        this.deleteDish(dishId);
      }
    });
  }

  deleteDish(dishId:number){
    console.log(dishId)
    this.service.deleteDish(dishId).subscribe((res) => {
     if(res !== null) {
      this.getAllDishes();
      this.message.success(`Dish Deleted successfully`,{ nzDuration :5000});
     }else{
      this.message.error(`Something went wrong`,{ nzDuration :5000});
     }

    })
  }

  getAllDishes(){
    this.dishes = [];
    this.service.getAllDishesByRestaurant(this.restaurantId).subscribe((res)=>{
      console.log(res)
      res.forEach(
        element => {
          const imgSrc = 'data:image;base64,' + element.returnedImg;
          // Sanitize the image source URL
          const sanitizedImgSrc: SafeResourceUrl = this.sanitizer.bypassSecurityTrustResourceUrl(imgSrc);
          
          // Add sanitized image source to the dish object
          element.img = sanitizedImgSrc;
          // element.img = 'data:image;base64,' + element.returnedImg;
          this.dishes.push(element);
        });
      });
     
  }

}
