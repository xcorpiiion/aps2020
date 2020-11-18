package br.com.unip.aps.controller.utils;

import org.joda.time.DateTime;

public final class DateTimeFormatterUtils {

    public static String dateTimeFormatter(DateTime yourDateTime) {
        String dia = String.valueOf(yourDateTime.dayOfMonth().get());
        String mes = String.valueOf(yourDateTime.getMonthOfYear());
        String ano = String.valueOf(yourDateTime.getYear());
        return dia + "/" + mes + "/" + ano;
    }

}
