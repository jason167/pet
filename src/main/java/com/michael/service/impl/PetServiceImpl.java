package com.michael.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.michael.dao.PetDao;
import com.michael.po.Pet;
import com.michael.service.PetService;

@Service
public class PetServiceImpl implements PetService {

	@Resource
	private PetDao petDaoImpl;
	
	public List<Pet> searchPets(int masterId) {
		// TODO Auto-generated method stub
		return petDaoImpl.findByMasterId(masterId);
	}

	@Override
	public Pet findById(int id) {
		// TODO Auto-generated method stub
		return petDaoImpl.findById(id);
	}

}
