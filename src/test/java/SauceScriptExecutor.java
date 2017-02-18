import org.junit.runner.RunWith;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import com.saucelabs.test.PremierQCScriptTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	PremierQCScriptTest.class,
})

public class SauceScriptExecutor {

	/*public static void main(String[] args) {
	      Result result = JUnitCore.runClasses(PremierQCScriptTest.class);

	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
			
	      System.out.println(result.wasSuccessful());
	   }*/
}
