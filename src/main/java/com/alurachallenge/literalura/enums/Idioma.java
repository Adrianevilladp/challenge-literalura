package com.alurachallenge.literalura.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Idioma {
    INGLES("en", "ingles"),
    ESPANIOL("es", "espanol"),
    RUSO("ru", "ruso"),
    FRANCES("fr", "frances"),
    PORTUGUES("pt", "portugues"),
    ARABE("ar", "arabe"),
    CHINO("zh", "chino"),
    JAPONES("ja", "japones");

    private final String codigo;
    private final String nombre;
    private static final String EXCEPTIONTEXT = " no fue encontrado";

    public static Idioma fromNombre(String nombreIdioma){
        for (Idioma idioma : Idioma.values()) {
            if (idioma.nombre.equalsIgnoreCase(nombreIdioma)) {
                return idioma;
            }
        }

        throw new IllegalArgumentException(String.format("%s%s", nombreIdioma, EXCEPTIONTEXT));
    }

    public static Idioma fromCodigo(String codigo){
        for (Idioma idioma : Idioma.values()) {
            if (idioma.codigo.toLowerCase().equals(codigo)) {
                return idioma;
            }
        }

        throw new IllegalArgumentException(String.format("%s%s", codigo, EXCEPTIONTEXT));
    }

}
