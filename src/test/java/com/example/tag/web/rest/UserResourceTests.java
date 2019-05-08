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
public class UserResourceTests {

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
    public void case1_login_fail_with_empty_name_pwd() throws Exception {
        MvcResult result = this.mockMvc.perform(
                post("/api/user/login").content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        JsonElement je = new JsonParser().parse(result.getResponse().getContentAsString());
        int code = je.getAsJsonObject().get("code").getAsInt();
        assertEquals(code, 300);
    }


    @Test
    @Sql(
            config = @SqlConfig(dataSource = "sqliteDataSource"),
            scripts = "/prepareData/com.example.tag.web.rest/UserResourceTests/case2_login_success.sql"
    )
    public void case2_login_success() throws Exception {

        JsonObject json = new JsonObject();
        json.addProperty("name", "admin");
        json.addProperty("password", "123456");

        MvcResult result = this.mockMvc.perform(
                post("/api/user/login").content(json.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        JsonElement je = new JsonParser().parse(result.getResponse().getContentAsString());
        int code = je.getAsJsonObject().get("code").getAsInt();
        assertEquals(code, 200);

    }

    @Test
    public void case3_login_fail_with_wrong_pass() throws Exception {

        JsonObject json = new JsonObject();
        json.addProperty("name", "admin");
        json.addProperty("password", "wrong");

        MvcResult result = this.mockMvc.perform(
                post("/api/user/login").content(json.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        JsonElement je = new JsonParser().parse(result.getResponse().getContentAsString());
        int code = je.getAsJsonObject().get("code").getAsInt();
        assertEquals(code, 200);

    }
}
