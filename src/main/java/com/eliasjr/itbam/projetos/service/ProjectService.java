package com.eliasjr.itbam.projetos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eliasjr.itbam.projetos.exception.RecordNotFoundException;
import com.eliasjr.itbam.projetos.model.ProjectEntity;
import com.eliasjr.itbam.projetos.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	ProjectRepository repository;

	public List<ProjectEntity> getAllProjects() {
		List<ProjectEntity> projectsList = repository.findAll();

		if (projectsList.size() > 0) {
			return projectsList;
		} else {
			return new ArrayList<ProjectEntity>();
		}
	}

	public ProjectEntity getProjectById(Long id) throws RecordNotFoundException {
		Optional<ProjectEntity> project = repository.findById(id);

		if (project.isPresent()) {
			return project.get();
		} else {
			throw new RecordNotFoundException("Não existe registro de projeto para o ID fornecido");
		}
	}

	public ProjectEntity createOrUpdateProject(ProjectEntity entity) throws RecordNotFoundException {
		Optional<ProjectEntity> project = repository.findById(entity.getId());

		if (project.isPresent()) {
			ProjectEntity newEntity = project.get();
			newEntity.setNome(entity.getNome());
			newEntity.setDataInicio(entity.getDataInicio());
			newEntity.setDataFim(entity.getDataFim());
			newEntity.setValor(entity.getValor());

			newEntity = repository.save(newEntity);

			return newEntity;
		} else {
			entity = repository.save(entity);
			return entity;
		}
	}

	public void deleteProjectById(Long id) throws RecordNotFoundException {
		Optional<ProjectEntity> project = repository.findById(id);

		if (project.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException("Não existe registro de projeto para o ID fornecido");
		}
	}

}
