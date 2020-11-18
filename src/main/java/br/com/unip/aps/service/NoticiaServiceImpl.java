package br.com.unip.aps.service;

import br.com.unip.aps.model.Noticia;
import br.com.unip.aps.repository.NoticiaRepository;
import br.com.unip.aps.service.utils.busca.BuscaBinariaUtils;
import br.com.unip.aps.service.utils.ordenacao.enums.EnumTipoOrdenacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.unip.aps.service.utils.busca.BuscaBinariaUtils.filterAndFind;
import static br.com.unip.aps.service.utils.ordenacao.enums.EnumTipoOrdenacao.*;

@Service
public class NoticiaServiceImpl implements NoticiaService {

    @Autowired
    private NoticiaRepository noticiaRepository;

    private List<Noticia> noticias;

    @Override
    public Noticia save(Noticia noticia) {
        return noticiaRepository.save(noticia);
    }

    @Override
    public void saveAll(List<Noticia> noticias) {
        noticiaRepository.saveAll(noticias);
    }

    @Override
    public List<Noticia> findAll() {
        return noticiaRepository.findAll();
    }

    @Override
    public List<Noticia> findByDate(Integer de, Integer ate, EnumTipoOrdenacao tipoOrdenacao, boolean isAsc) {
        noticias = this.noticiaRepository.findAll();
        return BuscaBinariaUtils.findByDate(noticias, de, ate, tipoOrdenacao, isAsc);
    }

    @Override
    public Noticia findById(Long id) {
        noticias = this.noticiaRepository.findAll();
        return BuscaBinariaUtils.findById(noticias, id);
    }

    @Override
    public List<Noticia> findByTitulo(String titulo, EnumTipoOrdenacao tipoOrdenacao, boolean isAsc) {
        noticias = this.noticiaRepository.findAll();
        if (tipoOrdenacao == DATA) {
            return filterAndFind(noticias, titulo, DATA, isAsc);
        }
        if (tipoOrdenacao == TITULO) {
            return filterAndFind(noticias, titulo, TITULO, isAsc);
        }
        return filterAndFind(noticias, titulo, ID, isAsc);
    }
}
