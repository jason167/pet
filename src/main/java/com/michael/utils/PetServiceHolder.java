package com.michael.utils;

import org.springframework.stereotype.Service;

@Service
public class PetServiceHolder extends PetServiceAware{

	@Override
	public PetMethod getServiceMethod(String serviceType, String version){
		return petServiceCache.get(mangleName(serviceType, version));
	}
	

}
