package com.lcs.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.lcs.model.LcsResponse;
import com.lcs.model.SetOfStrings;
import com.lcs.service.LcsService;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class LcsControllerTests {
	@Autowired
	LcsController userController;

	@MockBean
	LcsService lcsService;
    @Autowired
    private MockMvc mockMvc;

    
    @Test
    public void calculateLcsTest() throws Exception {
    	LcsResponse lcsResponse = new LcsResponse();
    	lcsResponse.setLcs(new ArrayList<SetOfStrings>());
		lcsResponse.getLcs().add(new SetOfStrings("com"));
    	
    	String output = "{\"lcs\":[{\"value\":\"com\"}]}";
        String lcsRequest = "{ \"setOfStrings\": [{ \"value \": \"comcast\"},{\"value\": \"commu\"}]}";
        Mockito.when(lcsService.validateRequest(Mockito.any())).thenReturn(lcsResponse);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/lcs")
                .content(lcsRequest)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(output);
                
    }
    
    @Test
    public void validateInputTest() throws Exception {
    	String lcsRequest = "{ \"setOfStrings \": []}";
        mockMvc.perform(MockMvcRequestBuilders.post("/lcs")
                .content(lcsRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.setOfStrings", Is.is("Response body should be {setOfStrings: [{value: comcast},{value: communicate}]}")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
