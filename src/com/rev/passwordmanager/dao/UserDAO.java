package com.rev.passwordmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.rev.passwordmanager.util.DBConnectionUtil;

public class UserDAO {

    private static final Logger logger =
            Logger.getLogger(UserDAO.class);

    private Connection getConnection() {
        try {
            return DBConnectionUtil.getConnection();
        } catch (Exception e) {
            logger.error("Failed to get DB connection", e);
            return null;
        }
    }

    // Register user
    public boolean registerUser(String name, String email, String password) {

        String sql = "INSERT INTO USERS (USER_ID, NAME, EMAIL, PASSWORD) "
                   + "VALUES (USER_SEQ.NEXTVAL, ?, ?, ?)";

        logger.info("Attempting registration for email: " + email);

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            ps.executeUpdate();
            logger.info("User registered successfully: " + email);
            return true;

        } catch (Exception e) {
            logger.error("Error during user registration", e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                logger.error("Error closing DB resources", e);
            }
        }
        return false;
    }

    // Login user
    public int loginUser(String email, String password) {

        String sql = "SELECT USER_ID FROM USERS WHERE EMAIL = ? AND PASSWORD = ?";

        logger.info("Login attempt for email: " + email);

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            rs = ps.executeQuery();
            if (rs.next()) {
                logger.info("Login successful for email: " + email);
                return rs.getInt("USER_ID");
            }

        } catch (Exception e) {
            logger.error("Login error", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                logger.error("Error closing DB resources", e);
            }
        }

        logger.warn("Login failed for email: " + email);
        return -1;
    }
}
