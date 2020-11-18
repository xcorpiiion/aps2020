package br.com.unip.aps.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.swing.*;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

public class NoticiaDTO {

    private JLabel lblTituloNoticia;

    private String conteudo;

    private JLabel lblData;

    private JButton btnVerNoticia;

    public NoticiaDTO() {
    }

    public JLabel getLblTituloNoticia() {
        return lblTituloNoticia;
    }

    public void setLblTituloNoticia(JLabel lblTituloNoticia) {
        this.lblTituloNoticia = lblTituloNoticia;
    }

    public JButton getBtnVerNoticia() {
        return btnVerNoticia;
    }

    public void setBtnVerNoticia(JButton btnVerNoticia) {
        this.btnVerNoticia = btnVerNoticia;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public JLabel getLblData() {
        return lblData;
    }

    public void setLblData(JLabel lblData) {
        this.lblData = lblData;
    }

    public void handleVerConteudo(JPanel yourPanel) {
        this.btnVerNoticia.addActionListener(e -> {
            JOptionPane.showMessageDialog(yourPanel, this.getConteudo(), lblTituloNoticia.getText(), INFORMATION_MESSAGE);
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        NoticiaDTO that = (NoticiaDTO) o;

        return new EqualsBuilder()
                .append(lblTituloNoticia, that.lblTituloNoticia)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(lblTituloNoticia)
                .toHashCode();
    }
}
