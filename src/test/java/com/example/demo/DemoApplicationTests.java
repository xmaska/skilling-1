package com.example.demo;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DemoApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void greeting() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/hello").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Skilling\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string("Hello Skilling!"));
    }

    @Test
    void statusUptime() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/health"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\":\"UP\"}"));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/metrics/process.uptime").accept(MediaType.APPLICATION_JSON)).andReturn();
        Double upTimeSeconds1 = JsonPath.parse(result.getResponse().getContentAsString()).read("$.measurements[0].value");
        TimeUnit.SECONDS.sleep(5);
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.get("/metrics/process.uptime").accept(MediaType.APPLICATION_JSON)).andReturn();
        Double upTimeSeconds2 = JsonPath.parse(result2.getResponse().getContentAsString()).read("$.measurements[0].value");
//        The request itself takes some time but it should not take more than 1 second... Therefore only checking the whole integer part
        assertThat((int) (upTimeSeconds2 - upTimeSeconds1)).isEqualTo(5);

    }

}
