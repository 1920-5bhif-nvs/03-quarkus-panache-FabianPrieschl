package at.htl.quickstart.business;

import at.htl.quickstart.model.CrewMember;
import at.htl.quickstart.model.Movie;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CrewMemberRepository implements PanacheRepository<CrewMember> {

    @Transactional
    public CrewMember save(CrewMember crewMember) {
        this.persistAndFlush(crewMember);
        return this.findById(crewMember.getId());
    }

    public List<CrewMember> getAll() {
        return this.listAll();
    }
}
