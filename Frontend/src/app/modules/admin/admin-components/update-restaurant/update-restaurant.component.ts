import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../admin-services/admin.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-update-restaurant',
  templateUrl: './update-restaurant.component.html',
  styleUrls: ['./update-restaurant.component.css']
})
export class UpdateRestaurantComponent implements OnInit {
  restaurantForm: FormGroup;
  restaurantId: any= this.activatedroute.snapshot.params['restaurantId'];
  selectedFoodFile: File | null = null;
  selectedLicenseFile: File | null = null;
  foodImagePreview: string | SafeResourceUrl | ArrayBuffer | null = null;
  licenseImagePreview: string | SafeResourceUrl | null = null;
  errorMessage:string;
  constructor(private fb: FormBuilder,
    private service: AdminService,
    private activatedroute: ActivatedRoute,
    private message: NzMessageService,private sanitizer: DomSanitizer, private router: Router) { }
   

  ngOnInit() {
    this.restaurantForm = this.fb.group({
      restaurantName: ['', [Validators.required]],
      description: ['', [Validators.required]]
      // Add other form controls as needed
    });
     this.getRestaurantById();
  }

  getRestaurantById(){
    this.service.getRestaurantById(this.restaurantId).subscribe(
      (restaurant:any) => {
        console.log(restaurant);
        this.restaurantForm.patchValue({
          restaurantName: restaurant.restaurantName,
          description: restaurant.description
        });
  
       const foodImg = 'data:image;base64,' +  restaurant.returnedFoodAttachment;
       // Sanitize the image source URL
       const sanitizedFoodImgSrc: SafeResourceUrl = this.sanitizer.bypassSecurityTrustResourceUrl(foodImg);
       this.foodImagePreview = sanitizedFoodImgSrc;
   
       const licenseImg = 'data:image;base64,' +  restaurant.returnedLicenseAttachment;
       // Sanitize the image source URL
       const sanitizedLicenseImgSrc: SafeResourceUrl = this.sanitizer.bypassSecurityTrustResourceUrl(licenseImg);
       this.licenseImagePreview = sanitizedLicenseImgSrc;

     
      });
  }

  onFoodFileSelected(event: any) {
    const file = event.target.files && event.target.files[0];
    this.convertFileToDataUrl(file, 'foodImagePreview');
    if (file) {
      this.selectedFoodFile = file;
      this.previewFile(file, 'foodImagePreview');
    }
  }

  private previewFile(file: File, property: 'foodImagePreview' | 'licenseImagePreview'): void {
    const reader = new FileReader();
    reader.onload = () => {
      this[property] = reader.result;
    };
    reader.readAsDataURL(file);
  }

  onLicenseFileSelected(event: any) {
    const file = event.target.files && event.target.files[0];
    this.convertFileToDataUrl(file, 'licenseImagePreview');
    if (file) {
      this.selectedLicenseFile = file;
      this.previewFile(file, 'licenseImagePreview');
    }
  }

  convertFileToDataUrl(file: File, property: 'foodImagePreview' | 'licenseImagePreview') {
    const reader = new FileReader();

    reader.onload = (event: any) => {
      // Dynamically update the property based on the argument
      this[property] = event.target.result;
    };

    reader.readAsDataURL(file);
  }

  removeFoodFile() {
    this.selectedFoodFile = null;
    this.foodImagePreview = null;
    // Reset the file input
    const foodInput = document.getElementById('foodInput') as HTMLInputElement;
    if (foodInput) {
      foodInput.value = '';
    }
  }

  removeLicenseFile() {
    this.selectedLicenseFile = null;
    this.licenseImagePreview = null;
    // Reset the file input
    const licenseInput = document.getElementById('licenseInput') as HTMLInputElement;
    if (licenseInput) {
      licenseInput.value = '';
    }
  }

  updateRestaurant() {
    if (this.restaurantForm.valid) {
      // const updatedRestaurantData = this.restaurantForm.value;
      const updatedRestaurantData: FormData = new FormData(); 
      if (this.selectedFoodFile){
        updatedRestaurantData.append('foodAttachment', this.selectedFoodFile)
      }
      if (this.selectedLicenseFile){
        updatedRestaurantData.append('licenseAttachment', this.selectedLicenseFile)
      }
      updatedRestaurantData.append("restaurantName",this.restaurantForm.get("restaurantName").value)
      updatedRestaurantData.append("description",this.restaurantForm.get("description").value)
      
      console.log(updatedRestaurantData); 
      this.service.updateRestaurant(this.restaurantId,updatedRestaurantData).subscribe(
           (res) => {
            this.errorMessage = '';
          this.message.success('Restaurant updated successfully', { nzDuration: 5000 });
          this.router.navigateByUrl('/admin/dashboard');
          // Optionally, navigate to a different page after successful update
        },
        (error) => {
          this.message.error('Error updating restaurant', { nzDuration: 5000 });
         
            this.errorMessage = error; // Set the error message to be displayed on the UI
            console.error(error);
          
        }
      );
    } else {
      console.log('Invalid form submission. Please check the form.');
    }
  }
 }


