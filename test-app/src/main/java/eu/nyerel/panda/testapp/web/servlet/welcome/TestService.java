package eu.nyerel.panda.testapp.web.servlet.welcome;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class TestService {

	public String makeInternalCalls(int numberOfCalls) {
		return recursiveInternalCall(numberOfCalls);
	}

	public String recursiveInternalCall(int number) {
		long randomWait = Math.round(1 + Math.random() * 20);
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() < (start + randomWait)) {
		}

		if (number > 0) {
			return recursiveInternalCall(number - 1);
		}
		return "finished";
	}

	public void randomWait(long max) {
		waitFor(Math.round(Math.random() * max));
	}

	public void waitFor(long millis) {
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() < (start + millis)) {

		}
	}

}
