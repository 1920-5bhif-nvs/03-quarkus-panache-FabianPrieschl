package at.htl.quickstart.business;

import at.htl.quickstart.model.Rating;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class RatingRepository implements PanacheRepository<Rating> {

    @Transactional
    public Rating save(Rating rating) {
        this.persistAndFlush(rating);
        return this.findById(rating.getId());
    }

    public List<Rating> getAll() {
        return this.listAll();
    }
}
