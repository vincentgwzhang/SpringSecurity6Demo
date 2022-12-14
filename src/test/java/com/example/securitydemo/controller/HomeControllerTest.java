package com.example.securitydemo.controller;

import com.example.securitydemo.dto.DeveloperDTO;
import com.example.securitydemo.service.DeveloperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class HomeControllerTest {

    @Mock
    private DeveloperService developerService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        // 使用 Mock 的 when 的说话，就一定要这样设置 mvc
        mockMvc = MockMvcBuilders.standaloneSetup(new HomeController(developerService)).build();
    }


    @Test
    @WithMockUser(roles = {"DEVELOPER"})
    void testGetDeveloper2() throws Exception {

        int id = 200;
        DeveloperDTO developerDTO = newDeveloperDTO(id);
        when(developerService.getDeveloper(id)).thenReturn(developerDTO);

        MvcResult mvcResult = mockMvc
                .perform(
                        post("/developer2/200").with(csrf())
                )
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(mvcResult);

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertThat(modelAndView, notNullValue());

        Map<String, Object> maps = modelAndView.getModel();
        assertThat(maps, notNullValue());
        assertThat(maps, hasKey("developer"));

        DeveloperDTO resultDeveloper = (DeveloperDTO)maps.get("developer");
        assertThat(resultDeveloper, is(developerDTO));
    }

    private DeveloperDTO newDeveloperDTO(int id) {
        DeveloperDTO developerDTO = new DeveloperDTO();
        developerDTO.setId(id);
        developerDTO.setFirstName("first name");
        developerDTO.setLastName("last name");
        developerDTO.setAge(28);
        return developerDTO;
    }

}
