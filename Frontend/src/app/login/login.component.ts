import { Component, OnInit } from '@angular/core';
//import { AuthenticateService } from '../authenticate.service';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { PATTERN } from '../constant/pattern/pattern';
import { VALIDATION_CRITERIA } from '../constant/validation-crieteria/validation-crieteria';
import { AuthService } from '../auth-services/auth-service/auth.service';
import { StorageService } from '../auth-services/storage-services/storage.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  valid:boolean=false;
  loginForm: FormGroup;
  errorMessage: string;

  constructor(
    private service: AuthService,
     private formBuilder: FormBuilder,
     private router: Router) { }

  ngOnInit(): void {
    this.createForm();
  }
  onLogin() {
    this.service.login(this.loginForm.value).subscribe(
      (res)=>{
      console.log(res);
      if(res.userId !== null){
       const user = {
          id:res.userId,
          role:res.userRole
        }
        console.log(user);
        StorageService.saveToken(res.jwt);
        StorageService.saveUser(user);
        if(StorageService.isAdminLoggedIn()){
          this.router.navigateByUrl("admin/dashboard");
        } else if(StorageService.isCustomerLoggedIn()){
          this.router.navigateByUrl("customer/dashboard");
        }
      }
      else{
        console.log("Wrong Credentials");
      }
    },
    (error)=>{
      this.errorMessage = error; // Set the error message to be displayed on the UI
      console.error(error);
    })
    //console.log(this.loginForm.value);
   // this.auth.authenticate = this.auth.isAuthenticate(this.loginForm.value);
    // if(!this.auth.authenticate)
    // {
    //   this.valid=true;
    // }
   // console.log("logcom", this.auth.authenticate);

  }
  createForm() {
    this.loginForm = this.formBuilder.group(
      {
        email: ['',
          [
            Validators.required,
          ]
        ],

        password: ['',
          [
            Validators.required, Validators.pattern(PATTERN.password),
            Validators.minLength(VALIDATION_CRITERIA.passwordMinLength),
            Validators.maxLength(VALIDATION_CRITERIA.passwordMaxLength)
          ]
        ]
      }
    );
  }
  getUserErrors() {
    let errors = this.loginForm.controls.username.errors;
    if (errors.required) {
      return 'Username is required';
    }
  }

  getPasswordErrors() {
    let errors = this.loginForm.controls.password.errors;

    if (errors.required) {
      return 'password is required';
    }
  }
}