package com.example.tag.web.rest;

import com.example.tag.domain.User;
import com.example.tag.mapper.UserMapper;
import com.example.tag.util.ErrorMsgConst;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;
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

    @Autowired
    private UserMapper userMapper;

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
        assertEquals(200, code);

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
        assertEquals(1000, code);

    }

    @Test
    @Sql(
            config = @SqlConfig(dataSource = "sqliteDataSource"),
            scripts = "/prepareData/com.example.tag.web.rest/UserResourceTests/case4_addUser_userExist.sql"
    )
    public void case4_addUser_userExist() throws Exception {
        String user = "{\n" +
                "  \"token\": \"test\",\n" +
                "  \"username\": \"test\",\n" +
                "  \"password\": \"12345\",\n" +
                "  \"role\": \"atest\"\n" +
                "}";
        MvcResult result = this.mockMvc.perform(
                post("/api/user/addUser").content(user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        JsonElement je = new JsonParser().parse(result.getResponse().getContentAsString());
        int code = je.getAsJsonObject().get("code").getAsInt();
        String msg = je.getAsJsonObject().get("msg").getAsString();
        assertEquals(1000, code);
        assertEquals(ErrorMsgConst.User_Exist, msg);
    }

    @Test
    public void case5_addUser_success() throws Exception {
        String user = "{\n" +
                "  \"token\": \"test\",\n" +
                "  \"username\": \"not_exist\",\n" +
                "  \"password\": \"12345\",\n" +
                "  \"role\": \"atest\"\n" +
                "}";
        MvcResult result = this.mockMvc.perform(
                post("/api/user/addUser").content(user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        JsonElement je = new JsonParser().parse(result.getResponse().getContentAsString());
        int code = je.getAsJsonObject().get("code").getAsInt();
        String name = je.getAsJsonObject().get("data").getAsJsonObject().get("user").getAsJsonObject().get("name").getAsString();
        Assert.assertEquals(200, code);
        assertEquals("not_exist", name);
        User createUser = userMapper.findUserByName(name);
        Assert.assertNotNull(createUser);
    }
}
