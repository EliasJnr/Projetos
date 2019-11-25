package com.eliasjr.itbam.projetos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eliasjr.itbam.projetos.exception.RecordNotFoundException;
import com.eliasjr.itbam.projetos.model.ProjectEntity;
import com.eliasjr.itbam.projetos.service.ProjectService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/projeto")
public class ProjectController {
	@Autowired
	private ProjectService service;

	@GetMapping("/projects")
	public List<ProjectEntity> getAll() {
		return service.getAllProjects();
	}

	@GetMapping("/projects/{id}")
	public ResponseEntity<ProjectEntity> getById(@PathVariable(value = "id") Long id)
			throws RecordNotFoundException {
		ProjectEntity obj = service.getProjectById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping("/projects")
	public ProjectEntity create(@Valid @RequestBody ProjectEntity entity) throws RecordNotFoundException {
		return service.createOrUpdateProject(entity);
	}

	@PutMapping("/projects/{id}")
	public ResponseEntity<ProjectEntity> update(@PathVariable(value = "id") Long id,
			@Valid @RequestBody ProjectEntity newEntity) throws RecordNotFoundException {
		final ProjectEntity updated = service.createOrUpdateProject(newEntity);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/projects/{id}")
	public Map<String, Boolean> delete(@PathVariable(value = "id") Long id) throws RecordNotFoundException {
		service.deleteProjectById(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
