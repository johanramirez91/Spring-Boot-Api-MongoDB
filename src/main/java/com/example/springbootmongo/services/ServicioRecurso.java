package com.example.springbootmongo.services;

import com.example.springbootmongo.DTOs.RecursoDTO;
import com.example.springbootmongo.DTOs.RespuestaDTO;
import com.example.springbootmongo.collections.Recurso;
import com.example.springbootmongo.mappers.RecursoMapper;
import com.example.springbootmongo.repositories.RepositorioRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioRecurso {

    @Autowired
    RepositorioRecurso repositorioRecurso;
    RecursoMapper recursoMapper = new RecursoMapper();

    // Service CRUD
    public List<RecursoDTO> obtenerTodos() {
        List<Recurso> recursos = repositorioRecurso.findAll();
        return recursoMapper.fromCollectionList(recursos);
    }

    public RecursoDTO obtenerPorId(String id) {
        Recurso recurso = repositorioRecurso.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        return recursoMapper.fromCollection(recurso);
    }

    public RecursoDTO agregarRecurso(RecursoDTO recursoDTO) {
        Recurso recurso = recursoMapper.fromDTO(recursoDTO);
        return recursoMapper.fromCollection(repositorioRecurso.save(recurso));
    }

    public RecursoDTO modificarRecurso(RecursoDTO recursoDTO) {
        Recurso recurso = recursoMapper.fromDTO(recursoDTO);
        repositorioRecurso.findById(recurso.getId())
                .orElseThrow(() -> new IllegalArgumentException("Recurso no encontrado"));
        return recursoMapper.fromCollection(repositorioRecurso.save(recurso));
    }

    public void eliminarRecurso(String id) {
        repositorioRecurso.deleteById(id);
    }

    //Service bussines
    public RespuestaDTO disponibilidad(String id) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        Recurso recurso = repositorioRecurso.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el recurso"));
        if (recurso.isDisponible()) {
            respuestaDTO.setRespuesta("El recurso " + recursoMapper.fromCollection(recurso).getTitulo() + " está disponible");
            respuestaDTO.setDisponible(true);
            respuestaDTO.setFecha(null);
            return respuestaDTO;
        }
        respuestaDTO.setRespuesta("El recurso: " +
                recursoMapper.fromCollection(recurso).getTitulo() +
                " NO está disponible, fue prestado el: " +
                recursoMapper.fromCollection(recurso).getFecha().toString());
        respuestaDTO.setDisponible(false);
        respuestaDTO.setFecha(recursoMapper.fromCollection(recurso).getFecha());
        return respuestaDTO;
    }

    public RespuestaDTO prestarRecurso(String id) {
        RespuestaDTO respuesta = new RespuestaDTO();
        LocalDate fecha = LocalDate.now();
        Recurso recurso = repositorioRecurso.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el recurso"));
        if (recurso.isDisponible()) {
            recurso.setDisponible(false);
            recurso.setFecha(fecha);
            repositorioRecurso.save(recurso);
            respuesta.setRespuesta("El recurso: " + recursoMapper.fromCollection(recurso).getTitulo() + " ha sido prestado");
            respuesta.setDisponible(false);
            respuesta.setFecha(recursoMapper.fromCollection(recurso).getFecha());
            return respuesta;
        }
        respuesta.setRespuesta("El recurso: " + recursoMapper.fromCollection(recurso).getTitulo() + " NO está disponible");
        respuesta.setDisponible(false);
        respuesta.setFecha(recursoMapper.fromCollection(recurso).getFecha());
        return respuesta;
    }

    public RespuestaDTO regresarRecurso(String id) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        LocalDate fecha = LocalDate.now();
        Recurso recurso = repositorioRecurso.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el recurso"));
        if (!recurso.isDisponible()) {
            recurso.setDisponible(true);
            recurso.setFecha(fecha);
            repositorioRecurso.save(recurso);
            respuestaDTO.setRespuesta("El recurso " + recursoMapper.fromCollection(recurso).getTitulo() + " ha sido regresado");
            respuestaDTO.setDisponible(true);
            respuestaDTO.setFecha(recursoMapper.fromCollection(recurso).getFecha());
            return respuestaDTO;
        }

        respuestaDTO.setRespuesta("El recurso: " + recursoMapper.fromCollection(recurso).getTitulo() + " está disponible");
        respuestaDTO.setDisponible(false);
        respuestaDTO.setFecha(recursoMapper.fromCollection(recurso).getFecha());
        return respuestaDTO;
    }

    public List<RecursoDTO> recursosRecomendadosPorTipo(String tipo) {
        List<RecursoDTO> recursosList = obtenerTodos();
        if (!tipo.equalsIgnoreCase("none")) {
            return recursosList.stream()
                    .filter(recursoDTO -> recursoDTO.getTipo().equalsIgnoreCase(tipo))
                    .collect(Collectors.toList());
        }
        return recursosList;
    }

    public List<RecursoDTO> recursosRecomendadosPorAreaTematica(String areaTematica) {
        List<RecursoDTO> recursosList = obtenerTodos();
        if (!areaTematica.equalsIgnoreCase("none")) {
            return recursosList.stream()
                    .filter(recursoDTO -> recursoDTO.getAreaTematica().equalsIgnoreCase(areaTematica))
                    .collect(Collectors.toList());
        }
        return recursosList;
    }

    public List<RecursoDTO> recursosRecomendadosPorAreaTematicaYTipo(String areaTematica, String tipo) {
        List<RecursoDTO> recursosList = obtenerTodos();
        if (isClasificado(areaTematica, tipo)) {
            return recursosList.stream()
                    .filter(recursoDTO -> recursoDTO.getAreaTematica().equalsIgnoreCase(areaTematica))
                    .filter(recursoDTO -> recursoDTO.getTipo().equalsIgnoreCase(tipo))
                    .collect(Collectors.toList());
        }
        return recursosList;
    }

    private boolean isClasificado(String areaTematica, String tipo) {
        return !areaTematica.equalsIgnoreCase("none") && !tipo.equalsIgnoreCase("none");
    }
}
