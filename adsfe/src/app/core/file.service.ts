import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from "@angular/common/http";
import {Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FileService {
  public static readonly BASE_URL_BACK_END = 'https://localhost:8080';

  constructor(private http: HttpClient) {
  }

  headers(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    formData.append('file', file);

    const req = new HttpRequest('GET', `${FileService.BASE_URL_BACK_END}/ads/headers`, formData, {reportProgress: true, responseType: 'json'});
    return this.http.request(req);
  }

  headers_be(file: File): any {
    of(["aadawad", "adawdawd", "dadad"]);
  }

}
