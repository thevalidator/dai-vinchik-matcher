/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sqlite.SQLiteConfig;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class DBUtil {

    private static final Logger logger = LogManager.getLogger(DBUtil.class);
    private static final String DB_PATH = "jdbc:sqlite:dvm.db";

    public static void createDatabase() {
        try ( Connection conn = DriverManager.getConnection(DB_PATH)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                //System.out.println("The driver name is " + meta.getDriverName());
                //System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
        }
    }

    public static boolean createTables() {
        String sql = "CREATE TABLE IF NOT EXISTS user (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	first_name text,\n"
                + "	last_name text,\n"
                + "	vk_id integer UNIQUE\n"
                + ");";

        String sql2 = "CREATE TABLE IF NOT EXISTS like (\n"
                + "	user_id integer NOT NULL,\n"
                + "	liked_vk_id integer NOT NULL,\n"
                + "	timestamp text NOT NULL,\n"
                + "	has_reply integer NOT NULL DEFAULT 0 CHECK(has_reply IN (0, 1)),\n"
                + "	was_invited integer NOT NULL DEFAULT 0 CHECK(has_reply IN (0, 1)),\n"
                + "	CONSTRAINT new_pk PRIMARY KEY (user_id, liked_vk_id),\n"
                + "     FOREIGN KEY(user_id) REFERENCES user(id) ON DELETE CASCADE"
                + ");";
        try ( Connection conn = DriverManager.getConnection(DB_PATH);  Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            stmt.execute(sql2);
        } catch (SQLException e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
            return false;
        }
        return true;
    }

    public static boolean dropTables() {
        String sql = "DROP TABLE user;";
        String sql2 = "DROP TABLE like;";
        try ( Connection conn = DriverManager.getConnection(DB_PATH);  Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            stmt.execute(sql2);
        } catch (SQLException e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
            return false;
        }
        return true;
    }

    public static boolean isUserExists(Integer userVkId) {
        String sql = "SELECT EXISTS(SELECT 1 FROM user WHERE vk_id=?) AS res;";
        try ( Connection conn = DriverManager.getConnection(DB_PATH);  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userVkId);
            return pstmt.executeQuery().getInt("res") == 1;
        } catch (SQLException e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
            return false;
            //TODO: throw exception instead of returning false
        }
    }

    public static Integer insertUser(String firstName, String lastName, Integer userVkId) {
        Integer id;
        String sql = "INSERT INTO user(first_name, last_name, vk_id) VALUES(?,?,?) RETURNING id;";
        try ( Connection conn = DriverManager.getConnection(DB_PATH);  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, userVkId);
            id = pstmt.executeQuery().getInt("id");//executeUpdate();
        } catch (SQLException e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
            return null;
        }
        return id;
    }

    public static boolean insertLikeByUserVkId(Integer userVkId, Integer likedVkId, String timestamp) {
        String sql = "INSERT INTO like(user_id, liked_vk_id, timestamp) VALUES((SELECT id FROM user WHERE vk_id=?),?,?);";
        try ( Connection conn = DriverManager.getConnection(DB_PATH);  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userVkId);
            pstmt.setInt(2, likedVkId);
            pstmt.setString(3, timestamp);
            pstmt.execute();
        } catch (SQLException e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
            return false;
        }
        return true;
    }

    public static List<Integer> getLikedUsersVkId(Integer userVkId) {
        List<Integer> likes = new ArrayList<>();
        String sql = "SELECT l.liked_vk_id "
                + "FROM \"like\" l "
                + "WHERE l.user_id=(SELECT u.id FROM \"user\" u WHERE u.vk_id=?) AND l.has_reply=0 AND l.was_invited=0;";
        try ( Connection conn = DriverManager.getConnection(DB_PATH);  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userVkId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                likes.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
            return likes;
        }
        return likes;
    }

    public static void setHasReplyTrue(Integer userVkId, Integer likedVkId) {
        String sql = "UPDATE like SET has_reply=1 WHERE user_id=(SELECT id FROM user WHERE vk_id=?) AND liked_vk_id=?;";
        try ( Connection conn = DriverManager.getConnection(DB_PATH);  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userVkId);
            pstmt.setInt(2, likedVkId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
        }
    }

    public static void setWasInvited(Integer userVkId, Integer likedVkId) {
        String sql = "UPDATE like SET was_invited=1 WHERE user_id=(SELECT id FROM user WHERE vk_id=?) AND liked_vk_id=?;";
        try ( Connection conn = DriverManager.getConnection(DB_PATH);  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userVkId);
            pstmt.setInt(2, likedVkId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
        }
    }

    public static boolean deleteAllLikesFromLikedIdWithoutReply(Integer likedVkId) {
        String sql = "DELETE FROM like WHERE liked_vk_id=? AND has_reply=0;";
        try ( Connection conn = DriverManager.getConnection(DB_PATH);  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, likedVkId);
            pstmt.execute();
        } catch (SQLException e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
            return false;
        }
        return true;
    }

    public static boolean deleteUser(Integer userId) {
        SQLiteConfig config = new SQLiteConfig();
        config.setPragma(SQLiteConfig.Pragma.FOREIGN_KEYS, "ON");
        String sql = "DELETE FROM user WHERE vk_id=?;";
        try ( Connection conn = DriverManager.getConnection(DB_PATH, config.toProperties());  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.execute();
        } catch (SQLException e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
            return false;
        }
        return true;
    }
    
    public static boolean isTableExists(String tableName) {
        String sql = "SELECT EXISTS (SELECT name FROM sqlite_master WHERE type='table' AND name=?);";
        try ( Connection conn = DriverManager.getConnection(DB_PATH);  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tableName);
            Integer res = pstmt.executeQuery().getInt(1);
            return res == 1;
        } catch (SQLException e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
            return false;
        }
    }

//    public static boolean deleteAllLikesForUserId(Integer userId) {
//        String sql = "DELETE FROM like WHERE user_id=?;";
//        try ( Connection conn = DriverManager.getConnection(DB_PATH);  PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, userId);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            return false;
//        }
//        return true;
//    }
//    public static boolean deleteLike(Integer userVkId, Integer likedVkId) {
//        String sql = "DELETE FROM like WHERE user_id=(SELECT id FROM user WHERE vk_id=?) AND liked_vk_id=?;";
//        try ( Connection conn = DriverManager.getConnection(DB_PATH);  PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, userVkId);
//            pstmt.setInt(2, likedVkId);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            return false;
//        }
//        return true;
//    }
//    public static boolean insertLikeByUserId(String userId, String likedUserVkId, String timestamp) {
//        String sql = "INSERT INTO like(user_id, liked_vk_id, timestamp) VALUES(?,?,?);";
//        try ( Connection conn = DriverManager.getConnection(DB_PATH);  PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, Integer.parseInt(userId));
//            pstmt.setString(2, likedUserVkId);
//            pstmt.setString(3, timestamp);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            return false;
//        }
//        return true;
//    }
}
