package ru.satird.communal.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.satird.communal.domain.Service;
import ru.satird.communal.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<User> findAll(Pageable pageable);

    Page<User> findAllByAccountContaining(Optional<Integer> num, Pageable pageable);

    Page<User> findAllByService(Service service, Pageable pageable);

    List<Service> findAllByServiceWithChild(Service id);

    User save(User user);

    Page<User> getUsers(Service id, Pageable paging);
}
