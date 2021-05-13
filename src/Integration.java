import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Integration {
    public Utility utility = new Utility();
    public AndroidDriver driver;
    public String number;
    public String otp;
    public String phoneNumber = "8562954340";
    public Long timeout = 10L;

    public void Run() throws InterruptedException, MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appPackage", "com.gojek.app.staging");
        desiredCapabilities.setCapability("appActivity", "com.gojek.home.splash.GojekSplashActivity");
        desiredCapabilities.setCapability("appWaitActivity", "com.gojek.app.authui.uiflow.onboarding.AuthOnBoardingActivity");
        desiredCapabilities.setCapability("avdLaunchTimeout", 300000);
        desiredCapabilities.setCapability("adbExecTimeout", 600000);
        desiredCapabilities.setCapability("automationName", "UiAutomator2");
        desiredCapabilities.setCapability("useKeystore", false);
        desiredCapabilities.setCapability("ignoreHiddenApiPolicyError", true);
        desiredCapabilities.setCapability("appWaitDuration", 60000);
        desiredCapabilities.setCapability("newCommandTimeout", 180);
        desiredCapabilities.setCapability("noReset", false);
        desiredCapabilities.setCapability("autoGrantPermissions", true);
        URL remoteUrl = new URL("http://0.0.0.0:4723/wd/hub");

        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
//        TimeUnit.SECONDS.sleep(timeout);

        //login
        driver.findElementById("com.gojek.app.staging:id/ll_button_container").click();
//        TimeUnit.SECONDS.sleep(timeout);
        driver.findElementById("com.gojek.app.staging:id/input_phone_number").sendKeys(phoneNumber);
//        TimeUnit.SECONDS.sleep(timeout);
        driver.findElementById("com.gojek.app.staging:id/circular_btn").click();
//        TimeUnit.SECONDS.sleep(timeout);

        //otp
        number = utility.OTP(phoneNumber).replaceAll("[^0-9]", "");

        if (number.length() > 4) {
            otp = number.substring(number.length() - 4);
        }

        driver.findElementById("com.gojek.app.staging:id/pin_input_edit_text").sendKeys(otp);
//        TimeUnit.SECONDS.sleep(timeout);

        driver.findElementById("com.gojek.app.staging:id/circular_btn").click();
//        TimeUnit.SECONDS.sleep(timeout);

        driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.widget.LinearLayout").click();
//        TimeUnit.SECONDS.sleep(timeout);
//        TimeUnit.SECONDS.sleep(timeout);

        goRideXPath();
        goFoodDeeplink();

        //logout
        driver.findElementById("com.gojek.app.staging:id/iv_profile_image").click();
//        TimeUnit.SECONDS.sleep(timeout);

        driver.findElementById("com.gojek.app.staging:id/aloha_tooltip_action").click();
//        TimeUnit.SECONDS.sleep(timeout);

        this.scrollAndClickText("Log out");
//        TimeUnit.SECONDS.sleep(timeout);

        driver.navigate().back();

        driver.quit();
    }

    public void scrollAndClickID(String id) {
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceIdMatches(\"" + id + "\").instance(0))").click();
    }

    public void scrollAndClickText(String visibleText) {
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + visibleText + "\").instance(0))").click();
    }

    public void goRideXPath() throws InterruptedException {
        driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.widget.FrameLayout/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[1]/android.widget.FrameLayout/android.widget.ImageView").click();
        driver.findElementById("com.gojek.app.staging:id/ll_button_container").click();
        TimeUnit.SECONDS.sleep(timeout);
        driver.navigate().back();
    }

    public void goFoodDeeplink() throws InterruptedException {
        driver.get( "gojek://gofood");
        TimeUnit.SECONDS.sleep(timeout);
        driver.navigate().back();
    }
}
