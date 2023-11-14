package com.trip.back.account;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AccountControllerTest {
	
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private AccountMapper accountRepository;
	
	@Autowired
	private ObjectMapper objectMaper;

	@Test
	@DisplayName("회원 가입 - 중복 아이디")
	void testJoin() throws Exception {
		
		Account account = Account.builder().email("yjk9805@naver.com").nickname("yjk9805").
				password("1q2w3e4r").build();
		String joinBody = objectMaper.writeValueAsString(account);
		
		mock.perform(post("/api/account/join").content(joinBody).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().is2xxSuccessful())
		.andDo(print());
		
	}

	@Test
	@Disabled
	void testHello() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled
	void testCheckEmail() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled
	void testCheckNickname() {
		fail("Not yet implemented");
	}

}
