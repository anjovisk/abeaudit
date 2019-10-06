package com.imarques.abeaudit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.imarques.abeaudit.model.PaymentAuthorization;
import com.imarques.abeaudit.model.TransactionTraceability;
import com.imarques.abeaudit.util.DataContainer;

@Service
public class AuditService {
	private Long transactionId= Long.valueOf(1);
	private static List<TransactionTraceability> traceabilities = new ArrayList<TransactionTraceability>();
	
	public DataContainer<TransactionTraceability> find(TransactionTraceability parameters, int limit, int offset) {
		List<TransactionTraceability> traceabilitiesTemp = traceabilities.stream().filter(traceability -> 
				(parameters.getId() == null || parameters.getId().equals(traceability.getId())) 
				&& (parameters.getPaymentAuthorization() == null || parameters.getPaymentAuthorization().getTransaction() == null ||  parameters.getPaymentAuthorization().getTransaction().getCreditCard() == null || parameters.getPaymentAuthorization().getTransaction().getCreditCard().getNumber() == null || traceability.getPaymentAuthorization().getTransaction().getCreditCard().getNumber().contains(parameters.getPaymentAuthorization().getTransaction().getCreditCard().getNumber())) 
				&& (parameters.getPaymentAuthorization() == null || parameters.getPaymentAuthorization().getDate() == null || parameters.getPaymentAuthorization().getDate().toLocalDate().isEqual(traceability.getPaymentAuthorization().getDate().toLocalDate())) 
				&& (parameters.getPaymentAuthorization() == null || parameters.getPaymentAuthorization().getTransaction() == null || parameters.getPaymentAuthorization().getTransaction().getValue() == null || parameters.getPaymentAuthorization().getTransaction().getValue().equals(traceability.getPaymentAuthorization().getTransaction().getValue()))
			).skip(offset)
				.limit(limit)
				.collect(Collectors.toList());
		DataContainer<TransactionTraceability> result = new DataContainer<TransactionTraceability>(limit, offset, traceabilities.size(), traceabilitiesTemp);
		return result;
	}
	
	public TransactionTraceability create(PaymentAuthorization paymentAuthorization) {
		TransactionTraceability result = new TransactionTraceability();
		result.setPaymentAuthorization(paymentAuthorization);
		result.setId(transactionId++);
		traceabilities.add(result);
		return result;
	}
	
	public Optional<TransactionTraceability> getTraceability(Long id) {
		Optional<TransactionTraceability> result = traceabilities.stream()
				.parallel().filter(transaction -> transaction.getId().equals(id))
				.findFirst();
		return result;
	}
}
