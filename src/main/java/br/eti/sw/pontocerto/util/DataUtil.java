/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Luiz
 */
public class DataUtil {

    public static int getMes(int mes) {
        switch (mes) {
            case 1:
                return Calendar.JANUARY;
            case 2:
                return Calendar.FEBRUARY;
            case 3:
                return Calendar.MARCH;
            case 4:
                return Calendar.APRIL;
            case 5:
                return Calendar.MAY;
            case 6:
                return Calendar.JUNE;
            case 7:
                return Calendar.JULY;
            case 8:
                return Calendar.AUGUST;
            case 9:
                return Calendar.SEPTEMBER;
            case 10:
                return Calendar.OCTOBER;
            case 11:
                return Calendar.NOVEMBER;
            case 12:
                return Calendar.DECEMBER;
            default:
                return Calendar.JANUARY;
        }
    }

    public static String getMesString(int mes) {
        switch (mes) {
            case 0:
                return "janeiro";
            case 1:
                return "fevereiro";
            case 2:
                return "março";
            case 3:
                return "abril";
            case 4:
                return "maio";
            case 5:
                return "junho";
            case 6:
                return "julho";
            case 7:
                return "agosto";
            case 8:
                return "setembro";
            case 9:
                return "outubro";
            case 10:
                return "novembro";
            case 11:
                return "dezembro";
            default:
                return "janeiro";
        }
    }

    public static String getDataFormatadaParaCertificado(Date dataInicio, Date dataFim) {
        StringBuilder dataCertificado = new StringBuilder();
        Calendar dataInicial = Calendar.getInstance();
        dataInicial.setTime(dataInicio);
        Calendar dataFinal = Calendar.getInstance();
        dataFinal.setTime(dataFim);

        if (dataInicial.compareTo(dataFinal) != 0) {
            dataCertificado.append("nos dias ");
            dataCertificado.append(dataInicial.get(Calendar.DAY_OF_MONTH));
            if (dataInicial.get(Calendar.MONTH) == dataFinal.get(Calendar.MONTH)) {
                dataCertificado.append(" e ");
                dataCertificado.append(dataFinal.get(Calendar.DAY_OF_MONTH));
                dataCertificado.append(" de ");
                dataCertificado.append(getMesString(dataInicial.get(Calendar.MONTH)));
                dataCertificado.append(" de ");
                dataCertificado.append(dataInicial.get(Calendar.YEAR));
            } else {
                dataCertificado.append(" de ");
                dataCertificado.append(getMesString(dataInicial.get(Calendar.MONTH)));
                dataCertificado.append(" e ");
                dataCertificado.append(dataFinal.get(Calendar.DAY_OF_MONTH));
                dataCertificado.append(" de ");
                dataCertificado.append(getMesString(dataFinal.get(Calendar.MONTH)));
                dataCertificado.append(" de ");
                dataCertificado.append(dataInicial.get(Calendar.YEAR));
            }
        } else {
            dataCertificado.append("no dia ");
            dataCertificado.append(dataInicial.get(Calendar.DAY_OF_MONTH));
            dataCertificado.append(" de ");
            dataCertificado.append(getMesString(dataInicial.get(Calendar.MONTH)));
            dataCertificado.append(" de ");
            dataCertificado.append(dataInicial.get(Calendar.YEAR));
        }
        return dataCertificado.toString();
    }

    public static String getHora(Date data) {
        SimpleDateFormat dateFormat
                = new SimpleDateFormat("HH:mm", new Locale("pt", "BR"));
        return dateFormat.format(data);
    }

    public static String getData(Date data, String separador) {
        SimpleDateFormat dateFormat
                = new SimpleDateFormat("dd" + separador + "MM" + separador + "yyyy", new Locale("pt", "BR"));
        if (data != null) {
            return dateFormat.format(data);
        } else {
            return "";
        }
    }

