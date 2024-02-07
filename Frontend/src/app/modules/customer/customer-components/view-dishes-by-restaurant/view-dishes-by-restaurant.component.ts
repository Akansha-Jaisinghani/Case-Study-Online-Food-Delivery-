import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { AdminService } from 'src/app/modules/admin/admin-services/admin.service';
import { CustomerService } from '../../customer-service/customer.service';

@Component({
  selector: 'app-view-dishes-by-restaurant',
  templateUrl: './view-dishes-by-restaurant.component.html',
  styleUrls: ['./view-dishes-by-restaurant.component.css']
})
export class ViewDishesByRestaurantComponent implements OnInit {

  dishForm: FormGroup;
  dishes:any[];
  isSpinning: boolean;

  ngOnInit():void {
    this.dishForm = this.fb.group({
      title: [null, [Validators.required]]
      });
    this.getAllDishes()
  }

  constructor(private service: CustomerService,
              private activatedroute: ActivatedRoute,
              private fb: FormBuilder,
              private message:NzMessageService,
              private sanitizer: DomSanitizer) {
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
