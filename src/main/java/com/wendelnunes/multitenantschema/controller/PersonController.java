package com.wendelnunes.multitenantschema.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wendelnunes.multitenantschema.model.Person;
import com.wendelnunes.multitenantschema.repository.PersonRepository;

@RestController
@RequestMapping("person")
public class PersonController {

	private final PersonRepository repository;

	public PersonController(PersonRepository repository) {
		this.repository = repository;
	}

	@PostMapping
	public Person persist(@RequestParam("name") String name) {
		Person person = new Person();
		person.setName(name);
		return this.repository.save(person);
	}

	@PutMapping("{id}")
	public Person update(@PathVariable("id") Long id, @RequestParam("name") String name) {
		Person person = this.repository.findById(id).orElseThrow(NoResultException::new);
		person.setName(name);
		return this.repository.save(person);
	}

	@GetMapping
	public List<Person> get() {
		return this.repository.findAll();
	}

	@GetMapping("{id}")
	public Optional<Person> get(@PathVariable("id") Long id) {
		return this.repository.findById(id);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Long id) {
		this.repository.deleteById(id);
	}
}