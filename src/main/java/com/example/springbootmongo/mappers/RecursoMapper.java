package com.example.springbootmongo.mappers;

import com.example.springbootmongo.DTOs.RecursoDTO;
import com.example.springbootmongo.collections.Recurso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecursoMapper {

    public Recurso fromDTO(RecursoDTO dto) {
        Recurso recurso = new Recurso();
        recurso.setId(dto.getId());
        recurso.setTitulo(dto.getTitulo());
        recurso.setTipo(dto.getTipo());
        recurso.setDisponible(dto.isDisponible());
        recurso.setFecha(dto.getFecha());
        recurso.setAreaTematica(dto.getAreaTematica());

        return recurso;
    }

    public RecursoDTO fromCollection(Recurso collection){
        RecursoDTO recursoDTO = new RecursoDTO();
        recursoDTO.setId(collection.getId());
        recursoDTO.setTitulo(collection.getTitulo());
        recursoDTO.setTipo(collection.getTipo());
        recursoDTO.setDisponible(collection.isDisponible());
        recursoDTO.setFecha(collection.getFecha());
        recursoDTO.setAreaTematica(collection.getAreaTematica());

        return recursoDTO;
    }

    public List<RecursoDTO> fromCollectionList(List<Recurso> collection){

        if (collection == null){
            return null;
        }
        List<RecursoDTO> list = new ArrayList<>(collection.size());
        Iterator listTracks = collection.iterator();

        while (listTracks.hasNext()){
            Recurso recurso = (Recurso) listTracks.next();
            list.add(fromCollection(recurso));
        }

        return list;
    }

}
