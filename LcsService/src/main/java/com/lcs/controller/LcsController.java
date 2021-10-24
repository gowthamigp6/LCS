package com.lcs.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lcs.exception.LcsException;
import com.lcs.model.LcsRequest;
import com.lcs.model.LcsResponse;
import com.lcs.service.LcsService;

@CrossOrigin(allowedHeaders="*",origins="*")
@RestController
public class LcsController {

	 Logger logger = LoggerFactory.getLogger(LcsController.class);
	@Autowired
	private LcsService lcsService;

	@PostMapping("lcs")
	public ResponseEntity<?> calculateLcs(@Valid  @RequestBody LcsRequest lcsRequest)  {
		LcsResponse lcsResponse = null;
		try {
			lcsResponse = lcsService.validateRequest(lcsRequest);
		} catch (LcsException e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(" { message : all strings are not unique }");
		} catch(Exception e) {
			logger.error("Exception in calculating lcs"+e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error while calculating the LCS");
		}
		return new ResponseEntity<LcsResponse>(HttpStatus.OK).ok(lcsResponse);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

	@ExceptionHandler(value = LcsException.class)
	public ResponseEntity<Object> exception(LcsException ex) {
		return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.PARTIAL_CONTENT);
	}
}
