/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tutorial.user.service.service;

import com.tutorial.user.service.entity.User;
import com.tutorial.user.service.feignClients.BikeFeignClient;
import com.tutorial.user.service.feignClients.CarFeignClient;
import com.tutorial.user.service.model.Bike;
import com.tutorial.user.service.model.Car;
import com.tutorial.user.service.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RestTemplate restTemplate;
  
    
    @Autowired
    CarFeignClient carFeignClient;
   
    @Autowired
    BikeFeignClient bikeFeignClient;
    
    public List<User> getAll(){
        return userRepository.findAll();
    }
    
    
    public User getUserById(int id){
        return userRepository.findById(id).orElse(null);
    }
    
    public User save(User user){
        User userNew =userRepository.save(user);
        return userNew;
    }
    
    
    public List<Car> getCars(int userId){
        List<Car> cars=restTemplate.getForObject("http://car-service/car/byuser/"+userId,List.class);
        return cars;
    }
    
    public List<Bike> getBikes(int userId){
        List<Bike> bikes=restTemplate.getForObject("http://bike-service/bike/byuser/"+userId,List.class);
        return bikes;
    }
    
    
    public Car saveCar (int userId,Car car){
        car.setUserId(userId);
        Car carNew=carFeignClient.save(car);
        return carNew;
    }
    
    public Bike saveBike (int userId,Bike bike){
        bike.setUserId(userId);
        Bike bikeNew=bikeFeignClient.save(bike);
        return bikeNew;
    }
    
    public Map<String,Object> getUserAndVehicles(int userId){
    	Map<String,Object> result=new HashMap<>();
    	User user=userRepository.findById(userId).orElse(null);
    	if(user==null) {
    		result.put("Mensaje","No existe el usuario");
    	}
    	result.put("User", user);
    	
    	
    	List<Car> cars=carFeignClient.getCars(userId);
    	if(cars.isEmpty()) {
    		result.put("Cars", "El "+userId+" usuario no tiene coches");
    	}else {
    		result.put("Cars", cars);
    	}
    	
    	
    	List<Bike> bikes=bikeFeignClient.getBikes(userId);
    	if(bikes.isEmpty()) {
    		result.put("Bikes", "El "+userId+" usuario no tiene motos");
    	}else {
    		result.put("Bikes", bikes);
    	}
    	return result;
    }
}
