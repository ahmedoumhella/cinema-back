package org.sid.cinema.dao;

import org.sid.cinema.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategorieRepository extends JpaRepository<Categorie,Long> {

}