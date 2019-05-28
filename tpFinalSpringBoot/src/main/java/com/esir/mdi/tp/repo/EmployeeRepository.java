package com.esir.mdi.tp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esir.mdi.tp.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

}
