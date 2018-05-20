/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reserve.photographerapiservice.dao;

import com.reserve.photographerapiservice.model.Booking;
import com.reserve.photographerapiservice.model.ResultResponse;
import com.reserve.photographerapiservice.model.UserInfo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

@Service
public class PhotoPlannerDAOImpl implements PhotoPlannerDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public boolean insertUser(UserInfo userInfo) {
        try {
            jdbcTemplate.update((Connection connection) -> {
                PreparedStatement ps = null;
                try {
                    ps = connection.prepareStatement("INSERT INTO USER (NAME,EMAIL,PASSWORD,PHONE) VALUES (?,?,?,?)");
                    ps.setString(1, userInfo.getUserName());
                    ps.setString(2, userInfo.getEmail());
                    ps.setString(3, userInfo.getPassword());
                    ps.setString(4, userInfo.getPhone());
                } catch (SQLException e) {
                    if (ps != null) {
                        ps.close();
                    }
                    throw (e);
                }
                return ps;
            });

        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ResultResponse validateUser(UserInfo userInfo) {
        ResultResponse resultResponse = new ResultResponse();
        try {
            int status = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM USER WHERE EMAIL =?",
                    new Object[]{userInfo.getEmail()}, Integer.class);
            if (status > 0) {
                status = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM USER WHERE EMAIL = ? AND PASSWORD = ?",
                        new Object[]{userInfo.getEmail(), userInfo.getPassword()}, Integer.class);
                if (status > 0) {
                    resultResponse.setCode(200);
                    resultResponse.setMessage("Success");
                    resultResponse.setType("success");
                } else {
                    resultResponse.setCode(400);
                    resultResponse.setMessage("Incorrect Password");
                    resultResponse.setType("failure");
                }
            } else {
                resultResponse.setCode(400);
                resultResponse.setMessage("User doesn't exist");
                resultResponse.setType("failure");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            resultResponse.setCode(400);
            resultResponse.setMessage("Error Occured");
            resultResponse.setType("failure");
        }
        return resultResponse;
    }

    @Override
    public ResultResponse insertBooking(Booking booking) {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setCode(400);
        resultResponse.setType("failure");
        try {
            int status = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM BOOKING WHERE EMAIL =? AND PID=? AND "
                    + "DATE=? AND STATUS IN (?,?)",
                    new Object[]{booking.getEmail(), booking.getPid(), booking.getDate(), "APPROVED", "PENDING"}, Integer.class);
            if (status > 0) {
                resultResponse.setMessage("A booking already exists for you on same date.");
                return resultResponse;
            }
            status = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM BOOKING WHERE PID =? AND DATE=? AND "
                    + "STATUS IN (?)",
                    new Object[]{booking.getPid(), booking.getDate(), "APPROVED"}, Integer.class);

            if (status == 0) {
                jdbcTemplate.update((Connection connection) -> {
                    PreparedStatement ps = null;
                    try {
                        ps = connection.prepareStatement("INSERT INTO BOOKING (CNAME,EMAIL,PID,DATE,STATUS) VALUES (?,?,?,?,?)");
                        ps.setString(1, booking.getEmail());
                        ps.setString(2, booking.getEmail());
                        ps.setString(3, booking.getPid());
                        ps.setString(4, booking.getDate());
                        ps.setString(5, booking.getStatus());
                    } catch (SQLException e) {
                        if (ps != null) {
                            ps.close();
                        }
                        throw (e);
                    }
                    return ps;
                });
                resultResponse.setCode(200);
                resultResponse.setType("success");
                resultResponse.setMessage("Booking successful.Wait for admin approval!");
                return resultResponse;
            } else {
                resultResponse.setMessage("Photographer is already booked for the given date");
                return resultResponse;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultResponse;
    }

    @Override
    public List<Booking> getBookings(String email) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(new Date(System.currentTimeMillis()));

        final List<Booking> allBookings = new ArrayList<>();
        String sql;
        if (email.equalsIgnoreCase("admin@gmail.com")) {
            String status = "PENDING";
            sql = "SELECT * FROM BOOKING WHERE STATUS='" + status + "' AND UNIX_TIMESTAMP(DATE)>=UNIX_TIMESTAMP('" + currentDate + "')";
        } else {
            sql = "SELECT * FROM BOOKING WHERE EMAIL='" + email + "' AND UNIX_TIMESTAMP(DATE)>=UNIX_TIMESTAMP('" + currentDate + "')";
        }
        System.out.println("Sql : " + sql);
        try {
            jdbcTemplate.query(sql, (ResultSet rs) -> {
                Booking booking = new Booking();
                booking.setId(String.valueOf(rs.getInt("ID")));
                booking.setPid(rs.getString("PID"));
                booking.setEmail(rs.getString("EMAIL"));
                booking.setDate(rs.getString("DATE"));
                booking.setStatus(rs.getString("STATUS"));
                allBookings.add(booking);
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return allBookings;
    }

    @Override
    public ResultResponse updateStatus(String id, String status) {
        ResultResponse resultResponse = new ResultResponse();
        try {
            jdbcTemplate.update("UPDATE BOOKING SET STATUS=? WHERE ID=?", new Object[]{status, id});
            resultResponse.setCode(200);
            resultResponse.setType("success");
            resultResponse.setMessage("Booking updated successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
            resultResponse.setCode(400);
            resultResponse.setType("failure");
            resultResponse.setMessage("Booking updation failed.Please try after some time.");
        }
        return resultResponse;
    }

}
