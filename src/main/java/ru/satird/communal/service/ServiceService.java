package ru.satird.communal.service;

import ru.satird.communal.domain.Service;

import java.util.List;

public interface ServiceService {
    List<Service> findAll();

    void deleteById(Service id);

    Service findById(Service service);
}
