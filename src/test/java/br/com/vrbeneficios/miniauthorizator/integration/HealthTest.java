package br.com.vrbeneficios.miniauthorizator.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.json.JSONObject;

@SpringBootTest
@AutoConfigureMockMvc
public class HealthTest {

    private final String HEALTH_PATH = "/actuator/health";

    private final String STATUS_ATTIBUTE = "status";
    private final String STATUS_SUCCESS = "UP";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void healthCheck() throws Exception {
        MockHttpServletResponse httpServletResponse = this.mockMvc.perform(get(HEALTH_PATH)).andExpect(status().isOk())
                .andReturn().getResponse();
        JSONObject responseContentJSON = new JSONObject(httpServletResponse.getContentAsString());

        assertEquals(responseContentJSON.get(STATUS_ATTIBUTE), STATUS_SUCCESS);
    }
}
