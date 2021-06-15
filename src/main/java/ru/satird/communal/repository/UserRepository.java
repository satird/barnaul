package ru.satird.communal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.satird.communal.domain.Service;
import ru.satird.communal.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select c from User c where concat(c.account) like %?1%")
    Page<User> findAllByAccountContaining(Optional<Integer> account, Pageable pageable);
    Page<User> findAll(Pageable pageable);
    Page<User> findAllByService(Service service, Pageable pageable);

}
