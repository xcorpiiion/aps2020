package br.com.unip.aps.service.utils.ordenacao;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public final class OrdenacaoSelectionSortUtils {

    private OrdenacaoSelectionSortUtils() {

    }

    public static List<DateTime> ordernacaoByDate(List<DateTime> yourDateList, boolean isAcrecente) {
        List<Long> datesMiliSegundos = new ArrayList<>();
        parseDateInMiliSegundos(yourDateList, datesMiliSegundos);
        List<Long> dateMiliSegundosOrdenados;
        dateMiliSegundosOrdenados = verificaSeOrdenacaoIsAscOrDes(isAcrecente, datesMiliSegundos);
        yourDateList = new ArrayList<>();
        parseMiliSegundosInDate(yourDateList, dateMiliSegundosOrdenados);
        return yourDateList;
    }

    private static List<Long> verificaSeOrdenacaoIsAscOrDes(boolean isAcrecente, List<Long> datesMiliSegundos) {
        List<Long> dateMiliSegundosOrdenados;
        if(isAcrecente) {
            dateMiliSegundosOrdenados = obtemMenorElemento(datesMiliSegundos);
        } else {
            dateMiliSegundosOrdenados = obtemMaiorElemento(datesMiliSegundos);
        }
        return dateMiliSegundosOrdenados;
    }

    private static void parseMiliSegundosInDate(List<DateTime> yourDateList, List<Long> dateMiliSegundosOrdenados) {
        for(Long dateOrdenado: dateMiliSegundosOrdenados) {
            yourDateList.add(new DateTime(dateOrdenado));
        }
    }

    private static void parseDateInMiliSegundos(List<DateTime> yourDateList, List<Long> datesMiliSegundos) {
        for(DateTime date : yourDateList) {
            datesMiliSegundos.add(date.getMillis());
        }
    }

    private static List<Long> obtemMenorElemento(List<Long> yourLongLists) {
        for(int i = 0; i < yourLongLists.size(); i++) {
            int menor = i;
            for(int j = i + i; j < yourLongLists.size(); j++) {
                if(yourLongLists.get(j) < yourLongLists.get(menor)) {
                    menor = j;
                }
            }
            trocar(yourLongLists, i, menor);
        }
        return yourLongLists;
    }

    private static List<Long> obtemMaiorElemento(List<Long> yourLongLists) {
        for(int i = 0; i < yourLongLists.size(); i++) {
            int maior = i;
            for(int j = i + i; j < yourLongLists.size(); j++) {
                if(yourLongLists.get(j) > yourLongLists.get(maior)) {
                    maior = j;
                }
            }
            trocar(yourLongLists, i, maior);
        }
        return yourLongLists;
    }

    private static void trocar(List<Long> yourLongLists, int i, int menor) {
        long aux = yourLongLists.get(i);
        yourLongLists.set(i, yourLongLists.get(menor));
        yourLongLists.set(menor, aux);
    }


}
