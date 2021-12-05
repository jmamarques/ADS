import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {FileService} from "../../core/file.service";
import {FormBuilder} from "@angular/forms";
import {Observable} from "rxjs";

@Component({
  selector: 'app-mapping',
  templateUrl: './mapping.component.html',
  styleUrls: ['./mapping.component.css']
})
export class MappingComponent implements OnInit {
  headersExcelClass$: Observable<String[]> | undefined;
  headersClass$: Observable<String[]> | undefined;
  classFile: File | undefined;
  timetableFile: File | undefined;

  constructor(private router: Router,
              private fileService: FileService,
              private fb: FormBuilder) { }

  ngOnInit(): void {
    this.headersExcelClass$ = this.fileService.headers_be(this.classFile);
    this.headersClass$ = this.fileService.headers_be(this.classFile);
  }

}
