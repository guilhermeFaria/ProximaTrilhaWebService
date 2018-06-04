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
/**
 * Class Model of Authorization
 * 
 * @author Guilherme Faria
 *
 */
@Entity
@Table(name = "AUTHORIZATION")
public class Authorization implements GrantedAuthority {

	private static final long serialVersionUID = -80306030346346038L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "AUTHORIZATION_ID")
	@JsonView({View.User.class, View.General.class})
	private Long id;
	
	@Column(name = "AUTHORITY", unique=true, length = 20, nullable = false)
	@JsonView({View.User.class, View.General.class})
	private String authority;
	
	public Long getId() {
		return id;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(final String authority) {
		this.authority = authority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authority == null) ? 0 : authority.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Authorization other = (Authorization) obj;
		if (authority == null) {
			if (other.authority != null)
				return false;
		} else if (!authority.equals(other.authority))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
