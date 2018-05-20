/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reserve.photographerapiservice.dao;

import com.reserve.photographerapiservice.model.Booking;
import com.reserve.photographerapiservice.model.ResultResponse;
import com.reserve.photographerapiservice.model.UserInfo;
import java.util.List;


public interface PhotoPlannerDAO {
    
    public boolean insertUser(UserInfo userInfo);
    
    public ResultResponse validateUser(UserInfo userInfo);
    
    public ResultResponse insertBooking(Booking booking);
    
    public List<Booking> getBookings(String email);
    
    public ResultResponse updateStatus(String id,String status);
}
