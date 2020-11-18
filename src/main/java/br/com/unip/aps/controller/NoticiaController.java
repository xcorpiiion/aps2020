package br.com.unip.aps.controller;

import br.com.unip.aps.dto.NoticiaDTO;
import br.com.unip.aps.model.Noticia;
import br.com.unip.aps.service.NoticiaServiceImpl;
import br.com.unip.aps.service.utils.ordenacao.enums.EnumTipoOrdenacao;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.unip.aps.controller.utils.DateTimeFormatterUtils.dateTimeFormatter;
import static br.com.unip.aps.service.utils.ordenacao.OrdenacaoSelectionSortUtils.ordernacaoByDate;
import static java.awt.Font.PLAIN;
import static javax.swing.SwingConstants.CENTER;
import static javax.swing.border.EtchedBorder.LOWERED;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@RestController
public class NoticiaController {

    private List<Integer> numberPages;

    private List<Noticia> noticias;

    private List<DateTime> dataNoticias;

    @Autowired
    private NoticiaServiceImpl noticiaService;

    public void init() {
        noticias = this.noticiaService.findAll();
        dataNoticias = new ArrayList<>();
        for (Noticia noticia : noticias) {
            dataNoticias.add(noticia.getDataNoticia());
        }
        dataNoticias = dataNoticias.stream().distinct().collect(Collectors.toList());
        defineNumeroPagina();
    }

    public void criaNoticia(List<JPanel> panelLinhas, JPanel panelBody, int numberPage) {
        int i = 1;
        int aux = 0;
        if (numberPage > 1) {
            aux = 6 * numberPage - 6;
        }
        for (int contador = aux; contador < numberPage * 6; contador++) {
            if (contador > noticias.size() - 1) {
                break;
            }
            NoticiaDTO noticiaDTO = new NoticiaDTO();
            criaNovoPanelLinha(panelLinhas, panelBody, 35 * i);
            criaTituloMensagem(noticias.get(contador).getTitulo(), noticiaDTO, panelLinhas);
            criaDataNoticia(noticiaDTO, noticias.get(contador).getDataNoticia(), panelLinhas);
            noticiaDTO.setConteudo(noticias.get(contador).getConteudo());
            noticiaDTO.setBtnVerNoticia(criaButtonVerNoticia(panelLinhas));
            noticiaDTO.handleVerConteudo(panelBody);
            i++;
        }
    }

    public void defineNumeroPagina() {
        int i = noticias.size();
        int contador = 1;
        numberPages = new ArrayList<>();
        numberPages.add(contador);
        while (i - 6 > 0) {
            contador++;
            numberPages.add(contador);
            i -= 6;
        }
    }

    private void criaTituloMensagem(String tituloMensagem, NoticiaDTO noticiaDTO, List<JPanel> panelLinhas) {
        final int index = panelLinhas.size() - 1;
        noticiaDTO.setLblTituloNoticia(new JLabel(tituloMensagem));
        noticiaDTO.getLblTituloNoticia().setHorizontalAlignment(CENTER);
        noticiaDTO.getLblTituloNoticia().setFont(new Font("Arial", PLAIN, 18));
        noticiaDTO.getLblTituloNoticia().setBounds(0, 0, 350, 28);
        panelLinhas.get(index).add(noticiaDTO.getLblTituloNoticia());
    }

    public void criaDataNoticia(NoticiaDTO noticiaDTO, DateTime dataNoticia, List<JPanel> panelLinhas) {
        final int index = panelLinhas.size() - 1;
        noticiaDTO.setLblData(new JLabel(dateTimeFormatter(dataNoticia)));
        noticiaDTO.getLblData().setHorizontalAlignment(CENTER);
        noticiaDTO.getLblData().setFont(new Font("Arial", PLAIN, 18));
        noticiaDTO.getLblData().setBounds(370, 0, 102, 28);
        panelLinhas.get(index).add(noticiaDTO.getLblData());
    }

    public JButton criaButtonVerNoticia(List<JPanel> panelLinhas) {
        final int index = panelLinhas.size() - 1;
        JButton btnVerNoticia = new JButton("Ver notícia");
        btnVerNoticia.setFont(new Font("Tahoma", PLAIN, 18));
        btnVerNoticia.setBounds(585, 0, 132, 28);
        panelLinhas.get(index).add(btnVerNoticia);
        return btnVerNoticia;
    }

    public JPanel criaNovoPanelLinha(List<JPanel> panelLinhas, JPanel panelBody, int y) {
        panelLinhas.add(new JPanel());
        final int index = panelLinhas.size() - 1;
        panelLinhas.get(index).setBorder(new EtchedBorder(LOWERED, null, null));
        panelLinhas.get(index).setBounds(10, y, 717, 28);
        panelBody.add(panelLinhas.get(index));
        panelLinhas.get(index).setLayout(null);
        return panelLinhas.get(index);
    }

    public void findByTitulo(String yourString, EnumTipoOrdenacao tipoOrdenacao, boolean isAsc) {
        this.noticias = new ArrayList<>();
        if(!isEmpty(yourString)) {
            this.noticias.addAll(this.noticiaService.findByTitulo(yourString, tipoOrdenacao, isAsc));
        } else {
            this.noticias.addAll(this.noticiaService.findAll());
        }
    }

    public void findByDate(Integer de, Integer ate, EnumTipoOrdenacao tipoOrdenacao, boolean isAsc) {
        this.noticias = new ArrayList<>();
        final List<Noticia> byDate = this.noticiaService.findByDate(de, ate, tipoOrdenacao, isAsc);
        byDate.removeIf(e -> e.getDataNoticia().getYear() < de);
        byDate.removeIf(e -> e.getDataNoticia().getYear() > ate);
        this.noticias.addAll(byDate);
    }

    public List<DateTime> getDataNoticias() {
        return ordernacaoByDate(dataNoticias, true);
    }

    public List<Integer> getNumberPages() {
        return numberPages;
    }
}
