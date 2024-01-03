package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaConsultas;
import med.voll.api.domain.consulta.ConsultaReturnDTO;
import med.voll.api.domain.consulta.PostConsultaDTO;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<PostConsultaDTO> postConsultaDTOJacksonTester;

    @Autowired
    private JacksonTester<ConsultaReturnDTO> consultaReturnDTOJacksonTester;

    @MockBean
    private AgendaConsultas agendaConsultas;

    @Test
    @DisplayName("Deveria devolver codigo HTTP 400 quando informacoes estao invalidas")
    @WithMockUser
    void agendarCenario1() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(post("/consultas"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo HTTP 200 quando informacoes estao validas")
    @WithMockUser
    void agendarCenario2() throws Exception {
        LocalDateTime date = LocalDateTime.now().plusHours(1);
        Especialidade especialidade = Especialidade.CARDIOLOGIA;

        ConsultaReturnDTO consultaReturnDTO = new ConsultaReturnDTO(null, 2L, 5L, date);

        when(agendaConsultas.agendar(any())).thenReturn(consultaReturnDTO);

        MockHttpServletResponse response = mvc
                .perform(
                        post("/consultas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(postConsultaDTOJacksonTester.write(
                                        new PostConsultaDTO(2L, 5L, date, especialidade)
                                ).getJson())
                ).andReturn()
                .getResponse();

        var expectedJson = consultaReturnDTOJacksonTester.write(consultaReturnDTO).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
}