package br.com.unip.aps.service.utils.ordenacao;

import java.util.List;

public final class OrdenacaoBubbleSortUtils {

	private OrdenacaoBubbleSortUtils() {

	}

	public static List<String> ordenacaoByString(List<String> yourStringList, boolean isAcrecente) {
		if (isAcrecente) {
			ordecanaoAscString(yourStringList);
		} else {
			ordecanaoDesString(yourStringList);
		}
		return yourStringList;
	}
	
	private static void ordecanaoAscString(List<String> yourList) {
		String aux;
		boolean isFinished;
		for (int i = 0; i < yourList.size(); i++) {
			isFinished = true;
			for (int j = 0; j < yourList.size() - 1; j++) {
				if (yourList.get(j).compareTo(yourList.get(j + 1)) > 0) {
					aux = yourList.get(j);
					yourList.set(j, yourList.get(j + 1));
					yourList.set(j + 1, aux);
					isFinished = false;
				}
			}
			if(isFinished) {
				break;
			}
		}
	}
	
	private static void ordecanaoDesString(List<String> yourList) {
		String aux;
		boolean isFinished;
		for (int i = 0; i < yourList.size(); i++) {
			isFinished = true;
			for (int j = 0; j < yourList.size() - 1; j++) {
				if (yourList.get(j).compareTo(yourList.get(j + 1)) < 0) {
					aux = yourList.get(j);
					yourList.set(j, yourList.get(j + 1));
					yourList.set(j + 1, aux);
					isFinished = false;
				}
			}
			if(isFinished) {
				break;
			}
		}
	}

}
