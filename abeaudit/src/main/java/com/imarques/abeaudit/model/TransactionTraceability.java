package com.imarques.abeaudit.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class TransactionTraceability {
	@ApiModel
	public enum AuthorizationStatus {
		@ApiModelProperty(notes = "Pagamento autorizado.")
		SUCCESS, 
		@ApiModelProperty(notes = "Pagamento não autorizado.")
		ERROR
	}
	private Long id;
	private PaymentAuthorization paymentAuthorization;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PaymentAuthorization getPaymentAuthorization() {
		return paymentAuthorization;
	}
	public void setPaymentAuthorization(PaymentAuthorization paymentAuthorization) {
		this.paymentAuthorization = paymentAuthorization;
	}
}
