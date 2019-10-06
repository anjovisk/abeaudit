package com.imarques.abeaudit.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class PaymentAuthorization {
	@ApiModel
	public enum AuthorizationStatus {
		@ApiModelProperty(notes = "Pagamento autorizado.")
		SUCCESS, 
		@ApiModelProperty(notes = "Pagamento não autorizado.")
		ERROR
	}
	private Long id;
	private Transaction transaction;
	private AuthorizationStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(notes = "Data do pagamento no formato yyyy/MM/dd HH:mm:ss", example = "2019/09/21 17:08:54")
	private LocalDateTime date;
	public Transaction getTransaction() {
		return transaction;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	public AuthorizationStatus getStatus() {
		return status;
	}
	public void setStatus(AuthorizationStatus status) {
		this.status = status;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
