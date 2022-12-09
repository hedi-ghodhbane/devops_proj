package com.example.devopsproject.services;

import com.example.devopsproject.exceptions.ResourceNotFoundException;
import com.example.devopsproject.models.Owner;
import com.example.devopsproject.repositories.OwnerRepository;
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
public class OwnerServiceTest {

    /**
     * Autowire in the service we want to test
     */
    @Autowired
    private OwnerService service;

    /**
     * Create a mock implementation of the OwnerRepository
     */
    @MockBean
    private OwnerRepository repository;


    @Test
    @DisplayName("Test findOwnerById Success")
    void testFindById() {
        // Set up our mock repository
        Owner owner = new Owner(1l, "Owner name", 15);
        doReturn(Optional.of(owner)).when(repository).findById(1l);

        // Execute the service call
        Owner returnedOwner = service.getOwnerById(1l);

        // Assert the response
        Assertions.assertSame(returnedOwner, owner, "The owner returned was not the same as the mock");
    }

    @Test
    @DisplayName("Test findOwnerById Not Found")
    void testFindByIdNotFound() {

        doReturn(Optional.empty()).when(repository).findById(1l);

        Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Owner returnedOwner = service.getOwnerById(1l);
        });
        String expectedMessage = "Owner with this Id not found";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Test getAllOwners")
    void testGetAllOwners() {

        // Set up our mock repository
        Owner owner1 = new Owner(1l, "sofiene", 26);
        Owner owner2 = new Owner(2l, "bilel", 25);
        doReturn(Arrays.asList(owner1, owner2)).when(repository).findAll();

        // Execute the service call
        List<Owner> owners = service.getAllOwners();

        // Assert the response
        Assertions.assertEquals(2, owners.size(), "findAll should return 2 owners");
    }


    @Test
    @DisplayName("Test createOwner")
    void testCreateOwner() {

        Owner ownerToSave = new Owner("bilel",24);
        Owner owner = new Owner(1l, "bilel", 24);
        doReturn(owner).when(repository).save(ownerToSave);

        // Execute the service call
        Owner returnedOwner = service.createOwner(ownerToSave);

        // Assert the response
        Assertions.assertNotNull(returnedOwner, "The saved owner should not be null");
        Assertions.assertSame(owner, returnedOwner, "The returned owner is not the same as expected");
    }
}