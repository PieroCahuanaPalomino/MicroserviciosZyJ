/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tutorial.bike.service.repository;

import com.tutorial.bike.service.entity.Bike;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USUARIO
 */
@Repository
public interface BikeRepository extends JpaRepository<Bike, Integer>{
    
    List<Bike> findByUserId(int userId);
}
