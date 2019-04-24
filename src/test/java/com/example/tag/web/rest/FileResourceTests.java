package com.example.tag.web.rest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FileResourceTests {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        this.session = new MockHttpSession();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    @Sql(
            config = @SqlConfig(dataSource = "as400DataSource"),
            scripts = "/prepareData/com.example.tag.web.rest/FileResourceTests/case1_getAllFiles.sql"
    )
    public void case1_getAllFiles() throws Exception {
        MvcResult result = this.mockMvc.perform(
                post("/api/files").content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        JsonElement je = new JsonParser().parse(result.getResponse().getContentAsString());
        int total = je.getAsJsonObject().get("data").getAsJsonObject().get("total").getAsInt();
        assertEquals(total, 1);
    }

    @Test
    public void case1_getAllFilesByCond() throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("customerId", "not exist");
        json.addProperty("type", "AC");
        MvcResult result = this.mockMvc.perform(
                post("/api/files").content(json.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        JsonElement je = new JsonParser().parse(result.getResponse().getContentAsString());
        int total = je.getAsJsonObject().get("data").getAsJsonObject().get("total").getAsInt();
        assertEquals(total, 0);
    }
}
