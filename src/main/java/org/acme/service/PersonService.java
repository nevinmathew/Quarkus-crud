package org.acme.service;

import java.util.logging.Logger;

import org.acme.model.Person;
import org.acme.repository.PersonRepository;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PersonService {

 @Inject
 PersonRepository personRepository;

@PostConstruct
public void init() {
	Logger logger = Logger.getLogger("org.acme.PersonService");
	logger.info("PersonRepository injected: {}"+ String.valueOf(personRepository != null));
}

 @GET
 @Produces(MediaType.APPLICATION_JSON)
 public Response getAllPersons() {
     return Response.ok(personRepository.listAll()).build();
 }

 @POST
 @Transactional
 @Produces(MediaType.APPLICATION_JSON)
 @Consumes(MediaType.APPLICATION_JSON)
 public Response savePerson(Person person) {
	 personRepository.persist(person);
     return Response.ok(person.getName() + " saved").build();
 }

 @PUT
 @Path("/{id}")
 @Transactional
 @Produces(MediaType.APPLICATION_JSON)
 @Consumes(MediaType.APPLICATION_JSON)
 public void updatePerson(@PathParam("id") Long id, Person updatedPerson) {
     Person existingPerson = personRepository.findById(id);
     if (existingPerson != null) {
         existingPerson.setName(updatedPerson.getName());
     } else {
         throw new NotFoundException("Could not find person with id " + id);
     }
 }

 @DELETE
 @Path("/{id}")
 @Transactional
 @Produces(MediaType.APPLICATION_JSON)
 public void deletePerson(@PathParam("id") Long id) {
     Person personToDelete = personRepository.findById(id);
     if (personToDelete != null) {
         personRepository.delete(personToDelete);
     } else {
         throw new NotFoundException("Could not find person with id " + id);
     }
 }
}
