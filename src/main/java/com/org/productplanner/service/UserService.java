package com.org.productplanner.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.org.productplanner.beans.User;
import com.org.productplanner.repository.UserRepository;

@Service
public class UserService extends CommonService{

	@Autowired
	private UserRepository userRepository;
	
	public boolean validateUser(Map<String,String> loginDetails)
    {
    	String password="";
		System.out.println("Login Data: "+loginDetails);
		try
		{
			password=userRepository.getPassword(loginDetails.get("username"));
		}
		catch(EmptyResultDataAccessException ex)
		{
			return false;
		}
		
		return password.equals(loginDetails.get("password"));//Password is case sensitive
    }
	
    public List<User> getAllUser(){
    	return userRepository.getUsers();
    }

    public void addUser(User user)
    {
    	user.setObjid(getNEXTObjId("TABLE_USER"));
    	user.setStatus(replaceForDB(user.getStatus()));
    	userRepository.addUser(user);
    	
    }

	public void update(User user) {
		user.setStatus(replaceForDB(user.getStatus()));
		userRepository.update(user);
	}
    
	public void delete(String userID){
		userRepository.delete(userID);
	}
}
