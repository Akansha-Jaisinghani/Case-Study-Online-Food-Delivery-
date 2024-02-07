import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login.component';
import { Routes, RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { NzFormModule, NzInputModule, NzButtonModule } from 'ng-zorro-antd';



const routes: Routes = [
  { path: '',component:LoginComponent},
];
@NgModule({
  declarations: [LoginComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),NzFormModule, NzInputModule, NzButtonModule
  ]

})
export class LoginModule { }
