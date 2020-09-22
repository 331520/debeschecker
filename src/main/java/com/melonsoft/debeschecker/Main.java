/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melonsoft.debeschecker;


import com.melonsoft.debeschecker.po.DebesPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author 33152
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    final static Logger logger = Logger.getLogger(Main.class);
    private static WebDriver driver;



    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        logger.info("It works");
        NewWebDriver newWebDriver = new NewWebDriver();
        //create new driver
        driver = newWebDriver.driver();
        DebesPage debesPage = new DebesPage(driver);
        debesPage.openPage();
        debesPage.clickDetails();
        debesPage.loginToDebessman();
        debesPage.clickEvents();
        debesPage.setFilter("WALKEDA");
        Thread.sleep(60000);
        debesPage.gelFullListOfItems();
        driver.close();
        driver.quit();
    }

}
