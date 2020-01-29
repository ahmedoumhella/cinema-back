package org.sid.cinema.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
//les annotations lombook
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Film implements Serializable{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titre ;
	private String description ;
	private String realisateur ;
	private Date dateSortie ;
	private String photo;
	private double duree ;
	@OneToMany(mappedBy = "film")
	private Collection<Projection> projections;
	
	@ManyToOne
	private Categorie categorie;
}
