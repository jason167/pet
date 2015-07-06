package com.michael.po;

public class Animal {

	private int masterId;
	private int id;
	private String speciesName;	
	private int age;
	private int gender;
	private String species;			// 品种
	
	
	public int getMasterId() {
		return masterId;
	}
	public int getId() {
		return id;
	}
	public String getSpeciesName() {
		return speciesName;
	}
	public int getAge() {
		return age;
	}
	public int getGender() {
		return gender;
	}
	public String getSpecies() {
		return species;
	}
	
	
	
	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	@Override
	public String toString() {
		return "masterId=" + masterId + ", id=" + id + ", speciesName="
				+ speciesName + ", age=" + age + ", gender=" + gender
				+ ", species=" + species;
	}
	
	
	
	
	
	
}
