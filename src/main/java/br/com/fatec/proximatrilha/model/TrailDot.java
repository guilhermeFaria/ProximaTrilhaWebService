package br.com.fatec.proximatrilha.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import br.com.fatec.proximatrilha.view.View;

@Entity
@Table(name="TRAIL_DOT")
public class TrailDot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRAIL_DOT_ID")
	@JsonView({View.TrailDot.class, View.Trail.class, View.General.class})
	private Long id;
	
	@Column(name="NAME", length=255, nullable=false)
	@JsonView({View.TrailDot.class, View.Trail.class, View.General.class})
	private String name;
	
	@Column(name="DESCRIPTION", length=255, nullable=false)
	@JsonView({View.TrailDot.class, View.Trail.class, View.General.class})
	private String description;

	@Column(name="LATITUDE", length=12, nullable=false)
	@JsonView({View.TrailDot.class, View.Trail.class, View.General.class})
	private String latitude;
	
	@Column(name="LONGITUDE", length=12, nullable=false)
	@JsonView({View.TrailDot.class, View.Trail.class, View.General.class})
	private String longitude;
	
	@ManyToOne
	@JoinColumn(name="TRAIL_ID", nullable=false)
	@JsonIgnore
	private Trail trail;

	@OneToMany(mappedBy="trailDot", fetch=FetchType.EAGER, targetEntity=Comment.class)
	@JsonView({View.TrailDot.class, View.Trail.class, View.General.class})
	private Set<Comment> comments;
	
	@OneToMany(mappedBy="trailDot", fetch = FetchType.EAGER, targetEntity=Multimedia.class)
	@JsonView({View.TrailDot.class, View.Trail.class, View.General.class})
	private Set<Multimedia> multimedias;
	
	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(final String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(final String longitude) {
		this.longitude = longitude;
	}
	
	public Set<Comment> getComments() {
		return comments;
	}
	
	public void setComments(final Set<Comment> comments) {
		this.comments = comments;
	}
	
	public Set<Multimedia> getMultimedias() {
		return multimedias;
	}

	public void setMultimedias(Set<Multimedia> multimedias) {
		this.multimedias = multimedias;
	}

	public Trail getTrail() {
		return trail;
	}

	public void setTrail(final Trail trail) {
		this.trail= trail;
	}
}