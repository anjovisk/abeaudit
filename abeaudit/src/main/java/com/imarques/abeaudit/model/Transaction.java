package com.imarques.abeaudit.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
	private Long id;
	private Long isbn;
	private Long userId;
	private LocalDateTime date;
	private BigDecimal value;
	private String creditCardNuber;
	
	public Long getIsbn() {
		return isbn;
	}
	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public String getCreditCardNuber() {
		return creditCardNuber;
	}
	public void setCreditCardNuber(String creditCardNuber) {
		this.creditCardNuber = creditCardNuber;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
