package com.code.service;

import com.code.dto.PessoaDTO;
import com.code.exception.NotFoundException;
import com.code.model.Pessoa;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.code.repository.PessoaRepository;
import com.code.repository.ProjetoRepository;

import java.util.List;

@Service
@Transactional
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(final PessoaRepository pessoaRepository,
            final ProjetoRepository projetoRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Cacheable("pessoa")
    public List<PessoaDTO> findAll() {
        final List<Pessoa> pessoas = pessoaRepository.findAll(Sort.by("id"));
        return pessoas.stream()
                .map(pessoa -> mapToDTO(pessoa, new PessoaDTO()))
                .toList();
    }

    @Cacheable("pessoa")
    public PessoaDTO get(final Long id) {
        return pessoaRepository.findById(id)
                .map(pessoa -> mapToDTO(pessoa, new PessoaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @CacheEvict(value = "pessoa", allEntries=true)
    public Long create(final PessoaDTO pessoaDTO) {
        final Pessoa pessoa = new Pessoa();
        mapToEntity(pessoaDTO, pessoa);
        return pessoaRepository.save(pessoa).getId();
    }

    @CachePut(value = "pessoa", key = "#id")
    @CacheEvict(value = "pessoa", allEntries=true)
    public void update(final Long id, final PessoaDTO pessoaDTO) {
        final Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(pessoaDTO, pessoa);
        pessoaRepository.save(pessoa);
    }

    @CachePut(value = "pessoa", key = "#id")
    @CacheEvict(value = "pessoa", allEntries=true)
    public void delete(final Long id) {
        final Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        pessoaRepository.delete(pessoa);
    }

    private PessoaDTO mapToDTO(final Pessoa pessoa, final PessoaDTO pessoaDTO) {
        pessoaDTO.setId(pessoa.getId());
        pessoaDTO.setNome(pessoa.getNome());
        pessoaDTO.setDatanascimento(pessoa.getDatanascimento());
        pessoaDTO.setCpf(pessoa.getCpf());
        pessoaDTO.setFuncionario(pessoa.getFuncionario());
        return pessoaDTO;
    }

    private Pessoa mapToEntity(final PessoaDTO pessoaDTO, final Pessoa pessoa) {
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setDatanascimento(pessoaDTO.getDatanascimento());
        pessoa.setCpf(pessoaDTO.getCpf());
        pessoa.setFuncionario(pessoaDTO.getFuncionario());
        return pessoa;
    }

}
