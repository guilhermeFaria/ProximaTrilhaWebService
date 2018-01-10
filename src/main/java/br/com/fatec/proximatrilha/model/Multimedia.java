package br.com.fatec.proximatrilha.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.fatec.proximatrilha.view.View;

@Entity
@Table(name="MULTIMEDIA")
public class Multimedia {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="MULTIMEDIA_ID")
	@JsonView(View.Trail.class)
	private Long id;
	
	@Column(name="URL", length=255, nullable=false)
	@JsonView(View.Trail.class)
	private String url;

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
	
}