package com.lcs.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LcsRequest {

	@NotNull(message = "Response body should be {setOfStrings: [{value: comcast},{value: communicate}]}")
	@NotEmpty(message = "Response body should be {setOfStrings: [{value: comcast},{value: communicate}]}")
	private List<SetOfStrings> setOfStrings;
}
