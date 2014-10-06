package eu.nyerel.panda.testapp.web.servlet.welcome;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class TestService {

	public String makeInternalCalls(int numberOfCalls, boolean withDelays) {
		return recursiveInternalCall(numberOfCalls, withDelays);
	}

	public String recursiveInternalCall(int number, boolean withDelays) {
		if (withDelays) {
			waitFor(10);
		}
		if (number > 0) {
			return recursiveInternalCall(number - 1, withDelays);
		}
		return "finished";
	}

	public void waitFor(long millis) {
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() < (start + millis)) {

		}
	}

}
