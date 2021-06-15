package ru.satird.communal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.satird.communal.domain.User;
import ru.satird.communal.repository.ServiceRepository;
import ru.satird.communal.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceRepository serviceRepository;

    List<ru.satird.communal.domain.Service> lowerExecutives = new ArrayList<>();
    List<List<User>> userList= new ArrayList<>();

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> findAllByAccountContaining(Optional<Integer> num, Pageable pageable) {
        return userRepository.findAllByAccountContaining(num, pageable);
    }

    @Override
    public Page<User> findAllByService(ru.satird.communal.domain.Service service, Pageable pageable) {
        return userRepository.findAllByService(service, pageable);
    }

    @Override
    public List<ru.satird.communal.domain.Service> findAllByServiceWithChild(ru.satird.communal.domain.Service service) {
        return serviceRepository.findUsersByService(service);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Page<User> getUsers(ru.satird.communal.domain.Service id, Pageable paging) {
        List<ru.satird.communal.domain.Service> userPage;
        userList.clear();
        userPage = findAllByServiceWithChild(id);
        lowerExecutives = userPage;
        userList.add(lowerExecutives.get(0).getUsers());
        fetchChildren(id);
        List<User> flat = userList.stream().flatMap(List::stream).collect(Collectors.toList());
        int start = (int)paging.getOffset();
        int end = Math.min((start + paging.getPageSize()), flat.size());
        return new PageImpl<>(flat.subList(start, end), paging, flat.size());
    }

    private void fetchChildren(ru.satird.communal.domain.Service executive) {
        if(!executive.getChildren().isEmpty()) {
            for(ru.satird.communal.domain.Service lowerExecutive : executive.getChildren()) {
                userList.add(lowerExecutive.getUsers());
                fetchChildren(lowerExecutive);
            }
        }
    }

}
