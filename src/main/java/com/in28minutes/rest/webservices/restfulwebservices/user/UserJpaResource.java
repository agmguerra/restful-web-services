package com.in28minutes.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
public class UserJpaResource {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;

	
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		List<User> users = userRepository.findAll();
		if (users == null || users.size() == 0) {
			throw new UserNotFoundException("Nenhum usuario encontrado");
		}
		
		return users;
	}
	
	@GetMapping("/jpa/users/{id}")
	public Resource<User> retreiveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id); 
		if (!user.isPresent()) {
			throw new UserNotFoundException(String.format("id %s not found", id));
		}
		
		//Colocar o link para recuperar todos os usuários
		//retrieveAllUsers seguindo o padrão HATEOAS
		Resource<User> resource = new Resource<User>(user.get());
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		resource.add(linkTo.withRel("all-users"));
		
		
		return resource;	
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		
		URI location = ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand(savedUser.getId()).toUri();
		
		ResponseEntity<Object> ret = ResponseEntity.created(location).build();

		
		return ret;
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
				
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveAllPostsByUserId(@PathVariable Integer id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("Nenhum usuario encontrado");
		}
		
		return user.get().getPosts();
	}


	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable Integer id, @Valid @RequestBody Post post) {
		
		Optional<User> userOptional  = userRepository.findById(id);
		
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("Nenhum usuario encontrado");
		}
		
		User user = userOptional.get();
		
		post.setUser(user);
		post = postRepository.save(post);
		
		
		URI location = ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand(post.getId()).toUri();
		
		ResponseEntity<Object> ret = ResponseEntity.created(location).build();
	
		return ret;
	}

	
}
