package at.htl.quickstart.business;

import at.htl.quickstart.model.Movie;
import at.htl.quickstart.model.Rating;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class MovieRepository implements PanacheRepository<Movie> {

    @Inject
    EntityManager em;

    @Transactional
    public Movie save(Movie movie) {
        this.persistAndFlush(movie);
        return this.findById(movie.getId());
    }

    public List<Movie> getAll() {
        return this.listAll();
    }

    public Movie getById(long id) {
        EntityGraph eg = em.getEntityGraph("source-entity-graph");
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", eg);
        return em.find(Movie.class, id, properties);
    }
}
