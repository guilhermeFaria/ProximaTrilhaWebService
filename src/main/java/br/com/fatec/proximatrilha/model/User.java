package br.com.fatec.proximatrilha.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import br.com.fatec.proximatrilha.view.View;

@Entity
@Table(name="USER")
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "USER_ID")
	@JsonView({View.User.class, View.General.class})
	private Long id;
	
	@Column(name = "NAME", length = 255, nullable = false)
	@JsonView({View.User.class, View.General.class})
	private String name;
	
	@Column(name = "EMAIL", unique=true, length = 55, nullable = false)
	@JsonView({View.User.class, View.General.class})
	private String email;
	
	@Column(name="PASSWORD", length=255, nullable=false)
	@JsonView({View.Individual.class})
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name = "USER_AUTHORIZATION",
			joinColumns = { @JoinColumn(name = "USER_ID")},
			inverseJoinColumns = { @JoinColumn(name = "AUTHORIZATION_ID") })
	@JsonView({View.Individual.class})
	private Set<Authorization> authorizations;
	
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER, targetEntity=Trail.class)
	@JsonView({View.Individual.class})
	private Set<Trail> trails;
	
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

	public Set<Authorization> getAuthorizations() {
		return authorizations;
	}

	public void setAuthorizations(final Set<Authorization> authorizations) {
		this.authorizations = authorizations;
	}
	
	public Set<Trail> getTrails() {
		return trails;
	}
	
	public void setTrails(final Set<Trail> trails) {
		this.trails = trails;
	}

	public String getPassword() {
		return password;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorizations == null) ? 0 : authorizations.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((trails == null) ? 0 : trails.hashCode());
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
		User other = (User) obj;
		if (authorizations == null) {
			if (other.authorizations != null)
				return false;
		} else if (!authorizations.equals(other.authorizations))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (trails == null) {
			if (other.trails != null)
				return false;
		} else if (!trails.equals(other.trails))
			return false;
		return true;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorizations;
	}
	
	@JsonIgnore
	@Override
	public String getUsername() {
		return email;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
	
}