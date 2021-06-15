package ru.satird.communal.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.satird.communal.domain.Service;
import ru.satird.communal.repository.ServiceRepository;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {
    @Autowired
    ServiceRepository serviceRepository;

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public void deleteById(Service id) {
        serviceRepository.delete(id);
    }

    @Override
    public Service findById(Service service) {
        return serviceRepository.findById(service.getId()).get();
    }
}
