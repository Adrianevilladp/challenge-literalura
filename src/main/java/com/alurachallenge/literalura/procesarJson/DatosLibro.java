package com.alurachallenge.literalura.procesarJson;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title")
        String titulo,

        @JsonAlias("download_count")
        Integer numeroDescargas,

        @JsonAlias("languages")
        String idioma
) {
}
