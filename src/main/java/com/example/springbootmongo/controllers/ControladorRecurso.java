package com.example.springbootmongo.controllers;

import com.example.springbootmongo.DTOs.RecursoDTO;
import com.example.springbootmongo.DTOs.RespuestaDTO;
import com.example.springbootmongo.services.ServicioRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biblioteca")
public class ControladorRecurso {

    @Autowired
    ServicioRecurso servicioRecurso;

    @GetMapping()
    public ResponseEntity<List<RecursoDTO>> obtenerTodos() {
        return new ResponseEntity<>(servicioRecurso.obtenerTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecursoDTO> encontrarPorId(@PathVariable("id") String id) {
        return new ResponseEntity<>(servicioRecurso.obtenerPorId(id), HttpStatus.OK);
    }

    @PostMapping("/agregarRecurso")
    public ResponseEntity<RecursoDTO> agregarRecurso(@RequestBody RecursoDTO recursoDTO){
        return new ResponseEntity<>(servicioRecurso.agregarRecurso(recursoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/modificarRecurso")
    public ResponseEntity<RecursoDTO> modificarRecurso(@RequestBody RecursoDTO recursoDTO){
        if (recursoDTO.getId() != null){
            return new ResponseEntity<>(servicioRecurso.modificarRecurso(recursoDTO), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RecursoDTO> eliminarRecurso(@PathVariable String id){
        try{
            servicioRecurso.eliminarRecurso(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception error){
            System.out.println(error.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Bussines
    @GetMapping("/recursoDisponible/{titulo}")
    public ResponseEntity<RecursoDTO> disponibilidadRecurso(@PathVariable String titulo){
        return new ResponseEntity(servicioRecurso.disponibilidad(titulo), HttpStatus.OK);
    }

    @PutMapping("/prestarRecurso/{titulo}")
    public ResponseEntity<RecursoDTO> prestarUnRecurso(@PathVariable String titulo){
        return new ResponseEntity(servicioRecurso.prestarRecurso(titulo), HttpStatus.OK);
    }

    @GetMapping("/regresar/{id}")
    public ResponseEntity<RespuestaDTO> regresarRecurso(@PathVariable("id") String id){
        return new ResponseEntity<>(servicioRecurso.regresarRecurso(id), HttpStatus.OK);
    }

    @GetMapping("/recomendacion/area/{id}")
    public ResponseEntity<List<RecursoDTO>> recomendacionPorArea(@PathVariable("id") String areaTematica){
        return new ResponseEntity<>(servicioRecurso.recursosRecomendadosPorAreaTematica(areaTematica), HttpStatus.OK);
    }

    @GetMapping("/recomendacion/tipo/{id}")
    public ResponseEntity<List<RecursoDTO>> recomendacionPorTipo(@PathVariable("id") String tipo){
        return new ResponseEntity<>(servicioRecurso.recursosRecomendadosPorTipo(tipo), HttpStatus.OK);
    }

    @GetMapping("/recomendacion/{clasificacion}/{area}")
    public ResponseEntity<RecursoDTO> recursosRecomendados(@PathVariable String tipo, @PathVariable String areaTematica){
        return new ResponseEntity(servicioRecurso.recursosRecomendadosPorAreaTematicaYTipo(areaTematica,tipo), HttpStatus.OK);
    }
}
