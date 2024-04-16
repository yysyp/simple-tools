package ps.demo.controller;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ps.demo.service.MongoService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MongoController.class)
class MongoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MongoService mongoService;

    @Test
    void findByCollectionName() throws Exception {
        String collectionName = "collectionName";

        when(mongoService.findAll(collectionName))
                .thenReturn(List.of(new Document("key1", "value1")));

        mockMvc.perform(get("/api/mongo/collection-name/"+collectionName+"/documents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].key1").value("value1"));

    }

    @Test
    void findByCollectionNameByKey() {
    }

    @Test
    void insertByCollectionName() {
    }
}