package com.example.springbootmongo.DTOs;

import java.time.LocalDate;

public class RespuestaDTO {

    private String respuesta;
    private Boolean disponible;
    private LocalDate fecha;

    public RespuestaDTO() {
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