    public static String calculaTempoDecorrido(Date horarioOriginal) {
        String unidade = "minutos";
        long numero = 0;
        final long MINUTOS = 60000;
        final long HORAS = 60 * MINUTOS;
        final long DIAS = 24 * HORAS;
        final long MESES = 30 * DIAS;
        final long ANOS = 12 * MESES;
        numero = new Date().getTime() - horarioOriginal.getTime();
        long tempoDecorrido = 0;

        if (numero > MINUTOS) {
            tempoDecorrido = numero / MINUTOS;
            unidade = (tempoDecorrido > 1) ? "minutos" : "minuto";
            if (numero > HORAS) {
                tempoDecorrido = numero / HORAS;
                unidade = (tempoDecorrido > 1) ? "horas" : "hora";
                if (numero > DIAS) {
                    tempoDecorrido = numero / DIAS;
                    unidade = (tempoDecorrido > 1) ? "dias" : "dia";
                    if (numero > MESES) {
                        tempoDecorrido = numero / MESES;
                        unidade = (tempoDecorrido > 1) ? "meses" : "mês";
                        if (numero > ANOS) {
                            tempoDecorrido = numero / ANOS;
                            unidade = (tempoDecorrido > 1) ? "anos" : "ano";
                        }
                    }
                }
            }
        } else {
            tempoDecorrido = 0;
            unidade = "minutos";
        }

        String tempo = tempoDecorrido + " " + unidade + " atrás";

        return tempo;
    }

    public static String getAbreviacaoMes(int mes) {
        if (mes == 0) {
            return "JAN";
        }
        if (mes == 1) {
            return "FEV";
        }
        if (mes == 2) {
            return "MAR";
        }
        if (mes == 3) {
            return "ABR";
        }
        if (mes == 4) {
            return "MAI";
        }
        if (mes == 5) {
            return "JUN";
        }
        if (mes == 6) {
            return "JUL";
        }
        if (mes == 7) {
            return "AGO";
        }
        if (mes == 8) {
            return "SET";
        }
        if (mes == 9) {
            return "OUT";
        }
        if (mes == 10) {
            return "NOV";
        }
        if (mes == 11) {
            return "DEZ";
        }
        return null;
    }

    public static String converterData(Date data, DataUtil.DataFormat dataType) {
        String dataFormatada = "";
        SimpleDateFormat dateFormat;
        switch (dataType) {
            case DDMMAAAA:
                dateFormat
                        = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
                break;
            case PARARSS:
                dateFormat
                        = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss a", new Locale("pt", "BR"));
                break;
            case DDMMAAAAHHMM:
                dateFormat
                        = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("pt", "BR"));
                break;
            case DDMMAAAAHHMMSS:
                dateFormat
                        = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", new Locale("pt", "BR"));
                break;
            case MMAAAA:
                dateFormat
                        = new SimpleDateFormat("MM/yyyy", new Locale("pt", "BR"));
                break;
            case HHMM:
                dateFormat
                        = new SimpleDateFormat("HH:mm", new Locale("pt", "BR"));
                break;
            default:
                dateFormat
                        = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        }
        dataFormatada = dateFormat.format(data);
        return dataFormatada;

    }

    public static Date converterData(String data, DataUtil.DataFormat dataType) {
        Date dataFormatada;
        SimpleDateFormat dateFormat;
        switch (dataType) {
            case DDMMAAAA:
                dateFormat
                        = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
                break;
            case PARARSS:
                dateFormat
                        = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss a", new Locale("pt", "BR"));
                break;
            case DDMMAAAAHHMM:
                dateFormat
                        = new SimpleDateFormat("dd/MM/yyyy hh:mm", new Locale("pt", "BR"));
                break;
            case DDMMAAAAHHMMSS:
                dateFormat
                        = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", new Locale("pt", "BR"));
                break;
            case HHMM:
                dateFormat
                        = new SimpleDateFormat("hh:mm", new Locale("pt", "BR"));
                break;
            case MMAAAA:
                dateFormat
                        = new SimpleDateFormat("MM/yyyy", new Locale("pt", "BR"));
                break;
            default:
                dateFormat
                        = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        }
        try {
            dataFormatada = dateFormat.parse(data);
        } catch (Exception ex) {
            dataFormatada = new Date();
        }
        return dataFormatada;
    }

    public enum DataFormat {

        DDMMAAAA, PARARSS, DDMMAAAAHHMM, DDMMAAAAHHMMSS, HHMM, MMAAAA;
    }
}
