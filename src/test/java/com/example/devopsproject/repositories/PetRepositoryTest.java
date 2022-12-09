package com.example.devopsproject.repositories;

import com.example.devopsproject.models.Owner;
import com.example.devopsproject.models.Pet;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@ExtendWith(DBUnitExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class PetRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PetRepository repository;

    public ConnectionHolder getConnectionHolder() {
        return () -> dataSource.getConnection();
    }

    @Test
    @DataSet("pets.yml")
    void testGetAllPets() {
        List<Pet> pets = Lists.newArrayList(repository.findAll());
        Assertions.assertEquals(2, pets.size(), "Expected 2 pets in the database");
    }

    @Test
    @DataSet("pets.yml")
    void testGetByIdSuccess() {
        Optional<Pet> pet = repository.findById(1L);
        Assertions.assertTrue(pet.isPresent(), "We should find an pet with ID 1");

        Pet p = pet.get();
        Assertions.assertEquals(1, p.getId(), "The pet ID should be 1");
        Assertions.assertEquals("pet 1", p.getName(), "Incorrect pet name");
        Assertions.assertEquals("the description of pet 1", p.getDescription(), "Incorrect pet age");
    }

    @Test
    @DataSet("pets.yml")
    void testGetByIdNotFound() {
        Optional<Pet> pet = repository.findById(3L);
        Assertions.assertFalse(pet.isPresent(), "A pet with ID 3 should not be found");
    }
}
