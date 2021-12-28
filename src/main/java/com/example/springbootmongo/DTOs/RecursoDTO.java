package com.example.springbootmongo.DTOs;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class RecursoDTO {

    @Id
    private String id;
    private String titulo;
    private String tipo;
    private LocalDate fecha;
    private boolean disponible;
    private String areaTematica;

    public RecursoDTO() {
    }

    public RecursoDTO(String id, String titulo, String tipo, LocalDate fecha, boolean disponible, String areaTematica) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.fecha = fecha;
        this.disponible = disponible;
        this.areaTematica = areaTematica;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getAreaTematica() {
        return areaTematica;
    }

    public void setAreaTematica(String areaTematica) {
        this.areaTematica = areaTematica;
    }
}
