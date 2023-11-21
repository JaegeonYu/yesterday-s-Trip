package com.trip.back.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class AccountServiceTest {
	
	@Autowired
	private AccountMapper accountMapper;
	
	
	@Test
	private void tdd() {
		Account account = accountMapper.findByEmail("yjk98051@naver.com");
		
		log.info("account : {}", account);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
