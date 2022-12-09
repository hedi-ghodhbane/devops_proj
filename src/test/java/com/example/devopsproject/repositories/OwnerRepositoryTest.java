package com.example.devopsproject.repositories;

import com.example.devopsproject.models.Owner;
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
public class OwnerRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private OwnerRepository repository;

    public ConnectionHolder getConnectionHolder() {
        return () -> dataSource.getConnection();
    }

    @Test
    @DataSet("owners.yml")
    void testGetAllOwners() {
        List<Owner> owners = Lists.newArrayList(repository.findAll());
        Assertions.assertEquals(2, owners.size(), "Expected 2 owners in the database");
    }

    @Test
    @DataSet("owners.yml")
    void testGetByIdSuccess() {
        Optional<Owner> owner = repository.findById(1L);
        Assertions.assertTrue(owner.isPresent(), "We should find an owner with ID 1");

        Owner o = owner.get();
        Assertions.assertEquals(1, o.getId(), "The owner ID should be 1");
        Assertions.assertEquals("owner 1", o.getName(), "Incorrect owner name");
        Assertions.assertEquals(15, o.getAge(), "Incorrect owner age");
    }

    @Test
    @DataSet("owners.yml")
    void testGetByIdNotFound() {
        Optional<Owner> owner = repository.findById(3L);
        Assertions.assertFalse(owner.isPresent(), "An owner with ID 3 should not be found");
    }
}
