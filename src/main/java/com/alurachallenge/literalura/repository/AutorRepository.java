package com.alurachallenge.literalura.repository;

import com.alurachallenge.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    //use derived queries
    @Query(value = "SELECT * FROM autores WHERE autores.death_date >= ?1 ", nativeQuery = true)
    List<Autor> findByYear(@Param("yearParaBuscar") Integer yearParaBuscar);

    @Query("SELECT a FROM Autor a WHERE LOWER(a.nombre) LIKE lower(:nombre)")
    Optional<Autor> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
