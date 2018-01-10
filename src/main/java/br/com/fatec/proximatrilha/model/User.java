package br.com.fatec.proximatrilha.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.fatec.proximatrilha.view.View;

@Entity
@Table(name="USER")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "USER_ID")
	@JsonView({View.User.class})
	private Long id;
	
	@Column(name = "NAME", length = 255, nullable = false)
	@JsonView({View.User.class})
	private String name;
	
	@Column(name = "EMAIL", unique=true, length = 55, nullable = false)
	@JsonView({View.User.class})
	private String email;
	
	@Column(name="PASSWORD", length=255, nullable=false)
	@JsonView(View.User.class)
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_AUTHORIZATION",
			joinColumns = { @JoinColumn(name = "USER_ID")},
			inverseJoinColumns = { @JoinColumn(name = "AUTHORIZATION_ID") })
	@JsonView(View.User.class)
	private List<Authorization> authorizations;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="TRAIL_USER",
			joinColumns = { @JoinColumn(name="USER_ID")},
			inverseJoinColumns = { @JoinColumn(name="TRAIL_ID")})
	@JsonView(View.User.class)
	private List<Trail> trails;
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public List<Authorization> getAutorizations() {
		return authorizations;
	}

	public void setAutorizations(final List<Authorization> authorizations) {
		this.authorizations = authorizations;
	}
	
	public List<Trail> getTrails() {
		return trails;
	}
	
	public void setTrails(final List<Trail> trails) {
		this.trails = trails;
	}
	
}