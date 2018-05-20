/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reserve.photographerapiservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 *
 * @author ajain
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class AllBookings extends ResultResponse{
    
    List<Booking> allbookings;

    public List<Booking> getAllbookings() {
        return allbookings;
    }

    public void setAllbookings(List<Booking> allbookings) {
        this.allbookings = allbookings;
    }
    
    
}
