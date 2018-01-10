package br.com.fatec.proximatrilha.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.fatec.proximatrilha.view.View;

@Entity
@Table(name = "AUTHORIZATION")
public class Authorization implements GrantedAuthority {

	private static final long serialVersionUID = -80306030346346038L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "AUTHORIZATION_ID")
	private Long id;
	
	@Column(name = "NAME", unique=true, length = 20, nullable = false)
	@JsonView({View.User.class})
	private String name;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setAuthority(String authority) {
		this.name = authority;
	}
	
	public String getAuthority() {
		return this.name;
	}
	
}
