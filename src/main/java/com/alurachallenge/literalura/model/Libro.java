package com.alurachallenge.literalura.model;

import com.alurachallenge.literalura.enums.Idioma;
import com.alurachallenge.literalura.procesarJson.DatosLibro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "libros")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Idioma idioma;

    @ManyToOne()
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Autor autor;

    @Column(name = "numero_descargas", nullable = false)
    private Integer numeroDescargas;

    public Libro(DatosLibro libroEncontrado) {
        this.titulo = libroEncontrado.titulo();
        this.idioma = Idioma.fromCodigo(libroEncontrado.idioma());
        this.numeroDescargas = libroEncontrado.numeroDescargas();
    }

    @Override
    public String toString() {
        return " \n Libro: \n Titulo: " + titulo +
                " - Idioma " + idioma +
                " - Autor " + autor.toString() +
                " - Numero de descargas " + numeroDescargas + "\n";
    }
}
