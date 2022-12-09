package com.example.devopsproject.services;

import com.example.devopsproject.exceptions.ResourceNotFoundException;
import com.example.devopsproject.models.Owner;
import com.example.devopsproject.models.Pet;
import com.example.devopsproject.repositories.OwnerRepository;
import com.example.devopsproject.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {


    @Autowired
    PetRepository petRepository;

    @Autowired
    OwnerRepository ownerRepository;

    public Pet createPet(Pet pet, Long ownerId) throws ResourceNotFoundException {

        Optional<Owner> ownerData = this.ownerRepository.findById(ownerId);
        if(ownerData.isPresent()) {
            Owner owner = ownerData.orElseThrow(()-> new ResourceNotFoundException("Owner not found"));
            pet.setOwner(owner);
            Pet createdPet = this.petRepository.save(pet);
            return createdPet;
        }else{
            throw new  ResourceNotFoundException("No owner found with that Id");
        }
    }

    public List<Pet> getAllPets() throws ResourceNotFoundException {
        List<Pet> pets = this.petRepository.findAll();
        return pets;
    }

    public Pet getPetById(Long petId) throws ResourceNotFoundException {
        Optional<Pet> petData = this.petRepository.findById(petId);
        if(petData.isPresent()){
            Pet pet = petData.orElseThrow(()-> new ResourceNotFoundException("owner not found"));
            return pet;
        }else{
            throw new  ResourceNotFoundException("Pet with this Id not found");
        }
    }

}
