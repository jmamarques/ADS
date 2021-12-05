package com.ads.utils.constants;

/**
 * JMA - 24/11/2021 23:09
 * File with constants relevant for the project
 **/
public class GeneralConst {
    // Timetable fields
    public final static String F_T_1 = "Curso";
    public final static String F_T_2 = "Unidade de execução";
    public final static String F_T_3 = "Turno";
    public final static String F_T_4 = "Turma";
    public final static String F_T_5 = "Inscritos no turno (no 1º semestre é baseado em estimativas)";
    public final static String F_T_6 = "Turnos com capacidade superior à capacidade das características das salas";
    public final static String F_T_7 = "Turno com inscrições superiores à capacidade das salas";
    public final static String F_T_8 = "Dia da Semana";
    public final static String F_T_9 = "Início";
    public final static String F_T_10 = "Fim";
    public final static String F_T_11 = "Dia";
    public final static String F_T_12 = "Características da sala pedida para a aula";
    public final static String F_T_13 = "Sala da aula";
    public final static String F_T_14 = "Lotação";
    public final static String F_T_15 = "Características reais da sala";
    public final static String F_T_OTHERS = "Outros";
    // Class fields
    public final static String F_C_1 = "Edifício";
    public final static String F_C_2 = "Nome sala";
    public final static String F_C_3 = "Capacidade Normal";
    public final static String F_C_4 = "Capacidade Exame";
    public final static String F_C_5 = "Nº características";
    public final static String F_C_6 = "Anfiteatro aulas";
    public final static String F_C_7 = "Apoio técnico eventos";
    public final static String F_C_8 = "Arq 1";
    public final static String F_C_9 = "Arq 2";
    public final static String F_C_10 = "Arq 3";
    public final static String F_C_11 = "Arq 4";
    public final static String F_C_12 = "Arq 5";
    public final static String F_C_13 = "Arq 6";
    public final static String F_C_14 = "Arq 9";
    public final static String F_C_15 = "BYOD (Bring Your Own Device)";
    public final static String F_C_16 = "Focus Group";
    public final static String F_C_17 = "Horário sala visível portal público";
    public final static String F_C_18 = "Laboratório de Arquitectura de Computadores I";
    public final static String F_C_19 = "Laboratório de Arquitectura de Computadores II";
    public final static String F_C_20 = "Laboratório de Bases de Engenharia";
    public final static String F_C_21 = "Laboratório de Electrónica";
    public final static String F_C_22 = "Laboratório de Informática";
    public final static String F_C_23 = "Laboratório de Jornalismo";
    public final static String F_C_24 = "Laboratório de Redes de Computadores I";
    public final static String F_C_25 = "Laboratório de Redes de Computadores II";
    public final static String F_C_26 = "Laboratório de Telecomunicações";
    public final static String F_C_27 = "Sala Aulas Mestrado";
    public final static String F_C_28 = "Sala Aulas Mestrado Plus";
    public final static String F_C_29 = "Sala NEE";
    public final static String F_C_30 = "Sala Provas";
    public final static String F_C_31 = "Sala Reunião";
    public final static String F_C_32 = "Sala de Arquitectura";
    public final static String F_C_33 = "Sala de Aulas normal";
    public final static String F_C_34 = "videoconferencia";
    public final static String F_C_35 = "Átrio";
    public final static String F_C_36 = "Características reais da sala";
    public final static String F_C_OTHERS = "Outros";
    // Criteria
    public static final String C1 = "Baixo número sem alocações";
    public static final String C2 = "Capacidade dentro dos limites";
    public static final String C3 = "Características dentro das especificações ";
    // General Consts
    public final static String[] CRITERIA = new String[]{C1, C2, C3};
    public final static String[] CLASS_FIELD_MAPPING = new String[]{F_C_1, F_C_2, F_C_3, F_C_4, F_C_5, F_C_6, F_C_7, F_C_8, F_C_9, F_C_10, F_C_11, F_C_12, F_C_13, F_C_14, F_C_15, F_C_16, F_C_17, F_C_18, F_C_19, F_C_20, F_C_21, F_C_22, F_C_23, F_C_24, F_C_25, F_C_26, F_C_27, F_C_28, F_C_29, F_C_30, F_C_31, F_C_32, F_C_33, F_C_34, F_C_35, F_C_36, F_C_OTHERS};
    public final static String[] TIMETABLE_FIELD_MAPPING = new String[]{F_T_1, F_T_2, F_T_3, F_T_4, F_T_5, F_T_6, F_T_7, F_T_8, F_T_9, F_T_10, F_T_11, F_T_12, F_T_13, F_T_14, F_T_15, F_T_OTHERS};
    public final static String TIMETABLE_FIELD_MAPPING_STR = "[\n" +
            "  \"Curso\",\n" +
            "  \"Unidade de execução\",\n" +
            "  \"Turno\",\n" +
            "  \"Turma\",\n" +
            "  \"Inscritos no turno (no 1º semestre é baseado em estimativas)\",\n" +
            "  \"Turnos com capacidade superior à capacidade das características das salas\",\n" +
            "  \"Turno com inscrições superiores à capacidade das salas\",\n" +
            "  \"Dia da Semana\",\n" +
            "  \"Início\",\n" +
            "  \"Fim\",\n" +
            "  \"Dia\",\n" +
            "  \"Características da sala pedida para a aula\",\n" +
            "  \"Sala da aula\",\n" +
            "  \"Lotação\",\n" +
            "  \"Características reais da sala\",\n" +
            "  \"Outros\"\n" +
            "]";
    // Excel Const
    public final static String[] EXCEL_EXTENSION = new String[]{"xlsx", "xls"};
    public final static String CSV_EXTENSION = "csv";
}
