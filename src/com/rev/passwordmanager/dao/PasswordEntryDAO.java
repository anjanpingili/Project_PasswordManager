package com.rev.passwordmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.rev.passwordmanager.util.DBConnectionUtil;

public class PasswordEntryDAO {

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }

    public boolean addPassword(int userId, String accountName, String username, String password) {

        String sql = "INSERT INTO PASSWORD_ENTRIES "
                   + "(ENTRY_ID, USER_ID, ACCOUNT_NAME, USERNAME, PASSWORD) "
                   + "VALUES (PASSWORD_ENTRY_SEQ.NEXTVAL, ?, ?, ?, ?)";

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

    public void getAllPasswords(int userId) {

        String sql = "SELECT ENTRY_ID, ACCOUNT_NAME, USERNAME "
                   + "FROM PASSWORD_ENTRIES WHERE USER_ID = ?";

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

    public void searchPassword(int userId, String accountName) {

        String sql = "SELECT ENTRY_ID, ACCOUNT_NAME, USERNAME "
                   + "FROM PASSWORD_ENTRIES WHERE USER_ID = ? AND ACCOUNT_NAME = ?";

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
                    " | Account: " + rs.getString("ACCOUNT_NAME") +
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

    public boolean deletePassword(int entryId, int userId) {

        String sql = "DELETE FROM PASSWORD_ENTRIES WHERE ENTRY_ID = ? AND USER_ID = ?";

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, entryId);
            ps.setInt(2, userId);

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

    public void viewPassword(int entryId, int userId, String masterPassword) {

        String sql = "SELECT P.ACCOUNT_NAME, P.USERNAME, P.PASSWORD "
                   + "FROM PASSWORD_ENTRIES P JOIN USERS U "
                   + "ON P.USER_ID = U.USER_ID "
                   + "WHERE P.ENTRY_ID = ? AND P.USER_ID = ? AND U.PASSWORD = ?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, entryId);
            ps.setInt(2, userId);
            ps.setString(3, masterPassword);

            rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println(
                    "Account: " + rs.getString("ACCOUNT_NAME") +
                    " | Username: " + rs.getString("USERNAME") +
                    " | Password: " + rs.getString("PASSWORD")
                );
            } else {
                System.out.println("Invalid master password or entry not found");
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

    // STEP 25: Update password entry
    public boolean updatePassword(int entryId, int userId, String newUsername, String newPassword) {

        String sql = "UPDATE PASSWORD_ENTRIES "
                   + "SET USERNAME = ?, PASSWORD = ? "
                   + "WHERE ENTRY_ID = ? AND USER_ID = ?";

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, newUsername);
            ps.setString(2, newPassword);
            ps.setInt(3, entryId);
            ps.setInt(4, userId);

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
}
