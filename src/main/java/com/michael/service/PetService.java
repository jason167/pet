package com.michael.service;

import java.util.List;

import com.michael.po.Pet;
public interface PetService {

	
	public List<Pet> searchPets(int masterId);
	public Pet findById(int id);
}
