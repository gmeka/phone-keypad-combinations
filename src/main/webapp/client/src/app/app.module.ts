import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { PhonenumToWordComponent } from './phonenum-to-word/phonenum-to-word.component';
import { HttpClientModule } from '@angular/common/http';
import { MatTableModule, MatInputModule, MatButtonModule, MatProgressSpinnerModule, MatPaginatorModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SimpleNotificationsModule } from 'angular2-notifications';

@NgModule({
  declarations: [
    AppComponent,
    PhonenumToWordComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatTableModule, 
    MatInputModule, 
    MatButtonModule, 
    MatProgressSpinnerModule, 
    MatPaginatorModule,
    SimpleNotificationsModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
