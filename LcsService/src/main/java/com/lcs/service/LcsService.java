package com.lcs.service;

import com.lcs.exception.LcsException;
import com.lcs.model.LcsRequest;
import com.lcs.model.LcsResponse;

public interface LcsService {

	public LcsResponse validateRequest(LcsRequest lcsRequest) throws LcsException;
}
