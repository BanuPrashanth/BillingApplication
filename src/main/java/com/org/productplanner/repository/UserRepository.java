package com.org.productplanner.repository;

import static com.org.productplanner.queries.Query.*;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.org.productplanner.beans.User;
import com.org.productplanner.rowmappers.UserRowMapper;

@Repository
@Transactional
public class UserRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public List<User> getUsers()
    {
    	return jdbcTemplate.query(GET_USERS,new UserRowMapper());
    }
	
	public void addUser(User user)
	{
		jdbcTemplate.update(ADD_USER, new Object[]
				{
					user.getObjid(),
					user.getUserID(),
					user.getUserName(),
					user.getPassword(),
					user.getAddress(),
					user.getPhone(),
					"Active".equalsIgnoreCase(user.getStatus())?"A":"I",
					user.getUserType(),
					user.getCreateDate()
				});
	}
	
	public String getPassword(String userName)
	{
		String password=jdbcTemplate.queryForObject(GET_PASSWORD, new Object[]{userName}, String.class);
		return password==null?"":password;
	}

	public void delete(String userID) {
		jdbcTemplate.update(DELETE_USERS,userID);
		
	}

	public void update(User user) 
	{
		jdbcTemplate.update(UPDATE_USER, 
				user.getUserName(),
				user.getAddress(),
				user.getPhone(),
				user.getStatus(),
				user.getUserType(),
				new Date(),
				user.getUserID());
	}
}
