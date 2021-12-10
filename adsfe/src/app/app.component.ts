import {Component, OnInit} from '@angular/core';
import {NavigationCancel, NavigationEnd, NavigationError, NavigationStart, Router} from "@angular/router";
import {FileService} from "./core/file.service";
import {Observable, of} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatListOption} from "@angular/material/list";
import {MatStepper} from "@angular/material/stepper";
import {RequestDto} from "./interfaces/request-dto";
import {MatDialog} from "@angular/material/dialog";
import {DialogError} from "./components/dialog-error";
import * as fileSaver from 'file-saver';
import * as XLSX from 'xlsx';
import {MatSelectChange} from "@angular/material/select";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  accept: string = '.xlsx,.csv,.xls';
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
  // Auxiliary variables
  timetableValid: boolean = false;
  classValid: boolean = false;
  // fast attribute
  isChecked = true;
  // final check
  isDone: boolean = false;
  // formats available
  formats = ['json', 'excel', 'csv'];
  formatFormGroup: FormGroup = this.fb.group({
    format: ['', Validators.required],
  });
  format = 'csv';
  jsonResult: any;
  currentExcelHeaders: any;

  // Constructor
  constructor(private router: Router,
              private fileService: FileService,
              private fb: FormBuilder,
              public dialog: MatDialog) {
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
    this.loading = true;
    console.log("I'm here class File upload");
    if (events && events.target && events.target.files) {
      this.classFile = events.target.files[0];
      this.firstFormGroup.controls['firstCtrl'].setValue(this.classFile);
      this.excelClassHeaders$ = this.fileService.headersExcel(this.classFile);
    }
    this.loading = false;
  }

  /**
   * Save timetable file
   * @param events
   */
  uploadTimetableFile(events: any) {
    this.loading = true;
    console.log("I'm here Timetable File upload");
    if (events && events.target && events.target.files) {
      this.timetableFile = events.target.files[0];
      this.thirdFormGroup.controls['thirdCtrl'].setValue(this.timetableFile);
      this.excelTimetableHeaders$ = this.fileService.headersExcel(this.timetableFile);
    }
    this.loading = false;
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

  /**
   * Update final form group
   * @param result - results
   */
  updateMappingClass(result: any) {
    if (result && this.classValid) {
      this.secondFormGroup.controls['secondCtrl'].setValue(result);
    }
  }

  /**
   * Update final form group
   * @param result - results
   */
  updateMappingTimetable(result: any) {
    if (result && this.timetableValid) {
      this.fourthFormGroup.controls['fourthCtrl'].setValue(result);
    }
  }

  /**
   * Auxiliary variable to control data assign to form group
   * @param isValid - true if it is valid
   */
  changeTimetableValid(isValid: boolean) {
    this.timetableValid = isValid;
  }

  /**
   * Auxiliary variable to control data assign to form group
   * @param isValid - true if it is valid
   */
  changeClassValid(isValid: boolean) {
    this.classValid = isValid;
  }

  /**
   * List of qualities
   */
  typeofQuality:
    string[] = [
    'Tolerância na quantidade de alunos alocados nas salas',
    'O menor número de aulas sem salas alocadas',
    'O menor número de mudanças de salas em conjuntos de aulas',
    'O menor número de mudanças de edifícios em conjuntos de aulas',
    'Maior número de auditórios, com várias horas seguidas, sem alocação de aulas',
    'O menor número de horas entre aulas'];


  /**
   * Update the form group with correct value
   * @param listOptions - list of options
   */
  updateCriterias(listOptions: MatListOption[]) {
    let result = listOptions.map(v => v.value);
    if (result && result.length > 0) {
      this.fifthFormGroup.controls['fifthCtrl'].setValue(result);
    }else{
      this.fifthFormGroup.controls['fifthCtrl'].setValue('');
    }
  }

  /**
   * Choose if you want a fast generation or not
   */
  toggle() {
    this.isChecked = !this.isChecked;
  }

  /**
   * Reset the stepper change variable isDone to not finished
   * @param stepper
   */
  reset(stepper: MatStepper) {
    stepper.reset();
    this.isDone = false;
  }

  /**
   * Submit form and save answer to convert to desire format
   * @param stepper - update state on stepper
   */
  finalForm(stepper: MatStepper) {
    if (!this.classFile) {
      this.dialog.open(DialogError, {data: { errors: ['O ficheiro das aulas deve ser carregado, verifique que está tudo bem']}});
    } else if (!this.timetableFile) {
      this.dialog.open(DialogError, {data: { errors: ['O ficheiro dos horários deve ser carregado, verifique que está tudo bem']}});
    } else if (!this.secondFormGroup.controls['secondCtrl'].value) {
      this.dialog.open(DialogError, {data: { errors: ['Por favor verifique que está tudo bem com o mapping das aulas']}});
    } else if (!this.fourthFormGroup.controls['fourthCtrl'].value) {
      this.dialog.open(DialogError, {data: { errors: ['Por favor verifique que está tudo bem com o mapping dos horários']}});
    } else if (!this.fifthFormGroup.controls['fifthCtrl'].value) {
      this.dialog.open(DialogError, {data: { errors: ['Selecione pelo menos um critério']}});
    } else if (
      this.classFile &&
      this.timetableFile &&
      this.secondFormGroup.controls['secondCtrl'].value &&
      this.fourthFormGroup.controls['fourthCtrl'].value &&
      this.fifthFormGroup.controls['fifthCtrl'].value
    ) {
      stepper.next();
      // final object to send to BE
      let result: RequestDto = {
        fast: this.isChecked,
        classFile: this.classFile,
        timetableFile: this.timetableFile,
        mappingClass: this.secondFormGroup.controls['secondCtrl'].value,
        mappingTimetable: this.fourthFormGroup.controls['fourthCtrl'].value,
        qualities: this.fifthFormGroup.controls['fifthCtrl'].value
      };
      this.isDone=false;
      this.fileService.submit(result).subscribe(value => {
        console.log("Save json response");
        this.jsonResult = value;
        this.isDone=true;
      }, error => {
        console.log(error);
        this.isDone=true;
      });

    } else {
      console.log("Final Form with problems");
    }
  }

  /**
   * Method is use to download file.
   * @param data - Array Buffer data
   * @param type - type of the document.
   */
  downLoadFile(data: any, type: string) {
    let blob = new Blob([data], { type: type});
    // let url = window.URL.createObjectURL(blob);
    // let pwa = window.open(url);
    fileSaver.saveAs(blob, 'Timetable');
    // if (!pwa || pwa.closed || typeof pwa.closed == 'undefined') {
    //   alert( 'Please disable your Pop-up blocker and try again.');
    // }
  }

  /**
   * Convert json to csv (string)
   * @param objArray - data to convert
   * @param headersServer - headers from server
   * @param headersExcel - headers from file
   * @param mapHeadersServer - mapping
   * @param splitter - splitter on csv
   */
  convertToCSV(objArray: any, headersServer: string[], headersExcel: any,  mapHeadersServer:any, splitter=';') {
    let array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
    let str = '';
    let row = '';

    for (let header in headersExcel) {
      row += headersExcel[header] + splitter;
    }
    row = row.slice(0, -1);
    str += row + '\r\n';
    for (let i = 0; i < array.length; i++) {
      let line = '';
      for (let index in headersServer) {
        let head = mapHeadersServer[headersServer[index]];

        line += array[i][head]+ splitter;
      }
      str += line.slice(0, -1) + '\r\n';
    }
    return str;
  }

  formatChange(format: MatSelectChange) {
    this.format = format.value;
  }

  download() {
    this.isDone = false;
    let headersServer = this.fileService.mapToList(this.fourthFormGroup.controls['fourthCtrl'].value);
    let headersExcel = this.currentExcelHeaders;
    let mapHeadersServer = this.fileService.mapHeadersTimetable();
    let type = 'text/csv;charset=utf-8;';
    switch (this.format) {
      case 'json':
        if(Array.isArray(this.jsonResult)) {
          const list: any[] = this.jsonResult
          for (let i = 0; i < list.length; i++) {
            const dataJson = JSON.stringify(list[i]);
            const typeJson = 'application/json';
            this.downLoadFile(dataJson, typeJson);
          }
        }
        break;
      case 'excel':
        if(Array.isArray(this.jsonResult)){
          const list: any[] = this.jsonResult
          for (let i = 0; i < list.length; i++) {
            const csvData = this.convertToCSV(list[i], headersServer, headersExcel, mapHeadersServer);
            let rawExcel = this.convertCsvToExcelBuffer(csvData);
            let blob = this.base64toBlob(rawExcel, 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
            fileSaver.saveAs(blob, 'Timetable.xlsx');
          }
        }
        break;
      case 'csv':
        headersServer = this.fileService.mapToList(this.fourthFormGroup.controls['fourthCtrl'].value);
        headersExcel = this.currentExcelHeaders;
        mapHeadersServer = this.fileService.mapHeadersTimetable();
        type = 'text/csv;charset=utf-8;';
        if(Array.isArray(this.jsonResult)){
          const list: any[] = this.jsonResult
          for (let i = 0; i < list.length; i++) {
            const csvData = this.convertToCSV(list[i], headersServer, headersExcel, mapHeadersServer);
            const data = '\ufeff' + csvData;
            this.downLoadFile(data, type);
          }
        }
    }
    this.isDone = true;
  }

  updateCurrentHeader(header: any) {
    this.currentExcelHeaders = header;
  }

  convertCsvToExcelBuffer = (csvString: string, splitter=';') => {
    const arrayOfArrayCsv = csvString.split("\r\n").map((row: string) => {
      return row.split(splitter)
    });
    const wb = XLSX.utils.book_new();
    const newWs = XLSX.utils.aoa_to_sheet(arrayOfArrayCsv);
    XLSX.utils.book_append_sheet(wb, newWs);
    const rawExcel = XLSX.write(wb, { type: 'base64' })
    return rawExcel
  }

  base64toBlob = (base64Data:any, contentType:any) => {
    contentType = contentType || '';
    let sliceSize = 1024;
    let byteCharacters = atob(base64Data);
    let bytesLength = byteCharacters.length;
    let slicesCount = Math.ceil(bytesLength / sliceSize);
    let byteArrays = new Array(slicesCount);
    for (let sliceIndex = 0; sliceIndex < slicesCount; ++sliceIndex) {
      let begin = sliceIndex * sliceSize;
      let end = Math.min(begin + sliceSize, bytesLength);

      let bytes = new Array(end - begin);
      for (var offset = begin, i = 0; offset < end; ++i, ++offset) {
        bytes[i] = byteCharacters[offset].charCodeAt(0);
      }
      byteArrays[sliceIndex] = new Uint8Array(bytes);
    }
    return new Blob(byteArrays, { type: contentType });
  }
}
