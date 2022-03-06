package com.demo.EmployeeStateMgmt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

	@Autowired
	EmployeeDao empDao;

	@Autowired
	private StateMachine<EmployeeStates, EmployeeEvents> stateMachine;

	@PostMapping
	public String addEmployee(@RequestBody Employee emp) {
		emp.setEmployeeState(EmployeeStates.ADDED);
		empDao.save(emp);
		stateMachine.start();
		stateMachine.sendEvent(EmployeeEvents.BEGIN_CHECK);
		return "Employee Added";
	}

	@GetMapping("/{employeeId}")
	public Optional<Employee> getEmployee(@PathVariable int employeeId) {
		return empDao.findById(employeeId);
	}

	@PatchMapping
	public String update(@RequestParam String event, @RequestParam int empId) {

		Employee emp = empDao.getById(empId);

		if (emp != null) {
			stateMachine.start();
			stateMachine.sendEvent(EmployeeEvents.valueOf(event));
			emp.setEmployeeState(stateMachine.getState().getId());
			empDao.save(emp);
			return "Employee state updated to " + stateMachine.getState().getId();
		}
		return "No Employee found";
	}

}
