package com.gaurav.searchProp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaurav.searchProp.model.GeometryData;


public interface GeometryRepository  extends JpaRepository<GeometryData, Long>  {

	void deleteAllByUsername(String username);

	List<GeometryData> findByUsername(String username);
}
