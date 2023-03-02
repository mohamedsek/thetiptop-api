package fr.thetiptop.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TheTipTopAppApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void testMyAppLogic() {
		Assertions.assertTrue(1==1, "1 equals 1");
	}
}
