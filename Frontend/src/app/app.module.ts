import {HttpClientModule} from '@angular/common/http'
import { BrowserModule } from '@angular/platform-browser';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NgModule } from '@angular/core';
import { NzFormModule, NzInputModule, NzButtonModule,NgZorroAntdModule } from 'ng-zorro-antd';
import {WrapperModule} from './wrapper/wrapper.module';
import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    NgZorroAntdModule,
    NzLayoutModule,
    HttpClientModule,ReactiveFormsModule,
    NzFormModule,NzInputModule, NzButtonModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    AppRoutingModule,
    WrapperModule,
    FormsModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
