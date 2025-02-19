package com.marques.helpdesk.services;


import com.marques.helpdesk.domain.Cliente;
import com.marques.helpdesk.domain.Pessoa;
import com.marques.helpdesk.domain.dtos.ClienteDTO;
import com.marques.helpdesk.repositories.ChamadoRepository;
import com.marques.helpdesk.repositories.ClienteRepository;
import com.marques.helpdesk.repositories.PessoaRepository;
import com.marques.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.marques.helpdesk.services.exceptions.ObjectnotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private ClienteRepository tecnicoRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! Id: " + id));
    }


    public List<Cliente> findAll() {
        return repository.findAll();

    }

    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null);
        validaPorCpfEEmail(objDTO);
        Cliente newObj = new Cliente(objDTO);
        return repository.save(newObj);
    }

    public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);
        validaPorCpfEEmail(objDTO);
        oldObj = new Cliente(objDTO);
        return repository.save(oldObj);
    }

    public void delete(Integer id) {
            Cliente obj = findById(id);
            if (obj.getChamados().size() > 0){
                throw new DataIntegrityViolationException("Cliente possui ordens de serviço e nao pode ser deletado!");
            }
            repository.deleteById(id);
    }



    private void validaPorCpfEEmail(ClienteDTO objDTO) {
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
