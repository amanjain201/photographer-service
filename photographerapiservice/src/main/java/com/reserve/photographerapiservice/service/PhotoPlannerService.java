/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reserve.photographerapiservice.service;

import com.reserve.photographerapiservice.dao.PhotoPlannerDAOImpl;
import com.reserve.photographerapiservice.model.AllBookings;
import com.reserve.photographerapiservice.model.Booking;
import com.reserve.photographerapiservice.model.ResultResponse;
import com.reserve.photographerapiservice.model.UserInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoPlannerService {
    
    @Autowired
    PhotoPlannerDAOImpl dao;
    
    public ResultResponse registerUser(UserInfo userInfo) {
        ResultResponse response = new ResultResponse();
        
        boolean status = dao.insertUser(userInfo);
        if(status){
            response.setCode(200);
            response.setMessage("User registered successfully");
            response.setType("success");
        } else {
            response.setCode(400);
            response.setMessage("User registration failed");
            response.setType("failure");
        }
        return response;
    }
    
    public ResultResponse validateUser(UserInfo userInfo) {
        ResultResponse response = dao.validateUser(userInfo);
        return response;
    }
    
    public ResultResponse bookGuy(Booking booking){
        ResultResponse response = dao.insertBooking(booking);
        return response;
    }
    
    public AllBookings getBookings(String email){
        AllBookings all = new AllBookings();
        List<Booking> allBookins = dao.getBookings(email);
        all.setAllbookings(allBookins);
        all.setCode(200);
        return all;
    }
    
    public ResultResponse updateStatus(String id,String status){
        ResultResponse response = dao.updateStatus(id, status);
        return response;
    }
    
    
}
