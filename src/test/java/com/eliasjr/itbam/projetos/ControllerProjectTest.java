package com.eliasjr.itbam.projetos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.eliasjr.itbam.projetos.model.ProjectEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerProjectTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void testGetAll() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/projects", HttpMethod.GET, entity,
				String.class);

		assertNotNull(response.getBody());
	}

	@Test
	public void testGetById() {
		ProjectEntity project = restTemplate.getForObject(getRootUrl() + "/projects/1", ProjectEntity.class);
		System.out.println(project.getNome());
		assertNotNull(project);
	}

	@Test
	public void testCreate() {
		ProjectEntity entity = new ProjectEntity();
		entity.setNome("Projeto Teste");
		entity.setDataInicio("01/01/2009");
		entity.setDataFim("01/01/2010");

		ResponseEntity<ProjectEntity> postResponse = restTemplate.postForEntity(getRootUrl() + "/projects", entity,
				ProjectEntity.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateEmployee() {
		int id = 1;
		ProjectEntity entity = restTemplate.getForObject(getRootUrl() + "/projects/" + id, ProjectEntity.class);
		entity.setNome("nome 2");
		entity.setValor(2500);

		restTemplate.put(getRootUrl() + "/projects/" + id, entity);

		ProjectEntity updatedEmployee = restTemplate.getForObject(getRootUrl() + "/projects/" + id,
				ProjectEntity.class);
		assertNotNull(updatedEmployee);
	}

	@Test
	public void testDelete() {
		int id = 2;
		ProjectEntity entity = restTemplate.getForObject(getRootUrl() + "/projects/" + id, ProjectEntity.class);
		assertNotNull(entity);

		restTemplate.delete(getRootUrl() + "/projects/" + id);

		try {
			entity = restTemplate.getForObject(getRootUrl() + "/projects/" + id, ProjectEntity.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
