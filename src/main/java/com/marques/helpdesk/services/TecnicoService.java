package com.marques.helpdesk.services;


import com.marques.helpdesk.domain.Pessoa;
import com.marques.helpdesk.domain.Tecnico;
import com.marques.helpdesk.domain.dtos.TecnicoDTO;
import com.marques.helpdesk.repositories.ChamadoRepository;
import com.marques.helpdesk.repositories.PessoaRepository;
import com.marques.helpdesk.repositories.TecnicoRepository;
import com.marques.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.marques.helpdesk.services.exceptions.ObjectnotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! Id: " + id));
    }


    public List<Tecnico> findAll() {
        return repository.findAll();

    }

    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null);
        objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        validaPorCpfEEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO);
        return repository.save(newObj);
    }

    public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
        objDTO.setId(id);
        Tecnico oldObj = findById(id);
        validaPorCpfEEmail(objDTO);
        oldObj = new Tecnico(objDTO);
        return repository.save(oldObj);
    }

    public void delete(Integer id) {
            Tecnico obj = findById(id);
            if (obj.getChamados().size() > 0){
                throw new DataIntegrityViolationException("Técnico possui ordens de serviço e nao pode ser deletado!");
            }
            repository.deleteById(id);
    }



    private void validaPorCpfEEmail(TecnicoDTO objDTO) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("CPF já Cadastrado no sistema!");
        }

        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("E-mail já Cadastrado no sistema!");
        }
    }



}
