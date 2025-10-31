package com.gaurav.searchProp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.searchProp.model.GeometryData;
import com.gaurav.searchProp.security.JwtUtil;
import com.gaurav.searchProp.service.GeometryService;

@RestController
@RequestMapping("/api/geometry")
@CrossOrigin(origins = "*")
public class GeometryController {

	private final GeometryService geomService;
	private final JwtUtil jwt;

	public GeometryController(GeometryService geomService, JwtUtil jwt) {
		this.geomService = geomService;
		this.jwt = jwt;
	}

	public Boolean validateToken(String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return false;
		}
		String token = authHeader.substring(7);
		if (!jwt.validateToken(token)) {
			return false;
		}
		return true;
		
	}
	@PostMapping("/saveGeom")
	public Map<String, Object> saveGeometry(@RequestHeader("Authorization") String authHeader,
			@RequestBody GeometryData data) {
		Map<String, Object> response = new HashMap<>();
		if (!validateToken(authHeader)) {
			response.put("Status", "FAIL");
			response.put("Message", "Invalid token");
			return response;
		}

		return geomService.saveGeom(data);

	}

	@PostMapping("/getMyGeom")
	public Map<String, Object> getMyGeometry(@RequestHeader("Authorization") String authHeader,@RequestBody GeometryData data) {
		Map<String, Object> response = new HashMap<>();
		if (!validateToken(authHeader)) {
			response.put("Status", "FAIL");
			response.put("Message", "Invalid token");
			return response;
		}
		return geomService.fetchMyGeom(data);

	}

	@PostMapping("/deleteMyGeom")
	public Map<String, Object> deleteMyGeometry(@RequestHeader("Authorization") String authHeader,@RequestBody GeometryData data) {
		Map<String, Object> response = new HashMap<>();
		if (!validateToken(authHeader)) {
			response.put("Status", "FAIL");
			response.put("Message", "Invalid token");
			return response;
		}
		return geomService.deleteMyGeom(data);

	}

	@GetMapping("/getAllGeom")
	public Map<String, Object> getAllGeometry(@RequestHeader("Authorization") String authHeader) {
		Map<String, Object> response = new HashMap<>();
		if (!validateToken(authHeader)) {
			response.put("Status", "FAIL");
			response.put("Message", "Invalid token");
			return response;
		}
		return geomService.fetchAllGeom();
	}
}
