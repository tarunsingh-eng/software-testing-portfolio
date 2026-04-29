import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JUnitOrderDemo {

	@BeforeAll
	void beforeAll() { System.out.println("BeforeAll"); } 

	@BeforeEach
	void beforeEach() { System.out.println("BeforeEach"); }

	@Test
	void t1() { System.out.println("Test1"); }

	@Test
	void t2() { System.out.println("Test2"); }

	@AfterEach
	void afterEach() { System.out.println("AfterEach"); }	

	@AfterAll
	void afterAll() { System.out.println("AfterAll"); } 
}