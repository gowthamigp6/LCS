package com.lcs.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lcs.exception.LcsException;
import com.lcs.model.LcsRequest;
import com.lcs.model.LcsResponse;
import com.lcs.model.SetOfStrings;
import com.lcs.service.LcsService;

@Service
public class LcsServiceImpl implements LcsService {

	Logger logger = LoggerFactory.getLogger(LcsServiceImpl.class);
	public LcsResponse validateRequest(LcsRequest lcsRequest) throws LcsException {
		String firstValue = null;
		List<SetOfStrings> setOfStrings = lcsRequest.getSetOfStrings();
		if (setOfStrings.size() >= 2) {
			firstValue = lcsRequest.getSetOfStrings().get(0).getValue();
			for (SetOfStrings set : setOfStrings.subList(1, setOfStrings.size())) {
				firstValue = findLongCommonString(firstValue, set.getValue());

			}
		}
		LcsResponse lcsResponse = new LcsResponse();
		lcsResponse.setLcs(new ArrayList<SetOfStrings>());
		lcsResponse.getLcs().add(new SetOfStrings(firstValue));
		return lcsResponse;

	}

	/*
	 * Find the longest common substring
	 */
	public String findLongCommonString(String commonString, String nextValue) throws LcsException {
		int m = commonString.length();
		int n = nextValue.length();

		int[][] array = new int[m + 1][n + 1];

		int len = 0;

		int row = 0, col = 0;

		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				if (i == 0 || j == 0)
					array[i][j] = 0;
				else if (commonString.charAt(i - 1) == nextValue.charAt(j - 1)) {
					array[i][j] = array[i - 1][j - 1] + 1;
					if (len < array[i][j]) {
						len = array[i][j];
						row = i;
						col = j;
					}
				} else
					array[i][j] = 0;
			}
		}

		// if true, then no common substring exists
		if (len == 0) {
			throw new LcsException("No Common Substring");
		}

		// allocate space for the longest common substring
		String resultStr = "";

		
		while (array[row][col] != 0) {
			resultStr = commonString.charAt(row - 1) + resultStr; // or Y[col-1]
			--len;
			row--;
			col--;
		}
		logger.info(resultStr);
		return resultStr;
	}

}
