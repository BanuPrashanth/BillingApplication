package com.org.productplanner.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.org.productplanner.beans.PartyWise;
import com.org.productplanner.beans.Report;
import com.org.productplanner.beans.Stock;
import com.org.productplanner.rowmappers.PartyWiseRowMapper;
import com.org.productplanner.rowmappers.StockRowMapper;

@Repository
@Transactional
public class ReportRepository {
		
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private String query;
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<Stock> getStocks(Report report)
	{
		List<Stock> stocks=jdbcTemplate.query(
							this.query,
							new Object[]
									{
										report.getProductID(),
										report.getFromDate(),
										report.getToDate()
									}, 
							new StockRowMapper());

		return stocks;
	}
	
	public List<PartyWise> getParties(Report report)
	{
		List<PartyWise> stocks=jdbcTemplate.query(
							this.query,
							new Object[]
									{
										report.getCustomerID(),
										report.getFromDate(),
										report.getToDate()
									}, 
							new PartyWiseRowMapper());

		return stocks;
	}
}
