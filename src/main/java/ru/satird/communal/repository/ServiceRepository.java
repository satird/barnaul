package ru.satird.communal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.satird.communal.domain.Service;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

    @Query(
            value = "WITH RECURSIVE service(id, name, parent) AS ("
                    + "   SELECT id, name, parent "
                    + "   FROM service "
                    + "   WHERE id = :service "
                    + "   UNION ALL "
                    + "   SELECT p.id, p.name, p.parent "
                    + "   FROM service p "
                    + "   JOIN service "
                    + "   ON service.id = p.parent "
                    + " )"
                    + "SELECT * from service ORDER BY id"
            , nativeQuery = true)
    List<Service> findUsersByService(Service service);
}
