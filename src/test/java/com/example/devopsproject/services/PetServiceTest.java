package com.example.devopsproject.services;

import com.example.devopsproject.exceptions.ResourceNotFoundException;
import com.example.devopsproject.models.Owner;
import com.example.devopsproject.models.Pet;
import com.example.devopsproject.repositories.OwnerRepository;
import com.example.devopsproject.repositories.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class PetServiceTest {

    /**
     * Autowire in the service we want to test
     */
    @Autowired
    private PetService service;

    /**
     * Create a mock implementation of the PetRepository
     */
    @MockBean
    private PetRepository petRepository;

    /**
     * Create a mock implementation of the OwnerRepository
     */
    @MockBean
    private OwnerRepository ownerRepository;

    @Test
    @DisplayName("Test findPetById Success")
    void testFindById() {
        // Set up our mock repository
        Pet pet = new Pet(1l, "putchi", "test putchi description");
        doReturn(Optional.of(pet)).when(petRepository).findById(1l);

        // Execute the service call
        Pet returnedPet = service.getPetById(1l);

        // Assert the response
        Assertions.assertSame(returnedPet, pet, "The pet returned was not the same as the mock");
    }

    @Test
    @DisplayName("Test findPetById Not Found")
    void testFindByIdNotFound() {

        doReturn(Optional.empty()).when(petRepository).findById(1l);

        Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Pet returnedPet = service.getPetById(1l);
        });
        String expectedMessage = "Pet with this Id not found";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    @DisplayName("Test getAllPets")
    void testGetAllPets() {

        // Set up our mock repository
        Pet pet1 = new Pet(1l, "putchi", "description of putchii test");
        Pet pet2 = new Pet(2l, "maka", "description of maka test");
        doReturn(Arrays.asList(pet1, pet2)).when(petRepository).findAll();

        // Execute the service call
        List<Pet> pets = service.getAllPets();

        // Assert the response
        Assertions.assertEquals(2, pets.size(), "findAll should return 2 pets");
    }


    @Test
    @DisplayName("Test createPet")
    void testCreatePet() {

        Owner owner = new Owner(1l, "sofiene", 15);
        doReturn(Optional.of(owner)).when(ownerRepository).findById(1l);

        Long ownerId = 1l ;

        Pet petToSave = new Pet("putchi","putchi description");
        Pet pet = new Pet(1l, "putchi", "putchi description", owner);
        doReturn(pet).when(petRepository).save(petToSave);

        // Execute the service call
        Pet returnedPet = service.createPet(petToSave,ownerId);

        // Assert the response
        Assertions.assertNotNull(returnedPet, "The saved pet should not be null");
        Assertions.assertSame(pet, returnedPet, "The returned pet is not the same as expected");
        Assertions.assertSame(pet.getOwner(),owner, "The pet owner is not with the passed Id");
    }
}
