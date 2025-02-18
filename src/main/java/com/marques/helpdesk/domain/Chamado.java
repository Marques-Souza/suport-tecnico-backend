package com.marques.helpdesk.domain;

import com.marques.helpdesk.domain.enums.Prioridade;
import com.marques.helpdesk.domain.enums.Status;

import java.time.LocalDate;

public class Chamado {
    private Integer id;
    private LocalDate dataAbertura = LocalDate.now();
    private LocalDate dataFechamento = LocalDate.now();
    private Prioridade prioridade;
    private Status status;
    private String titulo;
    private String observacoes;

    private Tecnico tecnico;
    private Cliente cliente;

    public Chamado() {
        super();
    }

    public Chamado(Integer id, Prioridade prioridade, Status status, String observacoes, String titulo, Cliente cliente, Tecnico tecnico) {
        this.id = id;
        this.prioridade = prioridade;
        this.status = status;
        this.observacoes = observacoes;
        this.titulo = titulo;
        this.cliente = cliente;
        this.tecnico = tecnico;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Chamado other = (Chamado) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
