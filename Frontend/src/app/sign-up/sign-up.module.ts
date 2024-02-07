import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SignupComponent } from './signup/signup.component';
import { ReactiveFormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { NzFormModule, NzInputModule, NzButtonModule } from 'ng-zorro-antd';

const routes: Routes = [
  { path: '',component:SignupComponent},
];
@NgModule({
  declarations: [SignupComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes), NzFormModule, NzInputModule, NzButtonModule
  ]
})
export class SignUpModule { }
