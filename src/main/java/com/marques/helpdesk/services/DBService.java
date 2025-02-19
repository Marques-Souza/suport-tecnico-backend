package com.marques.helpdesk.services;


import com.marques.helpdesk.domain.Chamado;
import com.marques.helpdesk.domain.Cliente;
import com.marques.helpdesk.domain.Tecnico;
import com.marques.helpdesk.domain.enums.Perfil;
import com.marques.helpdesk.domain.enums.Prioridade;
import com.marques.helpdesk.domain.enums.Status;
import com.marques.helpdesk.repositories.ChamadoRepository;
import com.marques.helpdesk.repositories.ClienteRepository;
import com.marques.helpdesk.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    TecnicoRepository tecnicoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ChamadoRepository chamadoRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void instanciaDB(){

        Tecnico tec1 = new Tecnico(null, "Marques Souza", "03589646101", "admin@admin.com",encoder.encode("123"));
        tec1.addPerfil(Perfil.ADMIN);

        Tecnico tec2 = new Tecnico(null, "Caio Souza", "83085270000", "caio@email.com",encoder.encode("123"));
        tec2.addPerfil(Perfil.TECNICO);

        Tecnico tec3 = new Tecnico(null, "Cleber Souza", "68406569000", "cleber@email.com",encoder.encode("123"));
        tec3.addPerfil(Perfil.TECNICO);

        Tecnico tec4 = new Tecnico(null, "Yore Souza", "85842700000", "yore@email.com",encoder.encode("123"));
        tec4.addPerfil(Perfil.TECNICO);

        Cliente cli1 = new Cliente(null, "Samantha Soares", "72888334100", "samantha@email.com",encoder.encode("123"));

        Cliente cli2 = new Cliente(null, "Karol Soares", "15320786093", "karol@email.com",encoder.encode("123"));

        Cliente cli3 = new Cliente(null, "Geralda Soares", "41660905001", "geralda@email.com",encoder.encode("123"));

        Cliente cli4 = new Cliente(null, "Sara Soares", "84623724026", "sara@email.com",encoder.encode("123"));

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ABERTO, "Chamando 01", "Primeiro chamado", tec1, cli1);

        Chamado c2 = new Chamado(null, Prioridade.BAIXA, Status.ABERTO, "Chamando 02", "Segundo chamado", tec2, cli2);

        Chamado c3 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Chamando 03", "Terceiro chamado", tec1, cli2);

        Chamado c4 = new Chamado(null, Prioridade.MEDIA, Status.ABERTO, "Chamando 04", "Quarto chamado", tec3, cli3);

        tecnicoRepository.saveAll(Arrays.asList(tec1, tec2, tec3, tec4));
        clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3, cli4));
        chamadoRepository.saveAll(Arrays.asList(c1, c2, c3, c4));

    }

}
