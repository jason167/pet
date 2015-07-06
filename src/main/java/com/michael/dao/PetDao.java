package com.michael.dao;

import java.util.List;

import com.michael.po.Pet;

public interface PetDao {

	
	public List<Pet> findByMasterId(int masterId);
	public Pet findById(int id);
}
