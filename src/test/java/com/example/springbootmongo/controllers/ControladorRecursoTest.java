package com.example.springbootmongo.controllers;

import com.example.springbootmongo.DTOs.RecursoDTO;
import com.example.springbootmongo.DTOs.RespuestaDTO;
import com.example.springbootmongo.services.ServicioRecurso;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ControladorRecursoTest {

    @MockBean
    private ServicioRecurso servicioRecurso;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /recursos")
    void obtenerRecursos() throws Exception {

        var listaRecursos = new ArrayList<RecursoDTO>();
        listaRecursos.add(new RecursoDTO("111", "Dracula", "Libro", LocalDate.now(), true, "Novela"));
        listaRecursos.add(new RecursoDTO("222", "Don quijote", "Libro", LocalDate.now(), true, "Novela"));
        listaRecursos.add(new RecursoDTO("333", "Hamlet", "Libro", LocalDate.now(), false, "Novela"));
        listaRecursos.add(new RecursoDTO("444", "Times", "Revista", LocalDate.now(), false, "Entreteminiento"));

        Mockito.when(servicioRecurso.obtenerTodos()).thenReturn(listaRecursos);

        mockMvc.perform(get("/biblioteca"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is("111")))
                .andExpect(jsonPath("$[0].titulo", is("Dracula")))
                .andExpect(jsonPath("$[0].tipo", is("Libro")))
                .andExpect(jsonPath("$[0].disponible", is(true)))
                .andExpect(jsonPath("$[0].areaTematica", is("Novela")))
                .andExpect(jsonPath("$[2].id", is("333")))
                .andExpect(jsonPath("$[2].titulo", is("Hamlet")))
                .andExpect(jsonPath("$[2].tipo", is("Libro")))
                .andExpect(jsonPath("$[2].disponible", is(false)))
                .andExpect(jsonPath("$[2].areaTematica", is("Novela")));
    }

    @Test
    @DisplayName("POST /agregarRecurso")
    void agregarRecursos() throws Exception {

        var recursoPost = new RecursoDTO();
        recursoPost.setTitulo("Bioquimica");
        recursoPost.setTipo("Libro");
        recursoPost.setDisponible(true);
        recursoPost.setAreaTematica("Ciencias");

        var recursoReturn = new RecursoDTO();
        recursoReturn.setId("555");
        recursoReturn.setTitulo("Bioquimica");
        recursoReturn.setTipo("Libro");
        recursoReturn.setDisponible(true);
        recursoReturn.setAreaTematica("Ciencias");

        Mockito.when(servicioRecurso.agregarRecurso(Mockito.any())).thenReturn(recursoReturn);

        mockMvc.perform(post("/biblioteca/agregarRecurso")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(recursoPost)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.titulo", is("Bioquimica")))
                .andExpect(jsonPath("$.areaTematica", is("Ciencias")))
                .andExpect(jsonPath("$.disponible", is(true)))
                .andExpect(jsonPath("$.tipo", is("Libro")));
    }

    @Test
    @DisplayName("Disponibilidad recurso test")
    void disponibilidadRecurso() throws Exception{
        var respuesta = new RespuestaDTO();
        var recursoPost = new RecursoDTO();
        recursoPost.setTitulo("Bioquimica");
        recursoPost.setTipo("Libro");
        recursoPost.setDisponible(true);
        recursoPost.setAreaTematica("Ciencias");
        recursoPost.setFecha(null);
        respuesta.setRespuesta("Recurso disponible");

        Mockito.when(servicioRecurso.disponibilidad(Mockito.any())).thenReturn(respuesta);

        mockMvc.perform(get("/biblioteca/recursoDisponible/Bioquimica")
                .contentType(MediaType.valueOf("test/plain;charset=UTF-8"))
                .content(asJsonString(recursoPost)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"respuesta\":\"Recurso disponible\",\"disponible\":null,\"fecha\":null}"));
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}