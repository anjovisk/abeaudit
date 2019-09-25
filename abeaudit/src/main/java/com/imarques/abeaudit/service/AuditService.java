package com.imarques.abeaudit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.imarques.abeaudit.model.Transaction;

@Service
public class AuditService {
	private Long transactionId= Long.valueOf(1);
	private static List<Transaction> transactions = new ArrayList<Transaction>();
	
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
