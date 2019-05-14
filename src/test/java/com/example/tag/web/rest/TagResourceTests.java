package com.example.tag.web.rest;

import com.example.tag.domain.Tag;
import com.google.gson.*;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagResourceTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private MockHttpSession session;

    private static String tagId;

    @Before
    public void setUp() throws Exception {
        session = new MockHttpSession();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void case1_addTag() throws Exception {
        String tag = "{\n" +
                "\t\"cordcustId\": \"C0003000116273\",\n" +
                "\t\"cordcorType\": \"AR\",\n" +
                "\t\"cordcrDate\": \"20181105\",\n" +
                "\t\"cordcrTime\": \"175831\",\n" +
                "\t\"serialNo\": \"serialNo\",\n" +
                "\t\"customerName\": \"customerName\",\n" +
                "\t\"secureDatas\": [{\n" +
                "\t\t\"tagId\": 5,\n" +
                "\t\t\"security\": \"test\",\n" +
                "\t\t\"depositor\": \"test\",\n" +
                "\t\t\"facilities\": \"test\",\n" +
                "\t\t\"valuation\": \"test\",\n" +
                "\t\t\"tag\": \"test\",\n" +
                "\t\t\"comments\": \"ssss\"\n" +
                "\t\t\n" +
                "\t}],\n" +
                "\t\"keySentences\": [{\n" +
                "\t\t\"type\": \"test\",\n" +
                "\t\t\"content\": \"sdffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffsdddddddddddddddddddddddd\"\n" +
                "\t\t\n" +
                "\t}]\n" +
                "}";
        MvcResult result = this.mockMvc.perform(
                post("/api/tag").content(tag)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(result.getResponse().getContentAsString());
        tag = jsonObject.get("data").getAsJsonObject().get("tag").getAsJsonObject().toString();
        Tag createdTag = new Gson().fromJson(tag, Tag.class);
        this.tagId = Integer.toString(createdTag.getId());
        assertEquals(createdTag.getCordcustId(), "C0003000116273");
        assertEquals(createdTag.getSerialNo(), "serialNo");
        assertEquals(createdTag.getCustomerName(), "customerName");
        Assert.assertTrue(createdTag.getCreatedAt() > 0);
    }


    @Test
    public void case2_getAllTags() throws Exception {
        MvcResult result = this.mockMvc.perform(
                post("/api/tags").content("{}")
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
    public void case2_getAllTagsByCond() throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("serialNo", "serialNo");
        json.addProperty("customerName", "customerName");
        json.addProperty("cordcustId", "C0003000116273");
        json.addProperty("cordcorType", "AR");
        MvcResult result = this.mockMvc.perform(
                post("/api/tags").content(json.toString())
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
    public void case3_getSecure() throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("tagId", this.tagId);
        MvcResult result = this.mockMvc.perform(
                post("/api/tag/secure").content(json.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        JsonElement je = new JsonParser().parse(result.getResponse().getContentAsString());
        JsonArray secureDatas = je.getAsJsonObject().get("data").getAsJsonObject().get("secureDatas").getAsJsonArray();
        assertEquals(secureDatas.size(), 1);
        assertEquals(secureDatas.get(0).getAsJsonObject().get("tagId").getAsString(), tagId);
    }

    @Test
    public void case4_getKeySentence() throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("tagId", this.tagId);
        MvcResult result = this.mockMvc.perform(
                post("/api/tag/keysentence").content(json.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        JsonElement je = new JsonParser().parse(result.getResponse().getContentAsString());
        JsonArray secureDatas = je.getAsJsonObject().get("data").getAsJsonObject().get("keySentences").getAsJsonArray();
        assertEquals(secureDatas.size(), 1);
        assertEquals(secureDatas.get(0).getAsJsonObject().get("tagId").getAsString(), tagId);

    }

    @Test
    public void case5_updateTag() throws Exception {
        System.out.println(this.tagId);
        String tag = "{\n" +
                "\t\"id\":" + this.tagId + "," +
                "\t\"cordcustId\": \"updatedId\",\n" +
                "\t\"cordcorType\": \"updatedType\",\n" +
                "\t\"cordcrDate\": \"updatedDate\",\n" +
                "\t\"cordcrTime\": \"175831\",\n" +
                "\t\"serialNo\": \"serialNo\",\n" +
                "\t\"customerName\": \"customerName\",\n" +
                "\t\"secureDatas\": [{\n" +
                "\t\t\"tagId\": 5,\n" +
                "\t\t\"security\": \"test\",\n" +
                "\t\t\"depositor\": \"test\",\n" +
                "\t\t\"facilities\": \"test\",\n" +
                "\t\t\"valuation\": \"test\",\n" +
                "\t\t\"tag\": \"test\",\n" +
                "\t\t\"comments\": \"ssss\"\n" +
                "\t\t\n" +
                "\t}],\n" +
                "\t\"keySentences\": [{\n" +
                "\t\t\"type\": \"updated\",\n" +
                "\t\t\"content\": \"sdffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffsdddddddddddddddddddddddd\"\n" +
                "\t\t\n" +
                "\t}]\n" +
                "}";
        MvcResult result = this.mockMvc.perform(
                put("/api/tag").content(tag)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        JsonElement je = new JsonParser().parse(result.getResponse().getContentAsString());
        JsonArray keySentences = je.getAsJsonObject().get("data").getAsJsonObject().get("tag").getAsJsonObject().get("keySentences").getAsJsonArray();
        assertEquals(keySentences.size(), 1);
        assertEquals(keySentences.get(0).getAsJsonObject().get("type").getAsString(), "updated");
    }

    @Test
    public void case6_deleteTag() throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("tagId", this.tagId);
        MvcResult result = this.mockMvc.perform(
                delete("/api/tag").content(json.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
//        JsonElement je = new JsonParser().parse(result.getResponse().getContentAsString());
    }


}
