package com.example.devopsproject.services;

import com.example.devopsproject.exceptions.ResourceNotFoundException;
import com.example.devopsproject.models.Owner;
import com.example.devopsproject.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    @Autowired
    OwnerRepository ownerRepository;

    public Owner createOwner(Owner owner) throws ResourceNotFoundException {

        Owner createdOwner = this.ownerRepository.save(owner);
        return createdOwner;
    }

    public List<Owner> getAllOwners() throws ResourceNotFoundException {

        List<Owner> owners = this.ownerRepository.findAll();
        return owners;
    }

    public Owner getOwnerById(Long ownerId) throws ResourceNotFoundException {

        Optional<Owner> ownerData = this.ownerRepository.findById(ownerId);
        if(ownerData.isPresent()){
            Owner owner = ownerData.orElseThrow(()-> new ResourceNotFoundException("owner not found"));
            return owner;
        }else{
            throw new  ResourceNotFoundException("Owner with this Id not found");
        }
    }

}
