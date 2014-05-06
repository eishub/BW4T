package client;

import static org.junit.Assert.assertEquals;
import client.Testing;
import org.junit.Test;

public class TestingTest {

	@Test
	public void tester() {
		assertEquals("Test",Testing.returnTest());
	}
	
}
