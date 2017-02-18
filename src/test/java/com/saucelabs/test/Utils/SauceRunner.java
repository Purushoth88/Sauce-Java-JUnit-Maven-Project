package com.saucelabs.test.Utils;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

/**
 * @author Ah0144719
 *
 */
public class SauceRunner implements SauceOnDemandSessionIdProvider {
	
    private static String sessionId;
    public static String username = AppVariables.get("username");
    public static String accesskey = AppVariables.get("accesskey");
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(username, accesskey);
    public @Rule SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);
    public @Rule TestName testName = new TestName();
    public String getSessionId() { return sessionId; }
    static RemoteWebDriver wd;
	
	public static Object sauceCapabilities() throws Exception {

        /* System.setProperty("http.proxyHost", proxyHost);            
        System.setProperty("http.proxyPort", proxyPort)            
        System.setProperty("http.proxyUser", userName);            
        System.setProperty("http.proxyPassword", password); */

		DesiredCapabilities capabilities = new DesiredCapabilities();
        RemoteWebDriver wd;

        capabilities.setCapability(CapabilityType.BROWSER_NAME, AppVariables.get("Browser"));
        capabilities.setCapability(CapabilityType.VERSION, AppVariables.get("Version"));
        //capabilities.setCapability("deviceName", deviceName);
        //capabilities.setCapability("device-orientation", deviceOrientation);
        capabilities.setCapability(CapabilityType.PLATFORM, AppVariables.get("OS"));

        String methodName = name.getMethodName();
        capabilities.setCapability("name", "SauceScriptsExecution");
		String seleniumURI = buildSauceUri();
		wd =  new RemoteWebDriver(
                new URL("https://" + username+ ":" + accesskey + seleniumURI +"/wd/hub"),
                capabilities);
		wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		sessionId = wd.getSessionId().toString();

		System.out.println("Sauce Launcher is launched successfully");
		return wd;
	}
	
	public static String buildSauceUri() {
        String seleniumURI = "@ondemand.saucelabs.com:443";
        String seleniumPort = System.getenv("SELENIUM_PORT");
        String seleniumHost = System.getenv("SELENIUM_HOST");
        if (seleniumPort != null &&
                seleniumHost != null &&
                !seleniumHost.contentEquals("ondemand.saucelabs.com")) {
            //While running in CI, if Sauce Connect is running the SELENIUM_PORT env var will be set.
            //use SC relay port
            seleniumURI = String.format("@localhost:%s", seleniumPort);

        }
        return seleniumURI;
    }
	
    @Rule
    public static TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };

}
