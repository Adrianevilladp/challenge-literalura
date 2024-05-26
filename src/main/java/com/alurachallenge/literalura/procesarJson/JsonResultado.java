package com.alurachallenge.literalura.procesarJson;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record JsonResultado(

        @JsonAlias("count")
        Integer cantidad,

        @JsonAlias("results")List<Result> result
        ) {
}
