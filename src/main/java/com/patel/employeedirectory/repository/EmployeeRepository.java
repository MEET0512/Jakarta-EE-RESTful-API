/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.patel.employeedirectory.repository;

import com.patel.employeedirectory.model.Employee;
import com.patel.employeedirectory.request.EmployeeRequest;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Meet
 */
@ApplicationScoped
@Transactional
public class EmployeeRepository {
    
    @PersistenceContext
    private EntityManager em;
    
    //List all the emploeyees
    public List<Employee> findAll() {
        return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
    }
    
    //Get the perticular employer by employee id
    public Optional<Employee> findById(Long id){
        return Optional.ofNullable(em.find(Employee.class, id));
    }
    
    //List of all employees who has same first name
    public List<Employee> findByFirstName(String firstName) {
        return em.createQuery("SELECT e FROM Employee e WHERE e.firstName=:fName", Employee.class)
                                                    .setParameter("fName", firstName).getResultList();
    }
    
    //List of all employees who has same last name
    public List<Employee> findByLastName(String lastName) {
        return em.createQuery("SELECT e FROM Employee e WHERE e.lastName=:lName", Employee.class)
                                                    .setParameter("lName", lastName).getResultList();
    }
    
    //Update the information of exist employee
    public Employee update(Employee emp) {
        return em.merge(emp);
    }
    
    //Create new employee
    public Employee create(EmployeeRequest empReq) {
        Employee newEmp = Employee.builder()
                                    .firstName(empReq.getFirstName())
                                    .lastName(empReq.getLastName())
                                     .email(empReq.getEmail())
                                     .phone(empReq.getPhone())
                                     .department(empReq.getDepartment())
                                    .build();
        em.persist(newEmp);
        return newEmp;
    }
    
    //Delete exist employee
    public void delete(Long id) {
        Employee emp = this.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Employee id: "+id));
        em.remove(emp);
    }
}
