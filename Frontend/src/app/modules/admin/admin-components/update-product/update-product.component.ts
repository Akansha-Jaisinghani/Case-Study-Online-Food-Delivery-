import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../admin-services/admin.service';
import { NzMessageService } from 'ng-zorro-antd';
import { ActivatedRoute, Router } from '@angular/router';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.css']
})
export class UpdateProductComponent implements OnInit {

  dishForm: FormGroup;
  dishId: any= this.activatedroute.snapshot.params['dishId'];
  imgChanged= false;
  selectedFile: any;
  imagePreview: string | ArrayBuffer | null = null;
  existingImage: string | SafeResourceUrl  | null = null;

  constructor(private service: AdminService,
              private fb: FormBuilder,
              private message:NzMessageService,
              private router: Router,
              private activatedroute: ActivatedRoute,
              private sanitizer: DomSanitizer ) { }

  ngOnInit() {
    this.dishForm = this.fb.group({
      dishName: ['', Validators.required],
      cost: ['', [Validators.required, Validators.pattern(/^\d+(\.\d{1,2})?$/)]]
      //quantity: ['', [Validators.required, Validators.pattern(/^[1-9]\d*$/)]]
    });
    this.getDishesById();
  }

  getDishesById(){
    this.service.getAllDishesById(this.dishId).subscribe(
      (res:any) => {
        console.log(res);
        const dishDto = res;
        // this.existingImage = 'data:image;base64,' + res.returnedImg;
        const img = 'data:image;base64,' + res.returnedImg;
        // Sanitize the image source URL
        const sanitizedImgSrc: SafeResourceUrl = this.sanitizer.bypassSecurityTrustResourceUrl(img);
        this.existingImage = sanitizedImgSrc;
        this.dishForm.patchValue(dishDto);
      });
  }  

  updateDish(): void {
    const formData: FormData = new FormData(); 
    if (this.imgChanged && this.selectedFile){
    formData.append('img', this.selectedFile)
    }
    formData.append("dishName",this.dishForm.get("dishName").value)
    formData.append("cost",this.dishForm.get("cost").value)
    console.log(formData); 
    this.service.updateDish(this.dishId, formData).subscribe((res) => { 
    console.log(res);
    if (res.id !== null) {  
    this.message.success("Product updated Successfully.",{ nzDuration: 5000 });
    this.router.navigateByUrl('/admin/dashboard');
    } else {
    this.message.error("Something went wrong",{ nzDuration: 5000 })
    }
    });
  }

  onFileSelected(event:any){
    this.selectedFile = event.target.files[0];
    this.previewImage();
    this.imgChanged = true;
    this.existingImage= null;
  }

  previewImage(){
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview =reader.result;
    };
    reader.readAsDataURL(this.selectedFile);
  }

  

}
