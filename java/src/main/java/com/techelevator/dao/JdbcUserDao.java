package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.techelevator.model.User;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsername(String username) {

        String sql = "SELECT user_id FROM users where username ILIKE ?";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, username);
        if (id != null) {
            return id;
            } else {
            return -1;
        }
    }

      /*  if (username == null) throw new IllegalArgumentException("Username cannot be null");

        Integer userId;
        try {
            userId = jdbcTemplate.queryForObject("select user_id from users where username = ?", Integer.class, username);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }

        return userId;
    } */



    @Override
	public User getUserById(int userId) {
		String sql = "SELECT * FROM users WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		if (results.next()) {
			return mapRowToUser(results);
		} else {
			return null;
		}
	}

    @Override
    public List<User> listUsersByGroupId(int groupID) {
        List<User> userList=null;
        String sql = "SELECT * FROM users JOIN group_user USING (user_id)  WHERE users.group_id = ?;";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, groupID);
        if(rs.next()){
            userList = new ArrayList<>();
            userList.add(mapRowToUser(rs));
        }
        return userList;
    }


    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }

        return users;
    }

    @Override
    public User findByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        for (User user : this.findAll()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }



    @Override
    public boolean create(String firstName, String lastName, String username, String password, String role, String email) {
            String insertUserSql = "insert into users (first_name, last_name, username, password_hash, role, email) values (?,?,?,?,?,?)";
            String password_hash = new BCryptPasswordEncoder().encode(password);
            String ssRole = role.toUpperCase().startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase();

            return jdbcTemplate.update(insertUserSql, firstName, lastName, username, password_hash, ssRole, email) == 1;
        }


    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
        user.setEmail(rs.getString("email"));
        user.setActivated(true);
        return user;
    }
}
