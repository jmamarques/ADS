export interface RequestDto {
  classFile: File;
  timetableFile: File;
  mappingClass: any;
  mappingTimetable: any;
  qualities: string[];
  fast: boolean;
  maxGenerations: number;
  populationSize: number;
  dateFormat?: any;
}
