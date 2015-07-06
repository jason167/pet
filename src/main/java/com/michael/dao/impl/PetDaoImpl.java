package com.michael.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.michael.dao.PetDao;
import com.michael.po.Cat;
import com.michael.po.Dog;
import com.michael.po.Pet;

@Service
public class PetDaoImpl implements PetDao {

	public List<Pet> findByMasterId(int masterId) {
		// TODO Auto-generated method stub
		List<Pet> pets = new ArrayList<Pet>();
		if (masterId == 1) {
			pets.add(createCat());
		}else{
			pets.add(createDog());
		}
		return pets;
	}
	
	@Override
	public Pet findById(int id) {
		// TODO Auto-generated method stub
		if (id == 1) {
			return createCat();
		}else{
			return createDog();
		}
	}
	
	private Pet createCat(){
		Pet cat = new Cat("小白");
		cat.setMasterId(1);
		cat.setId(1);
		cat.setAge(18);
		cat.setGender(0);
		cat.setSpecies("波斯猫");
		
		return cat;
	}
	
	private Pet createDog(){
		Pet dog = new Dog("小黑");
		dog.setMasterId(2);
		dog.setId(2);
		dog.setAge(20);
		dog.setGender(1);
		dog.setSpecies("八哥犬");
		return dog;
	}


}
