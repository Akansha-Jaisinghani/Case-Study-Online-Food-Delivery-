import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../admin-services/admin.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-item',
  templateUrl: './add-item.component.html',
  styleUrls: ['./add-item.component.css']
})
export class AddItemComponent implements OnInit {

  dishForm: FormGroup;
  restaurants: any[];
  selectedFile: File | null;
  imagePreview: string | ArrayBuffer | null;
  errorMessage: string;

  constructor(private service: AdminService,
              private fb: FormBuilder,
              private message:NzMessageService,
              private router:Router) { }

  ngOnInit() {
    this.dishForm = this.fb.group({
      foodName: ['', Validators.required],
      price: ['', [Validators.required, Validators.pattern(/^\d+(\.\d{1,2})?$/)]],
     // quantity: ['', [Validators.required, Validators.pattern(/^[1-9]\d*$/)]],
      restaurantName: [null, Validators.required],
    });
    this.loadRestaurants();
  }

  loadRestaurants() {
     // Replace 'your_api_endpoint' with your actual API endpoint
      this.service.getRestaurant().subscribe(
        (response) => {
          this.restaurants = response.map(restaurant => ({
            id: restaurant.restaurantId,
            name: restaurant.restaurantName
          }));
        },
        (error) => {
          console.error('Error loading restaurants:', error);
        }
      );
      
  }
   

  onFileSelected(event:any){
    this.selectedFile = event.target.files[0];
    this.previewImage();
  }

  previewImage(){
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview =reader.result;
    };
    reader.readAsDataURL(this.selectedFile);
  }

  onSubmit(): void {
    console.log(this.restaurants);
    if (this.dishForm.valid) {
      // Perform the API call or other actions here
      const formData:FormData= new FormData;
      const formDataVal = this.dishForm.value;
      formData.append("img",this.selectedFile)
      formData.append("dishName",this.dishForm.get("foodName").value)
      formData.append("cost",this.dishForm.get("price").value)
    
      // Append the selected restaurant ID to the form data
      formDataVal.restaurantId = this.dishForm.get('restaurantName').value;
      this.service.addDish(formDataVal.restaurantId,formData).subscribe(
        (res)=>{
        console.log(res)     
        if(res.id !== null){
          this.message.success("Item Added Successfully",{nzDuration: 5000})
          this.router.navigateByUrl(`/admin/${formDataVal.restaurantId}/view_dishes`);
        }else if(res.id == null){
          this.message.error("Something Went Wrong",{nzDuration: 5000})
        }
        } , (error) => {
          this.message.error('Error updating restaurant', { nzDuration: 5000 });
         
            this.errorMessage = error; // Set the error message to be displayed on the UI
            console.error(error);
          
        }
        
        
      );
      console.log('Form submitted with data:', this.dishForm.value);
    } else {
      // Handle form validation errors
      console.log('Form is invalid. Please check the fields.');
    }
  }

}
