/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tutorial.user.service.controller;

import com.tutorial.user.service.entity.User;
import com.tutorial.user.service.model.Bike;
import com.tutorial.user.service.model.Car;
import com.tutorial.user.service.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author USUARIO
 */

@RestController
@RequestMapping("/user")
public class UserController {
    
    
    @Autowired
    UserService userService;
    
    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        List<User> users=userService.getAll();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }
    
    
    @GetMapping("{id}")
    public ResponseEntity<User> getById(@PathVariable("id") int id){
        User user=userService.getUserById(id);
        if(user==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(user);
    }
    
    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user){
        User userNew=userService.save(user);
        if(userNew==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userNew);
    }
    
    @CircuitBreaker(name="carsCB",fallbackMethod="fallBackGetCars")
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable("userId") int userId){
        User user=userService.getUserById(userId);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        List<Car> cars=userService.getCars(userId);
        return ResponseEntity.ok(cars);
    }
    
    @CircuitBreaker(name="bikesCB",fallbackMethod="fallBackGetBikes")
    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBike(@PathVariable("userId") int userId){
        User user=userService.getUserById(userId);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        List<Bike> bikes=userService.getBikes(userId);
        return ResponseEntity.ok(bikes);
    }
    
    
    @CircuitBreaker(name="carsCB",fallbackMethod="fallBackSaveCar")
    @PostMapping("/savecar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable("userId") int userId,@RequestBody Car car){
        if(userService.getUserById(userId)==null) {
        	ResponseEntity.noContent().build();
        }
    	Car carNew=userService.saveCar(userId, car);
        return ResponseEntity.ok(carNew);
    }
    
    @CircuitBreaker(name="bikesCB",fallbackMethod="fallBackSaveBike")
    @PostMapping("/savebike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable("userId") int userId,@RequestBody Bike bike){
    	if(userService.getUserById(userId)==null) {
        	ResponseEntity.noContent().build();
        }
    	Bike bikeNew=userService.saveBike(userId, bike);
        return ResponseEntity.ok(bikeNew);
    }
    
    @CircuitBreaker(name="allCB",fallbackMethod="fallBackGetAll")
    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String,Object>> getAllVehicles(@PathVariable("userId") int userId){
    	Map<String,Object> result=userService.getUserAndVehicles(userId);
    	return ResponseEntity.ok(result);
    }


    private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable("userId") int userId,RuntimeException e){
    	return new ResponseEntity("El usuario "+userId+" tiene los coches en el taller",HttpStatus.OK);
    }
    
    public ResponseEntity<Car> fallBackSaveCar(@PathVariable("userId") int userId,@RequestBody Car car,RuntimeException e){
    	return new ResponseEntity("El usuario "+userId+" no tiene dinero para coches",HttpStatus.OK);
    }
    
    
    private ResponseEntity<List<Bike>> fallBackGetBikes(@PathVariable("userId") int userId,RuntimeException e){
    	return new ResponseEntity("El usuario "+userId+" tiene las motos en el taller",HttpStatus.OK);
    }
    
    public ResponseEntity<Bike> fallBackSaveBike(@PathVariable("userId") int userId,@RequestBody Bike bike,RuntimeException e){
    	return new ResponseEntity("El usuario "+userId+" no tiene dinero para motos",HttpStatus.OK);
    }
    
    
    public ResponseEntity<Map<String,Object>> fallBackGetAll(@PathVariable("userId") int userId,RuntimeException e){
    	return new ResponseEntity("El usuario "+userId+" tiene los vehiculos en el taller",HttpStatus.OK);    	
    }
    
  
}
