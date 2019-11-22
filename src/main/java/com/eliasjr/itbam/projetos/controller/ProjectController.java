package com.eliasjr.itbam.projetos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eliasjr.itbam.projetos.exception.RecordNotFoundException;
import com.eliasjr.itbam.projetos.model.ProjectEntity;
import com.eliasjr.itbam.projetos.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	ProjectService service;

	@GetMapping
	public ResponseEntity<List<ProjectEntity>> getAllEmployees() {
		List<ProjectEntity> list = service.getAllProjects();

		return new ResponseEntity<List<ProjectEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProjectEntity> getProjectById(@PathVariable("id") Long id) throws RecordNotFoundException {
		ProjectEntity entity = service.getProjectById(id);

		return new ResponseEntity<ProjectEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ProjectEntity> createOrUpdateProject(ProjectEntity project) throws RecordNotFoundException {
		ProjectEntity updated = service.createOrUpdateProject(project);
		return new ResponseEntity<ProjectEntity>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public HttpStatus deleteEmployeeById(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.deleteProjectById(id);
		return HttpStatus.FORBIDDEN;
	}

}
