import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

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
	void afterEach() { System.out.println("After_Each"); }	

	@AfterAll
	void afterAll() { System.out.println("AfterAll"); } 
}