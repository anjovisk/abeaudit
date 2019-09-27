package com.imarques.abeaudit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.imarques.abeaudit.model.Transaction;
import com.imarques.abeaudit.util.DataContainer;

@Service
public class AuditService {
	private Long transactionId= Long.valueOf(1);
	private static List<Transaction> transactions = new ArrayList<Transaction>();
	
	public DataContainer<Transaction> find(Transaction parameters, int limit, int offset) {
		List<Transaction> transactionsTemp = transactions.stream().filter(transaction -> 
				(parameters.getId() == null || parameters.getId().equals(transaction.getId())) 
				&& (parameters.getCreditCardNumber() == null || parameters.getCreditCardNumber().contains(transaction.getCreditCardNumber())) 
				&& (parameters.getDate() == null || parameters.getDate().toLocalDate().isEqual(transaction.getDate().toLocalDate())) 
				&& (parameters.getIsbn() == null || parameters.getIsbn().equals(transaction.getIsbn())) 
				&& (parameters.getUserId() == null || parameters.getUserId().equals(transaction.getUserId())) 
				&& (parameters.getValue() == null || parameters.getValue().equals(transaction.getValue()))
			).skip(offset)
				.limit(limit)
				.collect(Collectors.toList());
		DataContainer<Transaction> result = new DataContainer<Transaction>(limit, offset, transactions.size(), transactionsTemp);
		return result;
	}
	
	public Transaction create(Transaction transaction) {
		transaction.setId(transactionId++);
		transactions.add(transaction);
		return transaction;
	}
	
	public Optional<Transaction> getTransaction(Long id) {
		Optional<Transaction> result = transactions.stream()
				.parallel().filter(transaction -> transaction.getId().equals(id))
				.findFirst();
		return result;
	}
}
