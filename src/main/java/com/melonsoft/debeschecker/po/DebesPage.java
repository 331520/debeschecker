package com.melonsoft.debeschecker.po;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.List;


public class DebesPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    final static Logger logger = Logger.getLogger(DebesPage.class);

    @FindBy(id = "details-button")
    WebElement detailsButton;

    @FindBy(id = "final-paragraph")
    WebElement finalParagraphUrl;

    @FindBy(name = "client_id")
    WebElement clientIdField;

    @FindBy(name = "client_secret")
    WebElement passwordField;

    @FindBy(css = "button[type$='submit']")
    WebElement loginButton;

    @FindBy(xpath = "//*[text()='User Activity']")
    WebElement userActivityLabel;

    @FindBy(linkText = "Events")
    WebElement eventsLink;

    @FindBy(css = "input[placeholder='Filter']")
    WebElement filterField;

    @FindBy(css = "[class^='datepicker-input']")
    WebElement startDatepicker;

    @FindBy(linkText = "PAYLOAD")
    WebElement payloadTab;

    @FindBy(css = "[class^='event-list-item__service search-hit']")
    WebElement eventServiceTime;

/*
    @FindBy(css = "[class^='event-list-item__service search-hit']")
    List<WebElement> eventServiceTimeArray;
*/

    @FindBy(css = "[class^='event-list-item__service']")
    List<WebElement> eventServiceTimeArray;

    @FindBy(css = "[class^='chroma']")
    WebElement  eventchromaTxt;

    @FindBy(css = "[class^='event-list-item event-active']")
    WebElement ventListItemEventActive;


    //Agrreement KC_Start_Date Id
    @FindBy(css = "div[class$='event-active']")
    WebElement activeElement;

    public DebesPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, 30);
        PageFactory.initElements(driver, this);
    }

    public DebesPage openPage() {
        driver.get("https://fikc-m4man01.konecranes.com/login");
        return this;
    }

    public DebesPage clickDetails() {
        wait.until(ExpectedConditions.elementToBeClickable(detailsButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(finalParagraphUrl)).click();
        return this;
    }

    public DebesPage loginToDebessman() {
        wait.until(ExpectedConditions.elementToBeClickable(clientIdField)).click();
        wait.until(ExpectedConditions.elementToBeClickable(clientIdField)).sendKeys("xarbuzol");
        wait.until(ExpectedConditions.elementToBeClickable(passwordField)).click();
        wait.until(ExpectedConditions.elementToBeClickable(passwordField)).sendKeys("83rX1FHuclylcDap");
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        wait.until(ExpectedConditions.visibilityOf(userActivityLabel));
        return this;
    }

    public DebesPage clickEvents() {
        wait.until(ExpectedConditions.elementToBeClickable(eventsLink)).click();
        wait.until(ExpectedConditions.elementToBeClickable(filterField));
        return this;
    }

    public DebesPage setStartDate(String t) {
        wait.until(ExpectedConditions.elementToBeClickable(startDatepicker)).click();
        startDatepicker.sendKeys(Keys.END);
        for (int i = 0; i < 20; i++) {
            startDatepicker.sendKeys(Keys.BACK_SPACE);
        }
        startDatepicker.sendKeys(t);
        return this;
    }

    public DebesPage setFilter(String filter) {
        wait.until(ExpectedConditions.elementToBeClickable(filterField)).click();
        filterField.clear();
        filterField.sendKeys(filter);
        wait.until(ExpectedConditions.elementToBeClickable(payloadTab)).click();
        return this;
    }

    public DebesPage gelFullListOfItems() throws InterruptedException {
        //event-list-item
        wait.until(ExpectedConditions.visibilityOf(ventListItemEventActive)).click();
        //wait.until(ExpectedConditions.elementToBeClickable(eventServiceTime)).click();
        System.out.println("eventServiceTimeArray.size() : " + eventServiceTimeArray.size());
        //wait.until(ExpectedConditions.visibilityOf(eventServiceTime));

        Actions mouseHover = new Actions(driver);
        mouseHover.moveToElement(eventServiceTime);

        int prvEventArraySize = 0;

        for (int i = 0; i < 1000000; i++) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", eventServiceTimeArray.get(eventServiceTimeArray.size()-1));
            Thread.sleep(1500);
            System.out.println("eventServiceTimeArray.size() : " + eventServiceTimeArray.size());
            System.out.println("prvEventArraySize : " + prvEventArraySize);
            if (prvEventArraySize < eventServiceTimeArray.size()){
                prvEventArraySize = eventServiceTimeArray.size();
            } else {
                break;
            }
        }

        for (int i = prvEventArraySize-1; i > 0 ; i--) {
            System.out.println("Event : " + i);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", eventServiceTimeArray.get(i));
            Thread.sleep(1000);
            wait.until(ExpectedConditions.elementToBeClickable(eventServiceTimeArray.get(i))).click();
            if (eventServiceTimeArray.get(i).getText().contains("SLIM_PROD HttpTraceEvent") & !eventServiceTimeArray.get(i).getText().contains("faultcodes")){
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(payloadTab)).click();
                    wait.until(ExpectedConditions.visibilityOf(eventchromaTxt));
                    if (eventchromaTxt.getText().toLowerCase().contains("powra")){
                        System.out.println("Check : " + eventServiceTimeArray.get(i).getText());
                        wait.until(ExpectedConditions.visibilityOf(activeElement));
                        //System.out.println("Check : " +activeElement.getText());
                        //System.out.println(eventchromaTxt.getText());
                        logger.info("Start event =====================================================>");
                        logger.info("Check : " + activeElement.getText());
                        logger.info("Payload : " + eventchromaTxt.getText());
                        logger.info("End event    <=====================================================");
                        logger.info("\r\n \r\n \r\n");

                    }
                }catch (Exception e){
                    System.out.println("Error click PAYLOAD " + e.getMessage());
                    logger.info("Error click PAYLOAD. Check : " +activeElement.getText());
                }
            }
        }
        return this;
    }

}
