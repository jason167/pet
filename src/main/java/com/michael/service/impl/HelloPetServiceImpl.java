package com.michael.service.impl;

import com.michael.annotation.PetField;
import com.michael.annotation.PetServiceExporter;
import com.michael.service.IHelloPetService;


@PetServiceExporter
public class HelloPetServiceImpl implements IHelloPetService
 {

		
	@Override
	@PetField(value = "hello", version = "1.0.0")
	public Object proc(String msg){
		return "Hello " + msg;
	}
	
	@Override
	@PetField(value = {"hello", "hi"}, version = "1.0.1")
	public Object proc_v2(String msg){
		return "Hello " + msg;
	}
	
	
}
