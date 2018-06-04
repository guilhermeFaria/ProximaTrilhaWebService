package br.com.fatec.proximatrilha.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import br.com.fatec.proximatrilha.view.View;

@Entity
@Table(name="MULTIMEDIA")
public class Multimedia {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="MULTIMEDIA_ID")
	@JsonView({View.Multimedia.class, View.General.class})
	private Long id;
	
	@Column(name="URL", length=255, nullable=false)
	@JsonView({View.Multimedia.class, View.General.class})
	private String url;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_DT")
	@JsonView({View.Multimedia.class, View.General.class})
	private Date insertDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column (name = "UPDATE_DT")
	@JsonView({View.Multimedia.class, View.General.class})
	private Date updateDate;
	
	@ManyToOne
	@JoinColumn(name="TRAIL_DOT_ID", nullable=false)
	@JsonIgnore
	private TrailDot trailDot;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(final Date insertDate) {
		this.insertDate = insertDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(final Date updateDate) {
		this.updateDate = updateDate;
	}

	public TrailDot getTrailDot() {
		return trailDot;
	}

	public void setTrailDot(final TrailDot trailDot) {
		this.trailDot = trailDot;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((insertDate == null) ? 0 : insertDate.hashCode());
		result = prime * result + ((trailDot == null) ? 0 : trailDot.hashCode());
		result = prime * result + ((updateDate == null) ? 0 : updateDate.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Multimedia other = (Multimedia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (insertDate == null) {
			if (other.insertDate != null)
				return false;
		} else if (!insertDate.equals(other.insertDate))
			return false;
		if (trailDot == null) {
			if (other.trailDot != null)
				return false;
		} else if (!trailDot.equals(other.trailDot))
			return false;
		if (updateDate == null) {
			if (other.updateDate != null)
				return false;
		} else if (!updateDate.equals(other.updateDate))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
}