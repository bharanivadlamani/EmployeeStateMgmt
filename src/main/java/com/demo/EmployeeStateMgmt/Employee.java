package com.demo.EmployeeStateMgmt;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Employee {

	@Id
	@GeneratedValue
	private int employeeId;

	private String name;

	private int age;

	private String contractInfo;

	private String state;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getContractInfo() {
		return contractInfo;
	}

	public void setContractInfo(String contractInfo) {
		this.contractInfo = contractInfo;
	}

	public Employee() {

	}

	public Employee(String name, int age, String contractInfo, EmployeeStates es) {
		this.name = name;
		this.age = age;
		this.contractInfo = contractInfo;
		this.setEmployeeState(es);
	}
	
	public EmployeeStates getEmployeeState() {
		return EmployeeStates.valueOf(this.state);
	}

	public void setEmployeeState(EmployeeStates s) {
		this.state = s.name();
	}


}