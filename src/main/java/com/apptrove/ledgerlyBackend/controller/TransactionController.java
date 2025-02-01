package com.apptrove.ledgerlyBackend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apptrove.ledgerlyBackend.entities.TransactionRecords;
import com.apptrove.ledgerlyBackend.payload.ApiResponse;
import com.apptrove.ledgerlyBackend.payload.TransactionAuthorModel;
import com.apptrove.ledgerlyBackend.payload.TransactionMakerModel;
import com.apptrove.ledgerlyBackend.service.TxnRecordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/ldgr/T8000")
@RequiredArgsConstructor
public class TransactionController {
	
	private static final Logger logger = LogManager.getLogger(TransactionController.class);
	
	private final TxnRecordService txnRecordService;
	
	private final Environment environment;
	
	@PostMapping(path = "/S8001")
	public ResponseEntity<ApiResponse<Map<String,Object>>> transactionMakerService(@RequestBody TransactionMakerModel transactionMakerModel) {
		Map<String, Object> respObject =  new HashMap<String, Object>();
 		try {
			respObject = txnRecordService.makerTransactionRecord(transactionMakerModel);
			ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<Map<String,Object>>();
			apiResponse.setRespObject(respObject);
			boolean flag = respObject.containsKey("flag") ? (boolean) respObject.get("flag") : false;
			String errorCode = flag ? environment.getProperty("common.request.success.code") : environment.getProperty("common.request.failed.code");
			String errorMsg = flag ? environment.getProperty("txn.maker.request.success.message") : environment.getProperty("txn.maker.request.failure.message");
			apiResponse.setErrorCd(errorCode);
			apiResponse.setErrorMsg(errorMsg);
			return new ResponseEntity<ApiResponse<Map<String,Object>>>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("An error occurred: "+e.getMessage());
			return new ResponseEntity<ApiResponse<Map<String,Object>>>(new ApiResponse<Map<String,Object>>(null, environment.getProperty("txn.maker.request.failure.message"), environment.getProperty("common.request.failed.code")),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/S8002")
	public ResponseEntity<ApiResponse<Map<String, Object>>> transactionAuthorService(@RequestBody TransactionAuthorModel transactionAuthorModel) {
		Map<String, Object> respObject =  new HashMap<String, Object>();
 		try {
			respObject = txnRecordService.authorTransactionRecord(transactionAuthorModel);
			ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<Map<String,Object>>();
			apiResponse.setRespObject(respObject);
			boolean flag = respObject.containsKey("flag") ? (boolean) respObject.get("flag") : false;
			String errorCode = flag ? environment.getProperty("common.request.success.code") : environment.getProperty("common.request.failed.code");
			String errorMsg = respObject.containsKey("message") ? respObject.get("message").toString() : flag ? environment.getProperty("txn.author.request.success.message") : environment.getProperty("txn.author.request.failure.message");
			apiResponse.setErrorCd(errorCode);
			apiResponse.setErrorMsg(errorMsg);
			return new ResponseEntity<ApiResponse<Map<String,Object>>>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("An error occurred: "+e.getMessage());
			return new ResponseEntity<ApiResponse<Map<String,Object>>>(new ApiResponse<Map<String,Object>>(null, environment.getProperty("txn.author.request.failure.message"), environment.getProperty("common.request.failed.code")),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/S8003")
	public ResponseEntity<ApiResponse<List<TransactionRecords>>> unauthTxnListService() {
		List<TransactionRecords> recList = new ArrayList<TransactionRecords>();
		try {
			recList = txnRecordService.getUnauthorizedTxnList();
			ApiResponse<List<TransactionRecords>> apiResponse = new ApiResponse<List<TransactionRecords>>();
			apiResponse.setRespObject(recList);
			boolean flag = (recList == null || recList.isEmpty()) ? true : false;
			String errorCode = flag ? environment.getProperty("common.request.success.code") : environment.getProperty("common.request.failed.code");
			String errorMsg = flag ? environment.getProperty("txn.author.request.success.message") : environment.getProperty("txn.author.request.failure.message");
			apiResponse.setErrorCd(errorCode);
			apiResponse.setErrorMsg(errorMsg);
			return new ResponseEntity<ApiResponse<List<TransactionRecords>>>(apiResponse, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("An error occurred: "+e.getMessage());
			return new ResponseEntity<ApiResponse<List<TransactionRecords>>>(new ApiResponse<List<TransactionRecords>>(null, environment.getProperty("txn.author.request.failure.message"), environment.getProperty("common.request.failed.code")),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	

}
