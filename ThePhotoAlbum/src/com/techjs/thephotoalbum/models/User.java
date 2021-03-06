package com.techjs.thephotoalbum.models;

import java.util.Date;

import com.techjs.thephotoalbum.utils.Gender;
/**
 * Represents a User Entity Model
 * @author Sachin Singh
 * */
public class User {
	private Long id;
	private String name;
	private String dob;
	private Gender gender;
	private String email;
	private String password;
	/**
	 * Default constructor for creating a User.
	 * */
	public User() {
		super();
	}
	
	public User(Long id, String name, String dob, Gender gender, String email, String password) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.email = email;
		this.password = password;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", dob=" + dob + ", gender=" + gender + ", email=" + email
				+ ", password=" + password + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User user = (User) obj;
			return (user.getId() == this.getId()) ? true : false;
		}
		return false;
	}
}
