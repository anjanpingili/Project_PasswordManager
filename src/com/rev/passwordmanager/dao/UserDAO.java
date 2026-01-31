package com.rev.passwordmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.rev.passwordmanager.util.DBConnectionUtil;

public class UserDAO {

    private static final Logger logger =
            Logger.getLogger(UserDAO.class);

    private Connection getConnection() throws Exception {
        return DBConnectionUtil.getConnection();
    }

    // ================= REGISTER (SECURITY QUESTION MANDATORY) =================
    public boolean registerUser(String name,
                                String email,
                                String password,
                                String question,
                                String answer) {

        String sql =
            "INSERT INTO USERS " +
            "(USER_ID, NAME, EMAIL, PASSWORD, SECURITY_QUESTION, SECURITY_ANSWER) " +
            "VALUES (USER_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, question);
            ps.setString(5, answer);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            logger.error("User registration failed", e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return false;
    }

    // ================= LOGIN =================
    public int loginUser(String email, String password) {

        String sql =
            "SELECT USER_ID FROM USERS WHERE EMAIL=? AND PASSWORD=?";

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
                return rs.getInt("USER_ID");
            }

        } catch (Exception e) {
            logger.error("Login failed", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return -1;
    }

    // ================= MASTER PASSWORD VERIFICATION =================
    public int loginUserById(int userId, String password) {

        String sql =
            "SELECT USER_ID FROM USERS WHERE USER_ID=? AND PASSWORD=?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, userId);
            ps.setString(2, password);

            rs = ps.executeQuery();
            if (rs.next()) {
                return userId;
            }

        } catch (Exception e) {
            logger.error("Master password verification failed", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return -1;
    }

    // ================= FORGOT PASSWORD SUPPORT =================
    public String getSecurityQuestion(String email) {

        String sql =
            "SELECT SECURITY_QUESTION FROM USERS WHERE EMAIL=?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, email);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("SECURITY_QUESTION");
            }

        } catch (Exception e) {
            logger.error(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return null;
    }

    public boolean verifySecurityAnswer(String email, String answer) {

        String sql =
            "SELECT 1 FROM USERS WHERE EMAIL=? AND SECURITY_ANSWER=?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, answer);

            rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            logger.error(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return false;
    }

    public boolean resetPassword(String email, String newPassword) {

        String sql =
            "UPDATE USERS SET PASSWORD=? WHERE EMAIL=?";

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, newPassword);
            ps.setString(2, email);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            logger.error(e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return false;
    }
}
