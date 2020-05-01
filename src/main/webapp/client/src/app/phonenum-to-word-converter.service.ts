import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { ApiResponse } from './api-response.model';

@Injectable({
  providedIn: 'root'
})
export class PhonenumToWordConverterService {

  baseurl = environment.apiEndpoint;
  headers = {
      headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*'
      })
  };

  constructor(private httpClient: HttpClient) {}

  getCombinations(phoneNumber:string, page: number = 0, size: number = 10) {
    return this.httpClient.get(`${this.baseurl}/${phoneNumber}/combinations`, {
        params: new HttpParams()
            .set('courseId', phoneNumber.toString())
            .set('page', page.toString())
            .set('size', size.toString())
    });
  }

  getCount(phoneNumber:string) {
    return this.httpClient.get(`${this.baseurl}/${phoneNumber}/combinations/count`, this.headers);
  }


}
