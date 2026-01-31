package com.rev.passwordmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.rev.passwordmanager.util.DBConnectionUtil;

public class PasswordEntryDAO {

    private Connection getConnection() throws Exception {
        return DBConnectionUtil.getConnection();
    }

    // ================= ADD PASSWORD =================
    public boolean addPassword(int userId,
                               String accountName,
                               String username,
                               String password) {

        String sql =
            "INSERT INTO PASSWORD_ENTRIES " +
            "(ENTRY_ID, USER_ID, ACCOUNT_NAME, USERNAME, PASSWORD) " +
            "VALUES (PASSWORD_ENTRY_SEQ.NEXTVAL, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, userId);
            ps.setString(2, accountName);
            ps.setString(3, username);
            ps.setString(4, password);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // ================= LIST PASSWORDS =================
    public void getAllPasswords(int userId) {

        String sql =
            "SELECT ENTRY_ID, ACCOUNT_NAME, USERNAME " +
            "FROM PASSWORD_ENTRIES WHERE USER_ID=?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            rs = ps.executeQuery();
            System.out.println("\n--- Stored Passwords ---");

            while (rs.next()) {
                System.out.println(
                    "ID: " + rs.getInt("ENTRY_ID") +
                    " | Account: " + rs.getString("ACCOUNT_NAME") +
                    " | Username: " + rs.getString("USERNAME")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ================= VIEW PASSWORD =================
    public void viewPassword(int entryId, int userId) {

        String sql =
            "SELECT ACCOUNT_NAME, USERNAME, PASSWORD " +
            "FROM PASSWORD_ENTRIES WHERE ENTRY_ID=? AND USER_ID=?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, entryId);
            ps.setInt(2, userId);

            rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Account: " + rs.getString("ACCOUNT_NAME"));
                System.out.println("Username: " + rs.getString("USERNAME"));
                System.out.println("Password: " + rs.getString("PASSWORD"));
            } else {
                System.out.println("Invalid ENTRY_ID");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ================= SEARCH PASSWORD =================
    public void searchPassword(int userId, String accountName) {

        String sql =
            "SELECT ENTRY_ID, USERNAME " +
            "FROM PASSWORD_ENTRIES WHERE USER_ID=? AND ACCOUNT_NAME=?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, userId);
            ps.setString(2, accountName);

            rs = ps.executeQuery();
            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                    "ID: " + rs.getInt("ENTRY_ID") +
                    " | Username: " + rs.getString("USERNAME")
                );
            }

            if (!found) {
                System.out.println("No password found for this account");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
