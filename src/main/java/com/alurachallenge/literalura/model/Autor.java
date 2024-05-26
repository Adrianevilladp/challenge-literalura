package com.alurachallenge.literalura.model;

import com.alurachallenge.literalura.procesarJson.DatosAutor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "autores")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = true)
    private Year birthDate;

    @JsonIgnore
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Libro> libros = new HashSet<>();

    @Column(nullable = true)
    private Year deathDate;

    public Autor(DatosAutor autorEncontrado) {
        this.nombre = autorEncontrado.nombre();
        try {
            this.birthDate = Year.parse(autorEncontrado.birthYear());
        } catch (NumberFormatException | NullPointerException exception) {
            this.birthDate = null;
        }
        try {
            this.deathDate = Year.parse(autorEncontrado.deathYear());
        } catch (NumberFormatException | NullPointerException exception) {
            this.deathDate = null;
        }
    }

    public void aniadirLibro(Libro libro) {
        this.libros.add(libro);
        libro.setAutor(this);
    }

    public void eliminarLibro(Libro libro) {
        this.libros.remove(libro);
        libro.setAutor(null);
    }

    @Override
    public String toString() {
        return "\n Autor: \n Nombre: " + nombre +
                " - Fecha de nacimiento " + birthDate +
                " - Fecha de muerte " + deathDate;
    }
}
