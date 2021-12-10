export interface Timetable {
  course: string;
  unit: string;
  shift: string;
  classNumber: string;
  registeredShift: number;
  overflowShift: boolean;
  overflowEnrollment: boolean;
  dayOfWeek: string;
  begin: string;
  end: string;
  day: Date;
  features: string;
  classRoom: string;
  capacity: number;
  realFeatures: string[];
  hasError: boolean;
  error?: any;
  notError: boolean;
}
