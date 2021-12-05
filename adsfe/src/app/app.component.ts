import {Component, OnInit} from '@angular/core';
import {NavigationCancel, NavigationEnd, NavigationError, NavigationStart, Router} from "@angular/router";
import {FileService} from "./core/file.service";
import {Observable, of} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  accept: string = '.xlsx,.csv';
  loading = true;
  classFile: File | undefined;
  timetableFile: File | undefined;
  // Groups for steppers
  firstFormGroup: FormGroup = this.fb.group({
    firstCtrl: ['', Validators.required],
  });
  secondFormGroup: FormGroup = this.fb.group({
    secondCtrl: ['', Validators.required],
  });
  thirdFormGroup: FormGroup = this.fb.group({
    thirdCtrl: ['', Validators.required],
  });
  fourthFormGroup: FormGroup = this.fb.group({
    fourthCtrl: ['', Validators.required],
  });
  fifthFormGroup: FormGroup = this.fb.group({
    fifthCtrl: ['', Validators.required],
  });
  // Mappings
  headerClassroom: String[] = [];
  excelClassHeaders$: Observable<String[]> = of([]);
  headerTimetable: String[] = [];
  excelTimetableHeaders$: Observable<String[]> = of([]);

  // Constructor
  timetableValid: boolean = false;
  classValid: boolean = false;
  constructor(private router: Router,
              private fileService: FileService,
              private fb: FormBuilder) {
    // analyse all the request - spinner
    router.events.subscribe((routerEvent: any) => {
      this.checkRouterEvent(routerEvent);
    });
  }

  ngOnInit(): void {
    this.headerClassroom = this.fileService.headersClassroom();
    this.headerTimetable = this.fileService.headersTimetable();
  }

  /**
   * Save Classroom file
   * @param events
   */
  uploadClassFile(events: any) {
    console.log("I'm here class File upload");
    if (events && events.target && events.target.files) {
      this.classFile = events.target.files[0];
      this.firstFormGroup.controls['firstCtrl'].setValue(this.classFile);
      this.excelClassHeaders$ = this.fileService.headers_be(this.classFile);
    }
  }

  /**
   * Save timetable file
   * @param events
   */
  uploadTimetableFile(events: any) {
    console.log("I'm here Timetable File upload");
    if (events && events.target && events.target.files) {
      this.timetableFile = events.target.files[0];
      this.thirdFormGroup.controls['thirdCtrl'].setValue(this.timetableFile);
      this.excelTimetableHeaders$ = this.fileService.headers_be(this.timetableFile);
    }
  }

  /**
   * Spinner logic - trigger loading page
   * @param routerEvent - event
   */
  checkRouterEvent(routerEvent: any): void {
    if (routerEvent instanceof NavigationStart) {
      this.loading = true;
    }

    if (routerEvent instanceof NavigationEnd ||
      routerEvent instanceof NavigationCancel ||
      routerEvent instanceof NavigationError) {
      this.loading = false;
    }
  }

  updateMappingClass(result: any) {
    if(result && this.classValid){
      this.secondFormGroup.controls['secondCtrl'].setValue(result);
    }
  }

  updateMappingTimetable(result: any) {
    if(result && this.timetableValid) {
      this.fourthFormGroup.controls['fourthCtrl'].setValue(result);
    }
  }
  changeTimetableValid(isValid: boolean) {
    this.timetableValid = isValid;
    console.log(this.timetableValid);
  }
  changeClassValid(isValid: boolean) {
    this.classValid = isValid;
    console.log(this.classValid);
  }
}
