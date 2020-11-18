package br.com.unip.aps.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotEmpty
    @NotNull
    @NotBlank
    public String titulo;

    @NotEmpty
    @NotNull
    @NotBlank
    public String conteudo;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime dataNoticia;

    public Noticia() {
    }

    public Noticia(@NotEmpty @NotNull @NotBlank String titulo, @NotEmpty @NotNull @NotBlank String conteudo, @NotNull DateTime dataNoticia) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.dataNoticia = dataNoticia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public DateTime getDataNoticia() {
        return dataNoticia;
    }

    public void setDataNoticia(DateTime dataNoticia) {
        this.dataNoticia = dataNoticia;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("titulo", titulo)
                .append("conteudo", conteudo)
                .append("dataNoticia", dataNoticia)
                .toString();
    }
}
