import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	@Test
	public void createAndRetrieveWord() {
	    // Create a new user and save it
	    new Word("bti", "a company that advances collaboration").save();
	    
	    // Retrieve the user with email address bob@gmail.com
	    Word bti = Word.find("byName", "bti").first();
	    
	    // Test 
	    assertNotNull(bti);
	    assertEquals("bti", bti.name);
	}

}
