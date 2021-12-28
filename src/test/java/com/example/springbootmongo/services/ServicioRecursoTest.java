package com.example.springbootmongo.services;

import com.example.springbootmongo.DTOs.RecursoDTO;
import com.example.springbootmongo.collections.Recurso;
import com.example.springbootmongo.repositories.RepositorioRecurso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class ServicioRecursoTest {

    @MockBean
    private RepositorioRecurso repositorio;

    @Autowired
    private ServicioRecurso servicioRecurso;

    @Test
    @DisplayName("Find all test success")
    void obtenerTodos() {

        var recurso1 = new Recurso();
        recurso1.setId("aaaa");
        recurso1.setTitulo("Dracula");
        recurso1.setTipo("Libro");
        recurso1.setFecha(LocalDate.now());
        recurso1.setDisponible(true);
        recurso1.setAreaTematica("Novela");

        var recurso2 = new Recurso();
        recurso2.setId("bbbb");
        recurso2.setTitulo("Don Quijote");
        recurso2.setTipo("Libro");
        recurso2.setFecha(LocalDate.now());
        recurso2.setDisponible(false);
        recurso2.setAreaTematica("Novela");

        var listaRecursos = new ArrayList<Recurso>();
        listaRecursos.add(recurso1);
        listaRecursos.add(recurso2);

        Mockito.when(repositorio.findAll()).thenReturn(listaRecursos);

        var resultado = servicioRecurso.obtenerTodos();

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals(recurso1.getTitulo(), resultado.get(0).getTitulo());
        Assertions.assertEquals(recurso2.getTitulo(), resultado.get(1).getTitulo());
        Assertions.assertEquals(recurso1.getTipo(), resultado.get(0).getTipo());
        Assertions.assertEquals(recurso2.getTipo(), resultado.get(1).getTipo());
        Assertions.assertEquals(recurso1.isDisponible(), resultado.get(0).isDisponible());
        Assertions.assertEquals(recurso2.isDisponible(), resultado.get(1).isDisponible());

        Mockito.verify(repositorio).findAll();
    }

    @Test
    void agregarRecurso() {

        var recurso1 = new RecursoDTO();
        recurso1.setId("aaaa");
        recurso1.setTitulo("El principito");
        recurso1.setTipo("Libro");
        recurso1.setFecha(LocalDate.now());
        recurso1.setDisponible(true);
        recurso1.setAreaTematica("Cuentos");

        var recurso2 = new Recurso();
        recurso2.setId("bbbb");
        recurso2.setTitulo("Física Universitaria");
        recurso2.setTipo("Libro");
        recurso2.setFecha(LocalDate.now());
        recurso2.setDisponible(false);
        recurso2.setAreaTematica("Ciencias");

        Mockito.when(repositorio.save(any())).thenReturn(recurso2);

        var resultado = servicioRecurso.agregarRecurso(recurso1);

        Assertions.assertEquals(recurso2.getTitulo(), resultado.getTitulo());
        Assertions.assertEquals(recurso2.getTipo(), resultado.getTipo());
        Assertions.assertEquals(recurso2.getAreaTematica(), resultado.getAreaTematica());
        Assertions.assertEquals(recurso2.isDisponible(), resultado.isDisponible());
    }

    @Test
    @DisplayName("Regresar recurso test")
    void regresarRecurso() {

        var recurso1 = new RecursoDTO();
        recurso1.setId("aaaa");
        recurso1.setTitulo("El principito");
        recurso1.setTipo("Libro");
        recurso1.setFecha(LocalDate.now());
        recurso1.setDisponible(true);
        recurso1.setAreaTematica("Cuentos");

        var recurso2 = new Recurso();
        recurso2.setId("bbbb");
        recurso2.setTitulo("Física Universitaria");
        recurso2.setTipo("Libro");
        recurso2.setFecha(LocalDate.now());
        recurso2.setDisponible(false);
        recurso2.setAreaTematica("Ciencias");

        Mockito.when(repositorio.findById(any())).thenReturn(Optional.of(recurso2));

        var resultado = servicioRecurso.regresarRecurso(recurso1.getId());
        String mensajeEsperado = "El recurso " + recurso2.getTitulo() + " ha sido regresado";
        Assertions.assertEquals(mensajeEsperado, resultado.getRespuesta());
    }

    @Test
    void obtenerDisponibilidad(){

        var recurso1 = new Recurso();
        recurso1.setId("aaaa");
        recurso1.setTitulo("El principito");
        recurso1.setTipo("Libro");
        recurso1.setFecha(LocalDate.now());
        recurso1.setDisponible(true);
        recurso1.setAreaTematica("Cuentos");

        Mockito.when(repositorio.findById(any())).thenReturn(Optional.of(recurso1));

        var resultado = servicioRecurso.disponibilidad(recurso1.getId());

        Assertions.assertEquals(true, resultado.getDisponible());
        Assertions.assertEquals(null, resultado.getFecha());
    }
}