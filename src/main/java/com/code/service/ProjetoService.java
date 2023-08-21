package com.code.service;

import com.code.dto.ProjetoDTO;
import com.code.enums.Status;
import com.code.exception.BusinessException;
import com.code.exception.NotFoundException;
import com.code.model.Membros;
import com.code.model.MembrosKey;
import com.code.model.Pessoa;
import com.code.model.Projeto;
import com.code.repository.PessoaRepository;
import com.code.repository.ProjetoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final PessoaRepository pessoaRepository;

    public ProjetoService(final ProjetoRepository projetoRepository,
            final PessoaRepository pessoaRepository) {
        this.projetoRepository = projetoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Cacheable("projeto")
    public List<ProjetoDTO> findAll() {
        final List<Projeto> projetos = projetoRepository.findAll(Sort.by("id"));
        return projetos.stream()
                .map(projeto -> mapToDTO(projeto, new ProjetoDTO()))
                .toList();
    }

    @Cacheable("projeto")
    public ProjetoDTO get(final Long id) {
        return projetoRepository.findById(id)
                .map(projeto -> mapToDTO(projeto, new ProjetoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @CacheEvict(value = "projeto", allEntries=true)
    public Long create(final ProjetoDTO projetoDTO) {
        final Projeto projeto = new Projeto();
        mapToEntity(projetoDTO, projeto);
        Projeto projetoSalvo =  projetoRepository.save(projeto);
        Long id = projetoSalvo.getId();

        final List<Pessoa> pessoasMembros = pessoaRepository.findAllById(projetoDTO.getPessoasMembros() == null ? Collections.emptyList() : projetoDTO.getPessoasMembros());
        if (pessoasMembros.size() != (projetoDTO.getPessoasMembros() == null ? 0 : projetoDTO.getPessoasMembros().size())) {
            throw new NotFoundException("Um das pessoas não encontrada");
        }

        List<Membros> membros = pessoasMembros.stream()
                .filter(p -> p.getFuncionario()==true)
                .collect(
                        Collectors.mapping(
                                p -> new Membros(new MembrosKey(p.getId(), id), p, projetoSalvo),
                                Collectors.toList()));

        projeto.setMembros(membros);

        projetoRepository.save(projeto);

        return id;
    }

    @CachePut(value = "projeto", key = "#id")
    @CacheEvict(value = "projeto", allEntries=true)
    public void update(final Long id, final ProjetoDTO projetoDTO) {
        final Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(projetoDTO, projeto);
        projetoRepository.save(projeto);
    }

    @CachePut(value = "projeto", key = "#id")
    @CacheEvict(value = "projeto", allEntries=true)
    public void updateStatus(final Long id, final Status status) {
        final Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        projeto.setStatus(status);
        projetoRepository.save(projeto);
    }

    @CacheEvict(value = "projeto", key = "#id")
    public void delete(final Long id) {
        ProjetoDTO projeto = projetoRepository.findById(id)
                .filter(p -> p.getStatus() != Status.INICIADO
                        && p.getStatus() != Status.EM_ANDAMENTO
                        && p.getStatus() != Status.ENCERRADO)
                .map(p -> mapToDTO(p, new ProjetoDTO()))
                .orElseThrow(() -> new BusinessException("Status não permite a exclusão"));

        projetoRepository.deleteById(projeto.getId());
    }

    private ProjetoDTO mapToDTO(final Projeto projeto, final ProjetoDTO projetoDTO) {
        projetoDTO.setId(projeto.getId());
        projetoDTO.setNome(projeto.getNome());
        projetoDTO.setDataInicio(projeto.getDataInicio());
        projetoDTO.setDataPrevisaoFim(projeto.getDataPrevisaoFim());
        projetoDTO.setDataFim(projeto.getDataFim());
        projetoDTO.setDescricao(projeto.getDescricao());
        projetoDTO.setStatus(projeto.getStatus());
        projetoDTO.setOrcamento(projeto.getOrcamento());
        projetoDTO.setRisco(projeto.getRisco());
        projetoDTO.setIdgerente(projeto.getGerente() == null ? null : projeto.getGerente().getId());
        projetoDTO.setPessoasMembros(projeto.getMembros().stream().map(pessoa -> pessoa.getPessoa().getId()).toList());
        return projetoDTO;
    }

    private Projeto mapToEntity(final ProjetoDTO projetoDTO, final Projeto projeto) {
        projeto.setNome(projetoDTO.getNome());
        projeto.setDataInicio(projetoDTO.getDataInicio());
        projeto.setDataPrevisaoFim(projetoDTO.getDataPrevisaoFim());
        projeto.setDataFim(projetoDTO.getDataFim());
        projeto.setDescricao(projetoDTO.getDescricao());
        projeto.setStatus(projetoDTO.getStatus());
        projeto.setOrcamento(projetoDTO.getOrcamento());
        projeto.setRisco(projetoDTO.getRisco());

        final Pessoa gerente = projetoDTO.getIdgerente() == null ? null : pessoaRepository.findById(projetoDTO.getIdgerente()).orElseThrow(() -> new NotFoundException("Gerente não encontrado"));
        projeto.setGerente(gerente);

        return projeto;
    }

}
