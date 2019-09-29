package com.imarques.abeaudit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.imarques.abeaudit.model.TransactionTraceability;
import com.imarques.abeaudit.util.DataContainer;

@Service
public class AuditService {
	private Long transactionId= Long.valueOf(1);
	private static List<TransactionTraceability> traceabilities = new ArrayList<TransactionTraceability>();
	
	public DataContainer<TransactionTraceability> find(TransactionTraceability parameters, int limit, int offset) {
		List<TransactionTraceability> traceabilitiesTemp = traceabilities.stream().filter(traceability -> 
				(parameters.getId() == null || parameters.getId().equals(traceability.getId())) 
				&& (parameters.getTransaction() == null ||  parameters.getTransaction().getCreditCard() == null || parameters.getTransaction().getCreditCard().getNumber() == null || traceability.getTransaction().getCreditCard().getNumber().contains(parameters.getTransaction().getCreditCard().getNumber())) 
				&& (parameters.getDate() == null || parameters.getDate().toLocalDate().isEqual(traceability.getDate().toLocalDate())) 
				&& (parameters.getTransaction() == null || parameters.getTransaction().getValue() == null || parameters.getTransaction().getValue().equals(traceability.getTransaction().getValue()))
			).skip(offset)
				.limit(limit)
				.collect(Collectors.toList());
		DataContainer<TransactionTraceability> result = new DataContainer<TransactionTraceability>(limit, offset, traceabilities.size(), traceabilitiesTemp);
		return result;
	}
	
	public TransactionTraceability create(TransactionTraceability traceability) {
		traceability.setId(transactionId++);
		traceabilities.add(traceability);
		return traceability;
	}
	
	public Optional<TransactionTraceability> getTraceability(Long id) {
		Optional<TransactionTraceability> result = traceabilities.stream()
				.parallel().filter(transaction -> transaction.getId().equals(id))
				.findFirst();
		return result;
	}
}
