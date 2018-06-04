package br.com.fatec.proximatrilha.model;

import java.util.Set;

import javax.persistence.CascadeType;
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
@Table(name="TRAIL")
public class Trail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRAIL_ID")
	@JsonView({View.Trail.class, View.General.class})
	private Long id;
	
	@Column(name="NAME", length=255, nullable=false)
	@JsonView({View.Trail.class, View.General.class})
	private String name;
	
	@Column(name="DESCRIPTION", length=255, nullable=false)
	@JsonView({View.Trail.class, View.General.class})
	private String description;
	
	@Column(name="START_POINT", length=12, nullable=false)
	@JsonView({View.Trail.class, View.General.class})
	private String startPoint;
	
	@Column(name="END_POINT", length=12, nullable=false)
	@JsonView({View.Trail.class, View.General.class})
	private String endPoint;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="trail", targetEntity=TrailDot.class, cascade=CascadeType.ALL)
	@JsonView({View.Trail.class, View.Individual.class})
	private Set<TrailDot> trailDots;
	
	@ManyToOne
	@JoinColumn(name="USER_ID", nullable=false)
	@JsonIgnore
	private User user;
	
//	public Trail() {
//		
//	}
	
//	public Trail(final Long id, final String name, final String description) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.description = description;
//		this.trailDots = new HashSet<TrailDot>();
//	}
//	
//	public Trail(final Long id, final String name, final String description, final Set<TrailDot> trailDots) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.description = description;
//		this.trailDots = trailDots;
//	}

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
	
	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public Set<TrailDot> getTrailDots() {
		return trailDots;
	}

	public void setTrailDots(final Set<TrailDot> trailDots) {
		this.trailDots = trailDots;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

} 