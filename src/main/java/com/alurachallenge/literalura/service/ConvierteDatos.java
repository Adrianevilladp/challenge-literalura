package com.alurachallenge.literalura.service;

public interface ConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> tClass);
}
