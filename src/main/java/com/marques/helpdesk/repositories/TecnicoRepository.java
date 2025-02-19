package com.marques.helpdesk.repositories;

import com.marques.helpdesk.domain.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

    void deleteById(Integer id);
}
