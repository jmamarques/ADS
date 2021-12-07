import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {RequestDto} from "../interfaces/request-dto";

@Injectable({
  providedIn: 'root'
})
export class FileService {
  public static readonly BASE_URL_BACK_END = 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  headers(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    formData.append('file', file);

    const req = new HttpRequest('GET', `${FileService.BASE_URL_BACK_END}/ads/headers`, formData, {reportProgress: true, responseType: 'json'});
    return this.http.request(req);
  }

  headersClassroom(): String[]{
    return [
      "Edifício",
      "Nome sala",
      "Capacidade Normal",
      "Capacidade Exame",
      "Nº características",
      "Anfiteatro aulas",
      "Apoio técnico eventos",
      "Arq 1",
      "Arq 2",
      "Arq 3",
      "Arq 4",
      "Arq 5",
      "Arq 6",
      "Arq 9",
      "BYOD (Bring Your Own Device)",
      "Focus Group",
      "Horário sala visível portal público",
      "Laboratório de Arquitectura de Computadores I",
      "Laboratório de Arquitectura de Computadores II",
      "Laboratório de Bases de Engenharia",
      "Laboratório de Electrónica",
      "Laboratório de Informática",
      "Laboratório de Jornalismo",
      "Laboratório de Redes de Computadores I",
      "Laboratório de Redes de Computadores II",
      "Laboratório de Telecomunicações",
      "Sala Aulas Mestrado",
      "Sala Aulas Mestrado Plus",
      "Sala NEE",
      "Sala Provas",
      "Sala Reunião",
      "Sala de Arquitectura",
      "Sala de Aulas normal",
      "videoconferencia",
      "Átrio",
      "Características reais da sala",
      "Outros"
    ];
  }

  headersTimetable() {
    return [
      "Curso",
      "Unidade de execução",
      "Turno",
      "Turma",
      "Inscritos no turno (no 1º semestre é baseado em estimativas)",
      "Turnos com capacidade superior à capacidade das características das salas",
      "Turno com inscrições superiores à capacidade das salas",
      "Dia da Semana",
      "Início",
      "Fim",
      "Dia",
      "Características da sala pedida para a aula",
      "Sala da aula",
      "Lotação",
      "Características reais da sala",
      "Outros"
    ];
  }

  headersExcel(classFile: File | undefined):Observable<String[]> {
    if(classFile != undefined){
      const formData: FormData = new FormData();
      formData.append('file', classFile);

      // @ts-ignore
      return this.http.post(`${FileService.BASE_URL_BACK_END}/ads/headers`, formData);
    }
    return of([]);
  }

  submit(result: RequestDto) {

  }
}
