import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControl, FormControl } from '@angular/forms';
import { PATTERN } from '../../constant/pattern/pattern';
import { VALIDATION_CRITERIA } from '../../constant/validation-crieteria/validation-crieteria';
import { AuthService } from 'src/app/auth-services/auth-service/auth.service';
import { ToastrService } from 'ngx-toastr';
import { NzMessageService } from 'ng-zorro-antd';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signUpForm: FormGroup;
  visiblePassword = 'view';
  showpassword = false;
  visibleRepassword = 'view';
  showRepassword = false;
  errorMessage: string;
  
  constructor(private service: AuthService,private formBuilder: FormBuilder,
    private notification: NzMessageService,private router: Router) { }

  ngOnInit(): void {
    this.createForm()
  }

  createForm() {
    this.signUpForm = this.formBuilder.group(
      {
        name: ['',
          [
            Validators.required,
            Validators.pattern(PATTERN.name),
            Validators.minLength(VALIDATION_CRITERIA.nameMinLength),
            Validators.maxLength(VALIDATION_CRITERIA.nameMaxLength),
          ]
        ],
        email: ['',
          [
            Validators.required,
            Validators.pattern(PATTERN.email),
            Validators.maxLength(VALIDATION_CRITERIA.emailMaxLength)
          ]
        ],
        // phone: ['',
        //   [
        //     Validators.required,
        //     Validators.pattern(PATTERN.phone),
        //     Validators.minLength(VALIDATION_CRITERIA.mobMaxLength),
        //   ]
        // ],
        password: ['',
          [
            Validators.required,this.passWordValidation
          ]
        ],
        rePassword: ['',
          [
            Validators.required,this.passWordValidation
          ]
        ],

      },
      {
        validators: [this.checkPassword]
      }
    )
  }

  passWordValidation(control:FormControl):{[s:string]:boolean}
  {
    const passRegx=/^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$/;
    const value = control && control.value;
    if(!passRegx.test(value))
    {
      return {passinvalid:true}
    }
    else
    {
      return null;
    }
  }
  checkPassword(form: AbstractControl) {
    const password = form.get('password').value;
    const rePasswordControl = form.get('rePassword');
    const rePassword = rePasswordControl.value;
    let rePasswordErrors = rePasswordControl.errors;
    if (password != rePassword) {
      rePasswordErrors = { ...rePasswordErrors, invalid: true };
      rePasswordControl.setErrors(rePasswordErrors);
    }
    else if (rePasswordErrors && rePasswordErrors.invalid) {
      delete rePasswordErrors.invalid;
      rePasswordControl.setErrors(rePasswordErrors);
      rePasswordControl.updateValueAndValidity();
    }
  }
  getNameErrors() {
    let errors = this.signUpForm.controls.name.errors;
    if (errors.required) {
      return 'Name is required';
    }
    if (errors.pattern) {
      return 'Not a valid Name';
    }
    if (errors.maxlength) {
      return `Name can not be more than ${VALIDATION_CRITERIA.nameMaxLength} chars long`;
    }
  }
  getAgeErrors() {
    let errors = this.signUpForm.controls.age.errors;
    if (errors.required) {
      return 'Age is required';
    }
    if (errors.max || errors.min) {
      return 'Enter Valid Age';
    }
    if (errors.pattern) {
      return 'Not a valid Name';
    }
  }
  getPhoneErrors() {
    let errors = this.signUpForm.controls.phone.errors;

    if (errors.required) {
      return 'Mobile No. is required';
    }
    if (errors.maxlength) {                                                                       //do this with regex
      return `Mobile No. can not be more than ${VALIDATION_CRITERIA.mobMaxLength} digit long`;
    }
    if (errors.pattern) {
      return 'Not a valid mobile Number';
    }
  }
  getEmailErrors() {
    let errors = this.signUpForm.controls.email.errors;

    if (errors.required) {
      return 'Email is required';
    }
    if (errors.maxlength) {
      return `Email can not be more than ${VALIDATION_CRITERIA.emailMaxLength} chars long`;
    }
    if (errors.pattern) {
      return 'Not a valid email';
    }
  }
  
  showPassword() {
    // console.log( this.signUpForm.get('password').touched )

    // if(this.signUpForm.get('password').touched)
    this.showpassword=!this.showpassword;
    this.showpassword ? this.visiblePassword = 'Hide' : this.visiblePassword = 'View';
  }
  showRePassword() {
    // console.log( this.signUpForm.get('password').touched )

    // if(this.signUpForm.get('password').touched)
    this.showRepassword=!this.showRepassword;
    this.showRepassword ? this.visibleRepassword = 'Hide' : this.visibleRepassword = 'View';
  }
  onSignUp()
  {
    console.log(this.signUpForm.value);
    this.service.signup(this.signUpForm.value).subscribe((res)=>{
      this.errorMessage = ''
       console.log(res);
       if(res.userId !== null){
        console.log(res.userId);
        this.notification.success("You are registered successfully",{ nzDuration : 1000})
        this.router.navigateByUrl('/login');
       }
       else{
        this.notification.success("Something Went wrong",{ nzDuration : 5000})   
       }
    },(error)=>{
      this.errorMessage = error; // Set the error message to be displayed on the UI
      console.error(error);
    }
   
    );
  }

}
