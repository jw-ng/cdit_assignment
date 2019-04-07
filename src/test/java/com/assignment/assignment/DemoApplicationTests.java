package com.assignment.assignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.assignment.assignment.User;
import com.assignment.assignment.UserRepository;

@WebAppConfiguration
public class DemoApplicationTests extends AbstractTest {
	@MockBean
	UserRepository mockUserRepository;

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void getUsers_withOneUserWithSalaryBelow4000_shouldReturnThatUser() throws Exception {
		User onlyUser = new User("A", 1000.00);
		List<User> expectedUsers = new ArrayList<User>(Arrays.asList(onlyUser));
		when(mockUserRepository.findAll()).thenReturn(expectedUsers);

		String uri = "/users";

		MvcResult mvcResult = mvc.perform(
			MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)
		).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		
		TypeReference<HashMap<String, List<User>>> typeRef =
			new TypeReference<HashMap<String, List<User>>>() {};

		ObjectMapper objectMapper = new ObjectMapper();
		HashMap<String, List<User>> results = objectMapper.readValue(
			content,
			typeRef
		);
		assertTrue(expectedUsers.size() == results.get("results").size());
		assertTrue(onlyUser.getName().equals(results.get("results").get(0).getName()));
		assertTrue(onlyUser.getSalary() == results.get("results").get(0).getSalary());
	}

	@Test
	public void getUsers_withOneUserWithSalaryAbove4000_shouldReturnEmptyList() throws Exception {
		User onlyUser = new User("A", 5000.00);
		List<User> users = new ArrayList<User>(Arrays.asList(onlyUser));
		when(mockUserRepository.findAll()).thenReturn(users);

		String uri = "/users";

		MvcResult mvcResult = mvc.perform(
			MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)
		).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		
		TypeReference<HashMap<String, List<User>>> typeRef =
			new TypeReference<HashMap<String, List<User>>>() {};

		ObjectMapper objectMapper = new ObjectMapper();
		HashMap<String, List<User>> results = objectMapper.readValue(
			content,
			typeRef
		);
		assertTrue(0 == results.get("results").size());
	}

	@Test
	public void getUsers_withNoUsers_shouldReturnEmptyList() throws Exception {
		String uri = "/users";

		MvcResult mvcResult = mvc.perform(
			MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)
		).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		
		TypeReference<HashMap<String, List<User>>> typeRef =
			new TypeReference<HashMap<String, List<User>>>() {};

		ObjectMapper objectMapper = new ObjectMapper();
		HashMap<String, List<User>> results = objectMapper.readValue(
			content,
			typeRef
		);
		assertTrue(0 == results.get("results").size());
	}
}