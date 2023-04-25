package com.musinsa.product;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.musinsa.fashionBoard.item.Item;
import com.musinsa.fashionBoard.mapper.ItemMapper;

import groovy.util.logging.Log4j2;

@SpringBootTest
@Log4j2
class MusinsaTeamProjectApplicationTests {

	@Autowired
	ItemMapper itemMapper;
	
	@Disabled
//	@Test
//	void Test() {
//		Item item = Item.builder().name(null).price(1).count(0).seller_id(12).build();
//		itemMapper.upload(item);
//		System.out.println(item);
//	}
	
	
	@Test
	void contextLoads() {
	}

}
