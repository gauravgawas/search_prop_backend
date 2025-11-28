package com.gaurav.searchProp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaurav.searchProp.model.GeometryData;
import com.gaurav.searchProp.repository.GeometryRepository;

@Service
public class GeometryService {

	private final GeometryRepository geomRepo;
	private final ObjectMapper objectMapper = new ObjectMapper();
	public GeometryService(GeometryRepository geomRepo) {
		this.geomRepo=geomRepo;
	}
	
	public Map<String, Object> saveGeom(GeometryData data){
		Map<String, Object> res = new HashMap<>();
		try {
			 List<GeometryData> existing = geomRepo.findByUsername(data.getUsername());

		        if (existing.size()!=0) {
		            GeometryData existingData = existing.get(0);
		            existingData.setGeom(data.getGeom()); // update geometry
		            geomRepo.save(existingData);
		            res.put("Status", "OK");
		            res.put("Message", "Data updated successfully");
		        } else {
		            geomRepo.save(data);
		            res.put("Status", "OK");
		            res.put("Message", "Data saved successfully");
		        }
		}catch(Exception e) {
			System.out.println(e);
			res.put("Status", "FAIL");
			res.put("Message", "Failed to save data");
		}
		
		return res;
		
	}
	public Map<String, Object> fetchAllGeom(){
		Map<String, Object> res = new HashMap<>();
		try {
			List<GeometryData> data=geomRepo.findAll();
			res.put("Status", "OK");
			res.put("Data", data);
		}catch(Exception e) {
		
			res.put("Status", "FAIL");
			res.put("Message", "Failed to fetch data");
		}
		
		return res;
		
	}
	public Map<String, Object> fetchMyGeom(GeometryData geomData){
		Map<String, Object> res = new HashMap<>();
		try {
			List<GeometryData> data=geomRepo.findByUsername(geomData.getUsername());
			res.put("Status", "OK");
			res.put("Data", data);
		}catch(Exception e) {
			res.put("Status", "FAIL");
			res.put("Message", "Failed to fetch data");
		}
		
		return res;
		
	}

	public Map<String, Object> deleteMyGeom(GeometryData geomData) {
		Map<String, Object> res = new HashMap<>();
		try {
			geomRepo.deleteAllByUsername(geomData.getUsername());
			res.put("Status", "OK");
			res.put("Message","Data deleted successfully");
		}catch(Exception e) {
			res.put("Status", "FAIL");
			res.put("Message", "Failed to delete data");
		}
		
		return res;
	}

	public Map<String, Object> deleteAllGeom() {
		Map<String, Object> res = new HashMap<>();
		try {
			geomRepo.deleteAll();
			res.put("Status", "OK");
			res.put("Message","All Data deleted successfully");
		}catch(Exception e) {
			res.put("Status", "FAIL");
			res.put("Message", "Failed to delete all data");
		}
		
		return res;
	}
}
