package com.alurachallenge.literalura.procesarJson;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Result(

        @JsonAlias("title")
        String titulo,

        @JsonAlias("authors")
        List<DatosAutor> autors,

        @JsonAlias("languages")
        List<String> idioma,

        @JsonAlias("download_count")
        Integer cantidadDescargas

) {
}
