package helpers;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;


public class RetryAnalyzer implements IRetryAnalyzer {

    int counter = 0;
    //Number of retries
    int limit = 0;

    @Override
    public boolean retry(ITestResult result) {

        if(counter < limit) {
            counter++;
            return true;
        }
        return false;
    }
}
