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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.fatec.proximatrilha.view.View;

@Entity
@Table(name="TRAIL_DOT")
public class TrailDot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRAIL_DOT_ID")
	@JsonView(View.Trail.class)
	private Long id;
	
	@Column(name="NAME", length=255, nullable=false)
	@JsonView(View.Trail.class)
	private String name;
	
	@Column(name="DESCRIPTION", length=255, nullable=false)
	@JsonView(View.Trail.class)
	private String description;

	
	@Column(name="LATITUDE", length=12, nullable=false)
	@JsonView(View.Trail.class)
	private String latitude;
	
	@Column(name="LONGITUDE", length=12, nullable=false)
	@JsonView(View.Trail.class)
	private String longitude;
	
	//TODO Add the field User and Trail
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="TRAIL_DOT_COMMENT",
			joinColumns = { @JoinColumn(name="TRAIL_DOT_ID")},
			inverseJoinColumns = { @JoinColumn(name="USER_ID"),
					@JoinColumn(name="COMMENT_ID")})
	private List<Comment> comments;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MULTIMEDIA_ID")
	private Multimedia multimedia;
	
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
	
	public List<Comment> getComments() {
		return comments;
	}
	
	public void setComments(final List<Comment> comments) {
		this.comments = comments;
	}

	public Multimedia getMultimedia() {
		return multimedia;
	}

	public void setMultimedia(Multimedia multimedia) {
		this.multimedia = multimedia;
	}

}