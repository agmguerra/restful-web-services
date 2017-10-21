package com.in28minutes.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



@RestController
public class UserResource {

	@Autowired
	private UserDaoService service;
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		List<User> users = service.findAll();
		if (users == null || users.size() == 0) {
			throw new UserNotFoundException("Nenhum usuario encontrado");
		}
		
		return users;
	}
	
	@GetMapping("/users/{id}")
	public Resource<User> retreiveUser(@PathVariable int id) {
		User user = service.findOne(id); 
		if (user == null) {
			throw new UserNotFoundException(String.format("id %s not found", id));
		}
		
		//Colocar o link para recuperar todos os usuários
		//retrieveAllUsers seguindo o padrão HATEOAS
		Resource<User> resource = new Resource<User>(user);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		resource.add(linkTo.withRel("all-users"));
		
		
		return resource;	
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		
		URI location = ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand(savedUser.getId()).toUri();
		
		ResponseEntity<Object> ret = ResponseEntity.created(location).build();

		
		return ret;
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = service.deleteById(id);
		
		if (user == null) {
			throw new UserNotFoundException(String.format("id %s not found", id));
		}
		
	}

	
}
