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
@Table(name="TRAIL")
public class Trail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRAIL_ID")
	@JsonView(View.Trail.class)
	private Long id;
	
	@Column(name="NAME", length=255, nullable=false)
	@JsonView(View.Trail.class)
	private String name;
	
	@Column(name="DESCRIPTION", length=255, nullable=false)
	@JsonView(View.Trail.class)
	private String description;
	
} 