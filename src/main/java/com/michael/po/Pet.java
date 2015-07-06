package com.michael.po;

public class Pet extends Animal{
	

	private String petName;   // nike name
	
	public Pet(String petName) {
		super();
		this.petName = petName;
	}
	
	public Pet() {
		// TODO Auto-generated constructor stub
	}
	
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}

	@Override
	public String toString() {
		return "Pet [petName=" + petName +"," + super.toString() +"]";
	}
	
	
	
}
