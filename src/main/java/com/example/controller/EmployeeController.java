package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Employee;
import com.example.repository.es.EmployeeESRepo;
import com.example.service.EmployeeService;

@RestController
@Validated
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeESRepo employeeEsRepo;

	@RequestMapping("/getAll")
	public List<Employee> getEmployees() {
		/*List<Employee> employeesList = new ArrayList<Employee>();
		employeesList.add(new Employee(1, "lokesh", "gupta", "lokesh@gmail.com"));
		*/
		List<Employee> employeesList = employeeService.findAll();
		return employeesList;
	}

	@RequestMapping("/get/{id}")
	public Employee getEmployeeById(@PathVariable("id") Long id) {
		return employeeEsRepo.search(id);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Employee addEmployee(@RequestBody Employee employee) {
		employee = employeeService.create(employee);
		employeeEsRepo.insert(employee);
		return employee;
	}

}
