/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.patel.employeedirectory.controller;

import com.patel.employeedirectory.model.Employee;
import com.patel.employeedirectory.repository.EmployeeRepository;
import com.patel.employeedirectory.request.EmployeeRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author Meet
 */
@Path("/employee")
public class EmployeeController {
    
    @Inject
    private EmployeeRepository empRepo;
    
    @GET
    @Produces("application/json")
    public List<Employee> getEmployeeList() {
        return empRepo.findAll();
    }
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Employee findEmployee(@PathParam("id") Long id){
        return empRepo.findById(id)
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }
    
    @GET
    @Path("firstname/{fname}")
    @Produces("application/json")
    public List<Employee> findUsingFirstName(@PathParam("fname") String firstName) {
        return empRepo.findByFirstName(firstName);
    }
    
    @GET
    @Path("lastname/{lname}")
    @Produces("application/json")
    public List<Employee> findUsingLastName(@PathParam("lname") String lastName) {
        return empRepo.findByLastName(lastName);
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Employee addNewEmployee(EmployeeRequest empReq){
        return empRepo.create(empReq);
    }
    
    @DELETE
    @Path("{id}")
    public void deleteEmployee(@PathParam("id") Long id){
        try{
            empRepo.delete(id);
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
    
    
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Employee updateEmployee(Employee emp){
         try{
             return empRepo.update(emp);
         } catch(Exception e) {
             throw new WebApplicationException(Response.Status.BAD_REQUEST);
         }
    }
}
