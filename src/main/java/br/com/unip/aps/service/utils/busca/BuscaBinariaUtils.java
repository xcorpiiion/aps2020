package br.com.unip.aps.service.utils.busca;

import br.com.unip.aps.model.Noticia;
import br.com.unip.aps.service.utils.ordenacao.enums.EnumTipoOrdenacao;
import org.joda.time.DateTime;

import java.util.*;

import static br.com.unip.aps.service.utils.ordenacao.OrdenacaoBubbleSortUtils.ordenacaoByString;
import static br.com.unip.aps.service.utils.ordenacao.OrdenacaoQuickSortUtils.ordernacaoById;
import static br.com.unip.aps.service.utils.ordenacao.OrdenacaoSelectionSortUtils.ordernacaoByDate;
import static java.lang.Math.toIntExact;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

public final class BuscaBinariaUtils {

    private BuscaBinariaUtils() {

    }

    public static Noticia findById(List<Noticia> noticias, Long id) {
        List<Long> ids = new ArrayList<>();
        for (Noticia noticia : noticias) {
            ids.add(Long.valueOf(noticia.getId()));
        }
        ordernacaoById(ids, true);
        final Long idFound = buscaBinariaRecursiva(ids, id, true);
        noticias.removeIf(e -> e.getId() != toIntExact(idFound));
        if (isEmpty(noticias)) {
            throw new IllegalStateException("Não existe uma noticia com esse ID");
        }
        return noticias.get(0);
    }

    public static List<Noticia> findByDate(List<Noticia> noticias, Integer de, Integer ate,
                                           EnumTipoOrdenacao tipoOrdenacao, boolean isAsc) {
        List<DateTime> dataNoticias = new ArrayList<>();
        noticias.removeIf(e -> !(e.getDataNoticia().getYear() >= de && e.getDataNoticia().getYear() <= ate));
        for (Noticia noticia : noticias) {
            dataNoticias.add(noticia.getDataNoticia());
        }
        dataNoticias = ordernacaoByDate(dataNoticias, isAsc);
        List<Long> dataNoticiasMiliSegundos = new ArrayList<>();
        List<DateTime> dataNoticiasOrdenado = new ArrayList<>();
        for (DateTime noticia : dataNoticias) {
            dataNoticiasMiliSegundos.add(noticia.getMillis());
        }
        for (DateTime noticia : dataNoticias) {
            dataNoticiasOrdenado.add(new DateTime(buscaBinariaRecursiva(dataNoticiasMiliSegundos, noticia.getMillis(), isAsc)));
        }
        List<Noticia> noticiasFound = new ArrayList<>();
        for (DateTime dataNoticia : dataNoticiasOrdenado) {
            for (Noticia noticia : noticias) {
                if (noticia.getDataNoticia().equals(dataNoticia)) {
                    noticiasFound.add(noticia);
                }
            }
        }
        return filterAndFind(noticiasFound, tipoOrdenacao, isAsc);
    }


    private static Long buscaBinariaRecursiva(List<Long> numeros, Long elemento, boolean isAsc) {
        return buscaRecursiva(numeros, elemento, 0, numeros.size() - 1, isAsc);
    }

    private static Long buscaRecursiva(List<Long> numeros, Long elemento, int menor, int maior, boolean isAsc) {
        if (maior >= menor) {
            int index = menor + (maior - menor) / 2;
            if (numeros.get(index).compareTo(elemento) == 0) {
                return elemento;
            }
            if (isAsc) {
                if (numeros.get(index).compareTo(elemento) > 0) {
                    return buscaRecursiva(numeros, elemento, menor, index - 1, isAsc);
                }
                return buscaRecursiva(numeros, elemento, index + 1, maior, isAsc);
            } else {
                if (numeros.get(index).compareTo(elemento) > 0) {
                    return buscaRecursiva(numeros, elemento, index + 1, maior, isAsc);
                }
                return buscaRecursiva(numeros, elemento, menor, index - 1, isAsc);
            }
        }
        return -1L;
    }

    public static List<Noticia> filterAndFind(List<Noticia> noticias, String yourText, EnumTipoOrdenacao
            tipoOrdenacao, boolean isAsc) {
        List<Noticia> noticiasFound = new ArrayList<>();
        defineTipoOrdenacao(noticias, tipoOrdenacao, isAsc, noticiasFound);
        noticiasFound.removeIf(e -> !(e.getTitulo().toLowerCase().contains(yourText.toLowerCase())));
        return noticiasFound;
    }

    public static List<Noticia> filterAndFind(List<Noticia> noticias, EnumTipoOrdenacao
            tipoOrdenacao, boolean isAsc) {
        List<Noticia> noticiasFound = new ArrayList<>();
        Set<Noticia> noticiaSets = new LinkedHashSet<>(noticias);
        List<Noticia> newNoticias = new ArrayList<>(noticiaSets);
        defineTipoOrdenacao(newNoticias, tipoOrdenacao, isAsc, noticiasFound);
        Set<Noticia> noticiasFoundSets = new LinkedHashSet<>(noticiasFound);
        return new ArrayList<>(noticiasFoundSets);
    }

    private static void defineTipoOrdenacao(List<Noticia> noticias, EnumTipoOrdenacao tipoOrdenacao, boolean isAsc, List<Noticia> noticiasFound) {
        switch (tipoOrdenacao) {
            case ID:
                ordenaId(noticias, isAsc, noticiasFound);
                break;
            case DATA:
                ordenaData(noticias, isAsc, noticiasFound);
                break;
            case TITULO:
                ordenaString(noticias, isAsc, noticiasFound);
                break;
        }
    }

    private static void ordenaString(List<Noticia> noticias, boolean isAsc, List<Noticia> noticiasFound) {
        List<String> titulosOrdenados = new ArrayList<>();
        for (Noticia noticia : noticias) {
            titulosOrdenados.add(noticia.getTitulo());
        }
        final List<String> titulos = ordenacaoByString(titulosOrdenados, isAsc);
        for (String titulo : titulos) {
            for (Noticia noticia : noticias) {
                if (noticia.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                    noticiasFound.add(noticia);
                }
            }
        }
    }

    private static void ordenaData(List<Noticia> noticias, boolean isAsc, List<Noticia> noticiasFound) {
        List<DateTime> datasNoticias = new ArrayList<>();
        for (Noticia noticia : noticias) {
            datasNoticias.add(noticia.getDataNoticia());
        }
        List<DateTime> dataNoticiasOrdenado = ordernacaoByDate(datasNoticias, isAsc);
        for (DateTime dataNoticia : dataNoticiasOrdenado) {
            for (Noticia noticia : noticias) {
                if (noticia.getDataNoticia().equals(dataNoticia)) {
                    noticiasFound.add(noticia);
                }
            }
        }
    }

    private static void ordenaId(List<Noticia> noticias, boolean isAsc, List<Noticia> noticiasFound) {
        List<Long> ids = new ArrayList<>();
        for (Noticia noticia : noticias) {
            ids.add(Long.valueOf(noticia.getId()));
        }
        List<Long> idsOrdenados = ordernacaoById(ids, isAsc);
        for (Long id : idsOrdenados) {
            for (Noticia noticia : noticias) {
                if (noticia.getId().compareTo(toIntExact(id)) == 0) {
                    noticiasFound.add(noticia);
                }
            }
        }
    }

}
