package com.example.devopsproject.controllers;
import com.example.devopsproject.models.Pet;
import com.example.devopsproject.services.PetService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.devopsproject.utils.objectMapper.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PetControllerTest {

    @MockBean
    private PetService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /all success")
    void testGetPetsSuccess() throws Exception {

        Pet pet1 = new Pet(1l, "putchi", "description of putchii test");
        Pet pet2 = new Pet(2l, "maka", "description of maka test");
        doReturn(Lists.newArrayList(pet1, pet2)).when(service).getAllPets();

        mockMvc.perform(get("/pet/all"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("putchi")))
                .andExpect(jsonPath("$[0].description", is("description of putchii test")))
                .andExpect(jsonPath("$[1].name", is("maka")))
                .andExpect(jsonPath("$[1].description", is("description of maka test")));
    }

    @Test
    @DisplayName("GET /pet/1")
    void testGetPetById() throws Exception {
        // Set up our mocked service
        Pet pet = new Pet(1l, "putchi", "description of putchii test");
        doReturn(pet).when(service).getPetById(1l);

        // Execute the GET request
        mockMvc.perform(get("/pet/{id}", 1L))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("putchi")))
                .andExpect(jsonPath("$.description", is("description of putchii test")));
    }

    @Test
    @DisplayName("POST /pet/create")
    void testCreatePet() throws Exception {
        // Set up our mocked service

        Long ownerId = 1l ;
        Pet petToCreate = new Pet("putchi","putchi description" );
        Pet petToReturn = new Pet(1L, "putchi","putchi description");
        doReturn(petToReturn).when(service).createPet(petToCreate,ownerId);

        // Execute the POST request
        mockMvc.perform(post("/pet/create/{id}", ownerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(petToCreate)))

                // Validate the response code and content type
                .andExpect(status().isOk());
    }

}
