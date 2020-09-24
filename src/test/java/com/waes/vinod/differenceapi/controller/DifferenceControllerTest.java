package com.waes.vinod.differenceapi.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waes.vinod.differenceapi.DifferenceApiApplication;
import com.waes.vinod.differenceapi.model.DifferenceType;
import com.waes.vinod.differenceapi.model.Request;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DifferenceApiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DifferenceControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void saveLeft() throws Exception {
        String id = UUID.randomUUID().toString();
        Request request = Request.builder().value("adkshfuiarkngvfd=").build();
        mockMvc.perform(put("/v1/diff/" + id + "/left")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void saveRight() throws Exception {
        String id = UUID.randomUUID().toString();
        Request request = Request.builder().value("afdsjbbfjhgfjsdkjfn=").build();
        mockMvc.perform(put("/v1/diff/" + id + "/right")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDifferenceWithStatusEqual() throws Exception {
        String id = UUID.randomUUID().toString();
        Request request = Request.builder().value("adkshfuiarkngvfd=").build();
        mockMvc.perform(put("/v1/diff/" + id + "/left")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request)))
                .andExpect(status().isNoContent());

        mockMvc.perform(put("/v1/diff/" + id + "/right")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/v1/diff/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(DifferenceType.EQUALS.toString())));

    }

    @Test
    public void testDifferenceWithStatusDifferentSize() throws Exception {
        String id = UUID.randomUUID().toString();
        Request request = Request.builder().value("adkshfuiarkngvfd=").build();
        mockMvc.perform(put("/v1/diff/" + id + "/left")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request)))
                .andExpect(status().isNoContent());
        request = Request.builder().value("adarkngvfd=").build();
        mockMvc.perform(put("/v1/diff/" + id + "/right")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/v1/diff/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(DifferenceType.DIFFERENT_SIZE.toString())));

    }

    @Test
    public void testDifferenceWithStatusDifferentContent() throws Exception {
        String id = UUID.randomUUID().toString();
        Request request = Request.builder().value("adkshfuiarkngvfd=").build();
        mockMvc.perform(put("/v1/diff/" + id + "/left")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request)))
                .andExpect(status().isNoContent());
        request = Request.builder().value("adkshfuibrkngvfd=").build();
        mockMvc.perform(put("/v1/diff/" + id + "/right")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/v1/diff/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(DifferenceType.DIFFERENT_CONTENT.toString())));

    }

    @Test
    public void testDifferenceWithErrorNoContentOneSide() throws Exception {
        String id = UUID.randomUUID().toString();
        Request request = Request.builder().value("adkshfuiarkngvfd=").build();
        mockMvc.perform(put("/v1/diff/" + id + "/left")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/v1/diff/" + id))
                .andExpect(status().isConflict());
    }

    @Test
    public void testDifferenceWithIdDoesNotExit() throws Exception {
        String id = UUID.randomUUID().toString();
        mockMvc.perform(get("/v1/diff/" + id))
                .andExpect(status().isNotFound());
    }

    public static byte[] convertToJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}