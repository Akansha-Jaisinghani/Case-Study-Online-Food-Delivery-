import { ChangeDetectorRef, Component, OnInit, SecurityContext } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../admin-services/admin.service';
import { NzMessageService } from 'ng-zorro-antd';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Router } from '@angular/router';


@Component({
  selector: 'app-add-restaurant',
  templateUrl: './add-restaurant.component.html',
  styleUrls: ['./add-restaurant.component.css']
})
export class AddRestaurantComponent implements OnInit {
  restaurantForm: FormGroup;
  selectedFoodFile: File | null = null;
  selectedLicenseFile: File | null = null;
  foodImagePreview: string | ArrayBuffer | SafeResourceUrl | null = null;
  licenseImagePreview: string | ArrayBuffer | SafeResourceUrl | null = null;
  foodDocumentUrl: string | SafeResourceUrl | null = null;
  licenseDocumentUrl: string | SafeResourceUrl | null = null;
  errorMessage: string='';


  ngOnInit() {
    this.restaurantForm = this.fb.group({
      restaurantName: ['', [Validators.required]],
      description: ['', [Validators.required]]
       });
  }

  constructor(private service: AdminService,
    private cdr: ChangeDetectorRef,
              private fb: FormBuilder,
              private message:NzMessageService,
              private sanitizer: DomSanitizer,
              private route:Router) {
  }

 

// Update the onFoodFileSelected and onLicenseFileSelected methods
onFoodFileSelected(event: any) {
  // this.selectedFoodFile = event.target.files[0];
  // this.previewFoodImage();
  if (event && event.file && event.file.originFileObj) {
    this.selectedFoodFile = event.file.originFileObj;
   // this.previewFoodImage();
  } else {
    console.error('Invalid file input event:', event);
  }
  // Generate a URL for the selected food document
  this.foodDocumentUrl =  this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(this.selectedFoodFile));
}

downloadFoodDocument(){
  // if (this.foodDocumentUrl) {
  //   const link: HTMLAnchorElement = document.createElement('a');
  //   link.href = this.foodDocumentUrl as string;
  //   link.download = 'food_document';
  //   link.type = 'application/pdf';
  //   document.body.appendChild(link);
  //   link.click();
  //   document.body.removeChild(link);
  // }
this.foodDocumentUrl;
}

onLicenseFileSelected(event: any) {
  // this.selectedLicenseFile = event.target.files[0];
  // this.previewLicenseImage();

  if (event && event.file && event.file.originFileObj) {
    this.selectedLicenseFile = event.file.originFileObj;
  //   this.previewLicenseImage();
  } else {
    console.error('Invalid file input event:', event);
  }
  this.licenseDocumentUrl =  this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(this.selectedLicenseFile));

}

// Add methods to preview the selected files
previewFoodImage() {
  const reader = new FileReader();
  reader.onload = () => {
    this.foodImagePreview = this.sanitizer.bypassSecurityTrustResourceUrl(reader.result as string);
  };
  reader.readAsDataURL(this.selectedFoodFile);
}

previewLicenseImage() {
  const reader = new FileReader();
  reader.onload = () => {
    this.licenseImagePreview =this.sanitizer.bypassSecurityTrustResourceUrl(reader.result as string);
  };
  reader.readAsDataURL(this.selectedLicenseFile);
}
 
// Inside your component
removeFoodFile() {
  console.log(this.selectedFoodFile)
  this.selectedFoodFile = null;
  this.foodImagePreview = null; // You may need to reset this if it's used for preview
  // Reset the file input
  const foodInput = document.getElementById('foodInput') as HTMLInputElement;
  if (foodInput) {
    foodInput.value = '';
  }
  this.cdr.detectChanges();
  console.log('Food file removed successfully');
}

removeLicenseFile() {
  this.selectedLicenseFile = null;
  this.licenseImagePreview = null; // You may need to reset this if it's used for preview
  // Reset the file input
  const licenseInput = document.getElementById('licenseInput') as HTMLInputElement;
  if (licenseInput) {
    licenseInput.value = '';
  }
}
onAddRestaurant() {
    if (this.restaurantForm.valid) {

      console.log('Form submitted successfully!',this.restaurantForm.value);
      const formdata: FormData = new FormData();
      formdata.append("restaurantName",this.restaurantForm.get("restaurantName").value);
      formdata.append("description",this.restaurantForm.get("description").value);
     // Append food file with a unique name
     if(this.selectedFoodFile)
     formdata.append("foodAttachment", this.selectedFoodFile, 'foodAttachment');

     // Append license file with a unique name
     if(this.selectedLicenseFile)
     formdata.append("licenseAttachment", this.selectedLicenseFile, 'licenseAttachment');
 
      this.service.registerRestaurant(formdata).subscribe(
        (res)=>{
          this.errorMessage = ''
          console.log(res);
          if(res.id !== null){
            this.message.success("Restaurant Registered Successfully",{nzDuration: 5000})
            this.route.navigateByUrl("admin/dashboard")
          }else if(res.id == null){
            this.message.error("Something Went Wrong",{nzDuration: 5000})
          }
        
        },(error)=>{
          this.errorMessage = error; // Set the error message to be displayed on the UI
          console.error(error);
        }
        )
      
      // You can send the form data to your backend or perform other actions here
    } else {
      // Handle invalid form submission
      console.log('Invalid form submission. Please check the form.');
    }
  }

  // Function to handle file change event
  handleFileChange(event: any) {
    // Implement file change handling logic here
  }
}
