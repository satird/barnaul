package ru.satird.communal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.satird.communal.domain.Service;
import ru.satird.communal.domain.User;
import ru.satird.communal.service.ServiceService;
import ru.satird.communal.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    ServiceService services;

    @Autowired
    UserService users;

    @GetMapping(value = "/users")
    public List<User> usersList(
            @RequestParam(required = false, defaultValue = "") Optional<Integer> account,
            @RequestParam(required = false, defaultValue = "") Optional<String> order,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = Integer.MAX_VALUE + "") Integer size
    ) {
        String sort;
        if ("desc".equals(order.get()))
            sort = "desc";
        else
            sort = "asc";

        List<User> list;
        Pageable paging = PageRequest.of(page, size, Sort.Direction.fromString(sort), "account");
        Page<User> userPage;

        if (account.isPresent())
            userPage = users.findAllByAccountContaining(account, paging);
        else
            userPage = users.findAll(paging);

        list = userPage.getContent();
        return list;
    }

    @GetMapping(value = "/users/{id}")
    public List<User> usersInService(
            @PathVariable Service id,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = Integer.MAX_VALUE + "") Integer size
    ) {
        List<User> list;
        Pageable paging = PageRequest.of(page, size);
        Page<User> userPage;

        userPage = users.findAllByService(id, paging);
        list = userPage.getContent();
        return list;
    }

    @GetMapping(value = "/users/all/{id}")
    public List<User> usersInAllService(
            @PathVariable Service id,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = Integer.MAX_VALUE + "") Integer size
    ) {
        Pageable paging = PageRequest.of(page, size);
        final Page<User> pageU = users.getUsers(id, paging);
        return pageU.getContent();
    }

    @PostMapping(value = "/users/add")
    public User createUser(
            @RequestBody User user
    ) {
        return users.save(user);
    }

    @GetMapping(value = "/services")
    public List<Service> serviceList() {
        List<Service> list = services.findAll();
        return list;
    }

    @DeleteMapping(value = "/services/delete/{id}")
    public ResponseEntity deleteService(
            @PathVariable Service id,
            @RequestParam(required = false, defaultValue = "false") String force
    ) {
        Service service = services.findById(id);
        if (service.getChildren().isEmpty() && service.getUsers().isEmpty()) {
            services.deleteById(id);
            return new ResponseEntity("Вы удалили неиспользуемую услугу", new HttpHeaders(), HttpStatus.OK);
        } else {
            if (force.equals("true")) {
                services.deleteById(id);
                return new ResponseEntity("Вы использовали маркер \\\"force=true\\\", чтобы удалить используемую услугу", new HttpHeaders(), HttpStatus.OK);
            } else {
                return new ResponseEntity("Вы пытаетесь удалить услугу, у которой есть ссылка на дочернюю или на других пользователей \nЕсли вы действительно хотите удалить эту услугу - добавьте атрибут \"force=true\"", new HttpHeaders(), HttpStatus.FORBIDDEN);
            }
        }
    }

}
