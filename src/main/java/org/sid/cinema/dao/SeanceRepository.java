package org.sid.cinema.dao;

import org.sid.cinema.entities.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SeanceRepository extends JpaRepository<Seance,Long> {

}
