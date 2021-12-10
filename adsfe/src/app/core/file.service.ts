import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpParams, HttpRequest} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {RequestDto} from "../interfaces/request-dto";
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(private http: HttpClient) {
  }

  headers(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    formData.append('file', file);

    const req = new HttpRequest('GET', `${environment.API_URL}/ads/headers`, formData, {
      reportProgress: true,
      responseType: 'json'
    });
    return this.http.request(req);
  }

  headersClassroom(): String[] {
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

  headersExcel(classFile: File | undefined): Observable<String[]> {
    if (classFile != undefined) {
      const formData: FormData = new FormData();
      formData.append('file', classFile);

      // @ts-ignore
      return this.http.post(`${environment.API_URL}/ads/headers`, formData);
    }
    return of([]);
  }

  submit(result: RequestDto): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('classFile', result.classFile);
    formData.append('timetableFile', result.timetableFile);
    let qualities = '';
    let mappingCstr = '';
    let mappingFstr = '';
    result.qualities.forEach(value => qualities += value + ';')
    qualities = qualities.substr(0, qualities.length - 1)
    const mappingC = this.mapToList(result.mappingClass);
    mappingC.forEach(value => mappingCstr += value + ';')
    mappingCstr = mappingCstr.substr(0, mappingCstr.length - 1)
    const mappingF = this.mapToList(result.mappingTimetable);
    mappingF.forEach(value => mappingFstr += value + ';')
    mappingFstr = mappingFstr.substr(0, mappingFstr.length - 1)

    const params = new HttpParams()
      .set('mappingClass', mappingCstr)
      .set('mappingTimetable', mappingFstr)
      .set('qualities', qualities)
      .set('fast', result.fast);
    // , responseType: 'arraybuffer'
    // @ts-ignore
    return this.http.post(`${environment.API_URL}/ads/execute`, formData, {params});
  }

  mapToList(list: any) {
    const mapping = [];
    for (let j = 0; j < 100; j++) {
      if (list[j]) {
        mapping.push(list[j])
      } else {
        break;
      }
    }
    return mapping;
  }

  mapHeadersTimetable() {
    return {
      "Curso": 'course',
      "Unidade de execução": 'unit',
      "Turno": 'shift',
      "Turma": 'classNumber',
      "Inscritos no turno (no 1º semestre é baseado em estimativas)": 'registeredShift',
      "Turnos com capacidade superior à capacidade das características das salas": 'overflowShift',
      "Turno com inscrições superiores à capacidade das salas": 'overflowEnrollment',
      "Dia da Semana": 'dayOfWeek',
      "Início": 'begin',
      "Fim": 'end',
      "Dia": 'day',
      "Características da sala pedida para a aula": 'features',
      "Sala da aula": 'classRoom',
      "Lotação": 'capacity',
      "Características reais da sala": 'realFeatures',
      "Outros": 'error'
    };
  }
}
