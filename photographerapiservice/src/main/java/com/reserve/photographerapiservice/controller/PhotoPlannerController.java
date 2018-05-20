/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reserve.photographerapiservice.controller;

import com.reserve.photographerapiservice.model.AllBookings;
import com.reserve.photographerapiservice.model.Booking;
import com.reserve.photographerapiservice.model.ResultResponse;
import com.reserve.photographerapiservice.model.UserInfo;
import com.reserve.photographerapiservice.service.PhotoPlannerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
public class PhotoPlannerController {

    @Autowired
    PhotoPlannerService service;

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity registerUser(@RequestBody UserInfo userInfo) {
        ResultResponse response = service.registerUser(userInfo);
        switch (response.getCode()) {
            case 200:
                return new ResponseEntity<>(response, HttpStatus.OK);
            default:
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity validateUser(@RequestBody UserInfo userInfo) {
        ResultResponse response = service.validateUser(userInfo);
        switch (response.getCode()) {
            case 200:
                return new ResponseEntity<>(response, HttpStatus.OK);
            default:
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "/book", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity book(@RequestBody Booking booking){
        ResultResponse response = service.bookGuy(booking);
        switch (response.getCode()) {
            case 200:
                return new ResponseEntity<>(response, HttpStatus.OK);
            default:
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "/getbookings", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getBookings(@RequestParam("user") String user){
        AllBookings response = service.getBookings(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/updatestatus", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateStatus(@RequestParam("id") String id,@RequestParam("status") String status){
        ResultResponse response = service.updateStatus(id, status);
        switch (response.getCode()) {
            case 200:
                return new ResponseEntity<>(response, HttpStatus.OK);
            default:
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
