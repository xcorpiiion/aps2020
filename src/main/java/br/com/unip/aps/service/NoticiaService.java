package br.com.unip.aps.service;

import br.com.unip.aps.model.Noticia;
import br.com.unip.aps.service.utils.ordenacao.enums.EnumTipoOrdenacao;

import java.util.List;

public interface NoticiaService {

    Noticia save(Noticia noticia);

    void saveAll(List<Noticia> noticias);

    List<Noticia> findAll();

    List<Noticia> findByDate(Integer de, Integer ate, EnumTipoOrdenacao tipoOrdenacao, boolean isAsc);

    Noticia findById(Long id);

    List<Noticia> findByTitulo(String titulo, EnumTipoOrdenacao tipoOrdenacao, boolean isAsc);
}
