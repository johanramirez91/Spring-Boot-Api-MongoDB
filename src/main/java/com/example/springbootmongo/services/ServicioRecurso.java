package com.example.springbootmongo.services;

import com.example.springbootmongo.DTOs.RecursoDTO;
import com.example.springbootmongo.collections.Recurso;
import com.example.springbootmongo.mappers.RecursoMapper;
import com.example.springbootmongo.repositories.RepositorioRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        return recursoMapper.fromCollection(repositorioRecurso.save(recurso));
    }

    public void borrarRecurso(String id) {
        repositorioRecurso.deleteById(id);
    }

    // Service bussines

    public String disponibilidadRecurso(String titulo){
        String mensaje;
        List<RecursoDTO> listaRecursos = obtenerTodos();
        RecursoDTO recursoDTO = listaRecursos
                .stream()
                .filter(recursoDTO1 -> recursoDTO1.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .get();
        if (recursoDTO.isDisponible()){
            LocalDate fecha = recursoDTO.getFecha();
            mensaje = "Recurso no disponible, la fecha de prestamo fue: " + fecha;
            return mensaje;
        }
        mensaje = "Recurso disponible";
        return mensaje;
    }
}
