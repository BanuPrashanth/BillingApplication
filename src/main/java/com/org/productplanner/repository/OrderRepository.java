package com.org.productplanner.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class OrderRepository {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public void cancelOrder(String query,String orderId)
	{
		jdbcTemplate.update(query, orderId);
	}
	public void deleteOrderNotes(String query,String orderId)
	{
		jdbcTemplate.update(query, orderId);
	}
}
