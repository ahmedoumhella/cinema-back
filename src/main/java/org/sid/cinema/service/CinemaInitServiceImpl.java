package org.sid.cinema.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.sid.cinema.dao.*;
import org.sid.cinema.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService{
	

	@Autowired
	private VilleRepository villeRepository;
	
	@Autowired
	private SalleRepository salleRepository;
	
	@Autowired
	private CinemaRepository cinemaRepository;
	
	@Autowired
	private PlaceRepository placeRepository;
	
	@Autowired
	private SeanceRepository seanceRepository;
	
	@Autowired
	private FilmRepository filmRepository;
	
	@Autowired
	private ProjectionRepository projectionRepository;
	
	@Autowired
	private CategorieRepository categorieRepository;
	
	@Autowired
	private TicketRepository TicketRepository;
	
	
	
	
	
	
	
	
	
	@Override
	public void initVilles() {
		
Stream.of("Casa","Marrakech","Rabat","Tanger").forEach(nameVille->{
	Ville ville=new Ville();
	ville.setName(nameVille);
	villeRepository.save(ville);
	
});
		

	}

	@Override
	public void initCinemas() {
		villeRepository.findAll().forEach(v->{
		Stream.of("MegaRama","C2","C3","C4").forEach(nameCinema->{
		Cinema cinema=new Cinema();
		cinema.setName(nameCinema);
		cinema.setNombreSalles(3+(int)(Math.random()*7));
		cinema.setVille(v);	
		cinemaRepository.save(cinema);

		});
		});
	}

	@Override
	public void initSalles() {
    cinemaRepository.findAll().forEach(cinema->{
    	for(int i=0;i<cinema.getNombreSalles();i++ )
    	{
    		Salle salle=new Salle();
    		salle.setName("Salle"+ (i+1));
    		salle.setCinema(cinema);
    		salle.setNombrePlaces(15+(int)(Math.random()*20));
    		salleRepository.save(salle);
    	}
    });		
	}

	@Override
	public void initPlaces() {
		salleRepository.findAll().forEach(salle->{
			for(int i=0;i<salle.getNombrePlaces();i++)
			{
			Place place=new Place();
			place.setNumero(i+1);
			place.setSalle(salle);
			placeRepository.save(place);
			}
			
		});
	}

	@Override
	public void initSeances() {
		DateFormat dateFormat=new SimpleDateFormat("HH:mm");
		Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(s->{
			Seance seance=new Seance();
			try {
				seance.setHeureDebut(dateFormat.parse(s));
				seanceRepository.save(seance);
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
		});
	}

	@Override
	public void initCategories() {
	
Stream.of("Histoira","Drama","Action").forEach(c->{
	Categorie categorie=new Categorie();
	
	categorie.setName(c);;
	categorieRepository.save(categorie);
	
	
});		
	}

	@Override
	public void initFilms() {
		double[] durees= new double[] {1,5,3,2,1,4};
        List<Categorie> categories=categorieRepository.findAll();

	 Stream.of("film1","film2","film3","film4","film5","film6").forEach(f->{
    	 Film film =new Film() ;
    	 film.setTitre(f);
    	 film.setDuree(durees[new Random().nextInt(durees.length)]);
    	 film.setPhoto(f+".jfif");
    	 film.setCategorie(categories.get(new Random().nextInt(categories.size())));
    	 filmRepository.save(film);
     });	
	}

	@Override
	public void initProjections() {
		villeRepository.findAll().forEach(ville->{
			ville.getCinemas().forEach(c->{
				c.getSalles().forEach(salle->{	
					filmRepository.findAll().forEach(f->{
						seanceRepository.findAll().forEach(seance->{
	     Projection projection =new Projection();
	     projection.setDate(new Date());
	     projection.setFilm(f);
	     projection.setPrix(40);
	     projection.setSalle(salle);
		 projection.setSeance(seance);
		 projectionRepository.save(projection);
							});
						});
					});
				});
			});	
		}
	

	@Override
	public void initTickets() {
		projectionRepository.findAll().forEach(p->{
		p.getSalle().getPlaces().forEach(place->{
			Ticket ticket=new Ticket();
			ticket.setPlace(place);
			ticket.setPrix(p.getPrix());
			ticket.setReserve(false);
			ticket.setProjection(p);
			
			
			TicketRepository.save(ticket);
		});	
			
		});
		
	
}
}