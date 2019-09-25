package com.imarques.abeaudit.controller.v1;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.imarques.abeaudit.model.Transaction;
import com.imarques.abeaudit.service.AuditService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController("AuditControllerV1")
@RequestMapping("/v1/public/audits")
@Api(tags = {"Audits"})
public class AuditController {
	@Autowired
	private AuditService auditService;
	
	@ApiOperation(value = "Registra informações de uma transacao")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Transaction> postBook(
			@ApiParam(required = true, value = "Informações da transação") @RequestBody Transaction transaction) {
		Transaction result = auditService.create(transaction);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(result);
	}
	
	@ApiOperation(value = "Obtém informações de uma transação")
	@RequestMapping(path="/{id}",  method=RequestMethod.GET)
	public ResponseEntity<Transaction> getTransaction(
			@ApiParam(required = true, value = "Código da transação") @PathVariable("id") Long id) {
		Optional<Transaction> transaction = auditService.getTransaction(id);
		ResponseEntity<Transaction> result = ResponseEntity
				.status(transaction.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
				.body(transaction.orElse(null));
		return result;
	}
}
