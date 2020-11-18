package br.com.unip.aps.service.utils.ordenacao;

import java.util.List;

public final class OrdenacaoQuickSortUtils {

    private OrdenacaoQuickSortUtils() {

    }

    public static List<Long> ordernacaoById(List<Long> yourIdLists, boolean isAcrecente) {
        int esq = 0;
        int dir = yourIdLists.size() - 1;
        if(!isAcrecente) {
            esq = yourIdLists.size() - 1;
            dir = 0;
        }
        quickSort(yourIdLists, esq, dir, isAcrecente);
        return yourIdLists;
    }

    private static void quickSort(List<Long> yourIdLists, int esq, int dir, boolean isAcrecente) {
        if(isAcrecente) {
            if (esq < dir) {
                int j = separar(yourIdLists, esq, dir, isAcrecente);
                quickSort(yourIdLists, esq, j - 1, isAcrecente);
                quickSort(yourIdLists, j + 1, dir, isAcrecente);
            }
        } else {
            if (esq > dir) {
                int j = separar(yourIdLists, esq, dir, isAcrecente);
                quickSort(yourIdLists, esq, j + 1, isAcrecente);
                quickSort(yourIdLists, j - 1, dir, isAcrecente);
            }

        }
    }

    private static int separar(List<Long> yourIdLists, int esq, int dir, boolean isAcrecente) {
        if(isAcrecente) {
            int i = esq + 1;
            int j = dir;
            Long pivo = yourIdLists.get(esq);
            while (i <= j) {
                if (yourIdLists.get(i) <= pivo) {
                    i++;
                } else if (yourIdLists.get(j) > pivo) {
                    j--;
                } else if (i <= j) {
                    trocar(yourIdLists, i, j);
                    i++;
                    j--;
                }
            }
            trocar(yourIdLists, esq, j);
            return j;
        } else {
            int i = esq - 1;
            int j = dir;
            Long pivo = yourIdLists.get(esq);
            while (i >= j) {
                if (yourIdLists.get(i) <= pivo) {
                    i--;
                } else if (yourIdLists.get(j) > pivo) {
                    j++;
                } else if (i >= j) {
                    trocar(yourIdLists, i, j);
                    i--;
                    j++;
                }
            }
            trocar(yourIdLists, esq, j);
            return j;

        }
    }

    private static void trocar(List<Long> v, int i, int j) {
        Long aux = v.get(i);
        v.set(i, v.get(j));
        v.set(j, aux);
    }

}
