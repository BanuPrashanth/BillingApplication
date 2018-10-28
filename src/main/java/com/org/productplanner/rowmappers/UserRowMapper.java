package com.org.productplanner.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.org.productplanner.beans.User;
import com.org.productplanner.service.CommonService;

public class UserRowMapper extends CommonService implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user=new User();
		user.setObjid(rs.getInt(1));
		user.setUserID(rs.getString(2));
		user.setUserName(rs.getString(3));
		user.setAddress(rs.getString(5));
		user.setPhone(rs.getString(6));
		user.setStatus(replaceForGUI(rs.getString(7)));
		user.setUserType(rs.getString(8));
		user.setCreateDate(rs.getDate(9));
		return user;
	}
	
}
