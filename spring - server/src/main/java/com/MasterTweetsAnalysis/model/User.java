package com.MasterTweetsAnalysis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "TweetsAnalysis")
public class User implements Serializable {

	private String status;
	@javax.persistence.Id
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column( name = "id")
	private long id;
	@JsonIgnore
	@NotNull
	@Column ( name = "username")
	private String username;
	@JsonIgnore
	@Column ( name = "password")
	private	String password;

	public User () {};

	public User (String username, String password) {
		this.username = username;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	@JsonIgnore
	public String getUsername() {
		return username;
	}
	@JsonProperty
	public void setUsername(String username) {
		this.username = username;
	}
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public User(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User{" +
				"status='" + status + '\'' +
				", id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}

	@Override

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		if (!Objects.equals(this.username, other.username)) {
			return false;
		}
		if (!Objects.equals(this.password, other.password)) {
			return false;
		}
		return true;
	}

}
