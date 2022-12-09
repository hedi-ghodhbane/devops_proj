package com.example.devopsproject.controllers;

import com.example.devopsproject.models.Owner;
import com.example.devopsproject.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
@CrossOrigin(origins = "*")
public class OwnerController {

    @Autowired
    OwnerService ownerService;


    @GetMapping("/all")
    public List<Owner> getOwners()
    {
        List<Owner> ownersList = this.ownerService.getAllOwners();
        return ownersList;
    }


    @GetMapping("/{ownerId}")
    public Owner getOwnerById(@PathVariable("ownerId") Long ownerId)
    {
        Owner owner = this.ownerService.getOwnerById(ownerId);
        return owner;
    }

    @PostMapping("/create")
    public Owner createNewOwner(@RequestBody Owner newOwner)
    {
        Owner owner = this.ownerService.createOwner(newOwner);
        return owner;
    }

}
