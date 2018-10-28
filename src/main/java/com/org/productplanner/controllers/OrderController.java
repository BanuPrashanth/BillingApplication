package com.org.productplanner.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.org.productplanner.beans.DeliveryNote;
import com.org.productplanner.beans.Notes;
import com.org.productplanner.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	OrderService orderService;

	@RequestMapping(value="/generate/Id/{orderDate}/{simple}",method = RequestMethod.GET)
	public @ResponseBody Map<String,String> deliveryNote(@PathVariable(value="orderDate") Date orderDate,@PathVariable(value="simple") boolean simple)
	{
		return orderService.generateDeliveryNoteID(orderDate,simple);
	}
	
	@RequestMapping(value="/fetch/{simple}",method = RequestMethod.POST)
	public @ResponseBody List<DeliveryNote> getOrders(@RequestBody DeliveryNote deliveryNote,@PathVariable(value="simple") boolean simple)
	{
		return orderService.getDeliveryNote(deliveryNote,simple);
	}
	
	@RequestMapping(value="/notes/{orderId}/{simple}",method = RequestMethod.GET)
	public @ResponseBody List<Notes> getOrderNotes(@PathVariable(value="orderId") String orderId,@PathVariable(value="simple") boolean simple)
	{
		return orderService.getNotes(orderId, simple);
	}
	
	@RequestMapping(value="/{simple}",method = RequestMethod.POST)
	public @ResponseBody boolean save(@RequestBody DeliveryNote deliveryNote,@PathVariable(value="simple") boolean simple)
	{
		return orderService.save(deliveryNote,simple);
	}
	
	@RequestMapping(value="/generate/{simple}",method = RequestMethod.POST)
	public @ResponseBody void download(HttpServletResponse response,@RequestBody DeliveryNote deliveryNote,@PathVariable(value="simple") boolean simple)
	{
		System.out.println("Download started");
		orderService.download(response, deliveryNote,simple);
		System.out.println("Download Complete");
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody void updateOrder(@RequestBody Map<String,Object> orderMap)
	{
		orderService.updateOrder(orderMap);
	}
	
	@RequestMapping(value="/{simple}/{id}",method = RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable(value="simple") boolean simple, @PathVariable(value="id") String id)
	{
		orderService.delete(simple, id);
	}
}
