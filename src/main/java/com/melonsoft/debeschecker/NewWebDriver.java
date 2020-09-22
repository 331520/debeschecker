/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melonsoft.debeschecker;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author ArbuzovA
 */
class NewWebDriver {

    final static Logger logger = Logger.getLogger(Main.class);
    public WebDriver driver() {
        //create new Crome driver
        logger.debug("Try to create driver");
        WebDriver driver = null;
        try {
            WebDriverManager.chromedriver().setup();
            //WebDriverManager.chromedriver().forceDownload().setup();

            //ChromeDriver driver = new ChromeDriver();
            //some options
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
            //options.addArguments("user-data-dir=userdata");
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            driver = new ChromeDriver(capabilities);
            logger.debug("Driver created");
        } catch (Exception e) {
            System.out.println("Error create driver : " + e.getLocalizedMessage());
            logger.debug("Error create driver : " + e.getLocalizedMessage());
        }
        return (ChromeDriver) driver;
    }
}