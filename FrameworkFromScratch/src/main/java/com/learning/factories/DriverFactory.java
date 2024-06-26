package com.learning.factories;

import com.learning.enums.ConfigProperties;
import com.learning.util.PropertiesUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DriverFactory {

    public static WebDriver getDriver(String browser) throws MalformedURLException {
        WebDriver driver = null;
        String runmode = PropertiesUtil.getValue(ConfigProperties.REMOTE);

        // String isRemote = "no";
        String isRemote = System.getProperty("isRemote", "no");
        //run command mvn clean test -DisRemote=no

        if (browser.equalsIgnoreCase("Chrome")) {
            if (runmode.equalsIgnoreCase("yes")) {
                ChromeOptions opt = new ChromeOptions();
                opt.setCapability("BrowserName", Browser.CHROME);

                driver = new RemoteWebDriver(new URL(PropertiesUtil.getValue(ConfigProperties.SELENIUMGRIDLOCALHOSTURL)), opt);

            } else {
                System.out.println("should come here");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");

                if(isRemote.equalsIgnoreCase("yes")){
                    // options.addArguments("--headless"); 
                    //in recetn 4.18.0 update headless is updated to headless new
                    options.addArguments("--headless=new");
                }

                    // WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(options);
                //sometimes there might be some issue in browser invocation here aswell, so throw exception commonly in init() method
            }
        } else {//edge
            if (runmode.equalsIgnoreCase("yes")) {
                EdgeOptions opt = new EdgeOptions();
                opt.setCapability("BrowserName", Browser.EDGE);

                driver = new RemoteWebDriver(new URL(PropertiesUtil.getValue(ConfigProperties.SELENIUMGRIDLOCALHOSTURL)), opt);

            } else {

                EdgeOptions options = new EdgeOptions();

                if(isRemote.equalsIgnoreCase("yes"))
                    options.addArguments("--headless");

                driver = new EdgeDriver(options);
            }

        }

        return driver;
    }
}
