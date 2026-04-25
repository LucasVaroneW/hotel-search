package com.hotelsearch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.hotelsearch.domain.port.out.SearchEventPublisher;
import com.hotelsearch.domain.port.out.SearchRepositoryPort;

@SpringBootTest
@ActiveProfiles("test")
class HotelSearchApplicationTests {

	@MockitoBean
	private SearchEventPublisher searchEventPublisher;

	@MockitoBean
	private SearchRepositoryPort searchRepositoryPort;

	@Test
	void contextLoads() {
	}

}
