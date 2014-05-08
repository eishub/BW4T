package server;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import server.EISEnvironment;

public class EISEnvironmentTest {

	/**
	 * An example test to show how you can write tests for piece that are
	 * written.
	 */
	@Test
	public void requiredVersionTest() {
		EISEnvironment testEnvironment = new EISEnvironment();
		assertEquals("0.3", testEnvironment.requiredVersion());
	}

}
