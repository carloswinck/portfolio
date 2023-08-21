package com.code;

import com.code.dto.PessoaDTO;
import com.code.service.PessoaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class PessoaServiceTest {

    @Autowired
    private PessoaService pessoaService;

    @Test
    public void getPaulo() {
        List<PessoaDTO> listaPessoaDTO =  pessoaService.findAll();
        assertEquals(listaPessoaDTO.get(0).getNome(), "Paulo");
    }

}
