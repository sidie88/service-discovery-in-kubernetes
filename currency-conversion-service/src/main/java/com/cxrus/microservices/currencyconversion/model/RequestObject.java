package com.cxrus.microservices.currencyconversion.model;

public class RequestObject {
	private String from;
	private String to;
	private int quantity;
	
	public RequestObject(String from, String to, int quantity) {
		super();
		this.from = from;
		this.to = to;
		this.quantity = quantity;
	}
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "RequestObject [from=" + from + ", to=" + to + ", quantity=" + quantity + "]";
	}

}
