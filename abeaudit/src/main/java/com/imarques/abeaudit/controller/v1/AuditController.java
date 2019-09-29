package com.imarques.abeaudit.controller.v1;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imarques.abeaudit.model.CreditCard;
import com.imarques.abeaudit.model.Transaction;
import com.imarques.abeaudit.model.TransactionTraceability;
import com.imarques.abeaudit.service.AuditService;
import com.imarques.abeaudit.util.DataContainer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController("AuditControllerV1")
@RequestMapping("/v1/public/audits")
@Api(tags = {"Audits"})
public class AuditController {
	@Autowired
	private AuditService auditService;
	
	@ApiOperation(value = "Registra informações de uma transacao", code = 201 )
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Transação registrada com sucesso."),
			@ApiResponse(code = 406, message = "Não foi possível registrar a transacão.")
	})
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<TransactionTraceability> postTransaction(
			@ApiParam(required = true, value = "Informações da transação") @RequestBody TransactionTraceability transaction) {
		TransactionTraceability result = auditService.create(transaction);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(result);
	}
	
	@ApiOperation(value = "Obtém informações de uma transação")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Solicitação procecessada com sucesso."),
			@ApiResponse(code = 404, message = "Transação não encontrada.")
	})
	@RequestMapping(path="/{id}",  method=RequestMethod.GET)
	public ResponseEntity<TransactionTraceability> getTransaction(
			@ApiParam(required = true, value = "Código da transação") @PathVariable("id") Long id) {
		Optional<TransactionTraceability> transaction = auditService.getTraceability(id);
		ResponseEntity<TransactionTraceability> result = ResponseEntity
				.status(transaction.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
				.body(transaction.orElse(null));
		return result;
	}
	
	@ApiOperation(value = "Obtém informações de uma transação")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Solicitação procecessada com sucesso.")
	})
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<DataContainer<TransactionTraceability>> getTransactions(
			@ApiParam(required = false, value = "Código ISBN") @RequestParam(name="isbn", required=false) Long isbn, 
			@ApiParam(required = false, value = "Id da transação") @RequestParam(name="id", required=false) Long id,
			@ApiParam(required = false, value = "Nickname do usuário que efetuou a transação") @RequestParam(name="userId", required=false) String username,
			@ApiParam(required = false, value = "Data em que a transação foi efetuada") @RequestParam(name="date", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
			@ApiParam(required = false, value = "Número do cartão") @RequestParam(name="creditCardNumber", required=false) String creditCardNumber,
			@ApiParam(required = false, value = "Valor da transação") @RequestParam(name="value", required=false) BigDecimal value,
			@ApiParam(required = false, value = "Quantidade máxima de transações na requisição", defaultValue = "10") @RequestParam(name="limit", required=false, defaultValue = "10") int limit,
			@ApiParam(required = false, value = "Quantidade de transações ignoradas na pesquisa", defaultValue = "0") @RequestParam(name="offset", required=false, defaultValue = "0") int offset) {
		TransactionTraceability parameters = new TransactionTraceability();
		parameters.setTransaction(new Transaction());
		parameters.getTransaction().setCreditCard(new CreditCard());
		parameters.setId(id);
		parameters.getTransaction().getCreditCard().setNumber(creditCardNumber);
		parameters.setDate(date != null ? LocalDateTime.from(date) : null);
		parameters.getTransaction().setValue(value);
		DataContainer<TransactionTraceability> transactions = auditService.find(parameters, limit, offset);
		return ResponseEntity.status(HttpStatus.OK).body(transactions);
	}
}
