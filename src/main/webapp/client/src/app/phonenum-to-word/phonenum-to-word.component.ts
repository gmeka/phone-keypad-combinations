import { Component, OnInit, ViewChild } from '@angular/core';
import { PhonenumToWordConverterService } from '../phonenum-to-word-converter.service';
import { ApiResponse } from '../api-response.model';
import { MatTableDataSource, MatPaginator } from '@angular/material';
import { tap } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { NotificationsService } from 'angular2-notifications';
import { AppConfig } from '../app.config';

export class Word {
  public word: String
  constructor(str: string){
    this.word = str;
  }
}

@Component({
  selector: 'app-phonenum-to-word',
  templateUrl: './phonenum-to-word.component.html',
  styleUrls: ['./phonenum-to-word.component.css']
})
export class PhonenumToWordComponent implements OnInit {

  resourcesLoaded = false;
  phoneNumber: string = '';
  displayedColumns: string[] = ['word'];
  public resultList: MatTableDataSource<any[]>;
  private resultCount: number = 0;
  private pageSize: number = 10;
  private pageNum: number = 0;
  private showTable: boolean = false;
  private validationMessage: string = "";

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;

  constructor(private apiService: PhonenumToWordConverterService,
    private notification: NotificationsService
    ) { }


  ngOnInit() {
    this.resourcesLoaded = true;
  }

  getresultCount() {
    this.resourcesLoaded = false;
    this.apiService.getCount(this.phoneNumber).subscribe(
      (response: ApiResponse)=>{
        this.resultCount = response.resultCount;
        this.paginator.length = this.resultCount;
        this.resourcesLoaded = true;
      }, (errorResponse: HttpErrorResponse) => {
        this.showSnackBar(errorResponse);
        this.resourcesLoaded = true;
      }
    );
  }

  getCombinations() {
    console.log(`loading page ${this.pageNum} with ${this.pageSize} results`);
    this.resourcesLoaded = false;
    this.apiService.getCombinations(this.phoneNumber, this.paginator.pageIndex, this.paginator.pageSize).subscribe(
      (response: ApiResponse)=>{
        const data: Word[] = this.getData(response);
        this.showTable = true;
        this.resultList = new MatTableDataSource(this.getData(response) as any[]);
        this.resourcesLoaded = true;
      }, (errorResponse: HttpErrorResponse) => {
        this.showSnackBar(errorResponse);
        this.resourcesLoaded = true;
      }
    );
  }

  getData(response: ApiResponse): Word[]{
    let result: Word[] = [];
    if(!response.combinations || response.combinations.length === 0){
      return result;
    }
    for(let str of response.combinations){
      result.push(new Word(str));
    }
    return result;
  }

  showSnackBar(errorResponse: HttpErrorResponse) {
    errorResponse.error.errors.forEach(msg => {
      this.notification.error('', msg, AppConfig.NOTIFICATION_ERROR_OPTIONS);
    });

  }

  ngAfterViewInit() {
    if(this.paginator){
      this.paginator.page.pipe(
        tap(() => this.loadNextPage())
      ).subscribe();
    }
  }

  loadNextPage() {
    this.getCombinations();
  }

  loadFirstPage() {
    this.pageNum = 0;
    this.getCombinations();
  }

  validate(phoneNumber){
    this.validationMessage = '';
    if(isNaN(phoneNumber)) {
      this.validationMessage = 'Phone number must be numeric';
      return;
    }
    if(!(phoneNumber.length === 7 || phoneNumber.length === 10)){
      this.validationMessage = 'Phone number must be either 7 or 10 digit';
      return;
    }
  }
}
