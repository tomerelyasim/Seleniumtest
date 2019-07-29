package Selenium.com;

import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import java.util.Set;



public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",".\\Webdrivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        Actions action = new Actions(driver);

        //Open google website and maximize
        driver.get("https://www.google.com");
        driver.manage().window().maximize();
        driver.findElement(By.name("q")).sendKeys("Ynetnews",Keys.RETURN);

        //Enter Ynetnews website
        driver.findElement(By.xpath("//span[contains(text(),'ynetnews - Homepage')]")).click();

        //Check whether the current URL opened matches the regex ynetnews.com
        if (driver.getCurrentUrl().matches(".*ynetnews.com.*") ){
            System.out.println("The correct webpage opened!\n");
        }else {
            System.out.println("Incorrect webpage opened!\n");
        }

        //Assign weather element to weather variable and print the weather
        WebElement weather = driver.findElement(By.xpath("//span[@id='cdanwmansrch_weathertemps']"));
        System.out.println("The current weather in homepage is: " + weather.getText() + "\n");

        //Change the drop down weather to Eilat and print the weather in Eilat
        driver.findElement(By.xpath("//select[@id='cdanwmansrch_weathercitieselect']")).click();
        driver.findElement(By.xpath("//option[contains(text(),'Eilat')]")).click();
        driver.findElement(By.xpath("//a[@id='main_search_weather_link']")).click();
        WebElement weatherInEilat = driver.findElement(By.xpath("//span[@id='cdanwmansrch_weathertemps']"));
        System.out.println("The current weather in Eilat is: " + weatherInEilat.getText() + "\n");

        //Change page resolution
        driver.manage().window().setSize(new Dimension(1920,1080));

        //Open an article
        ((JavascriptExecutor)driver).executeScript("scroll(0,600)");
        driver.findElement(By.cssSelector("body.english_site.INDlangdirLTR.INDpositionLeft.INDDesktop.INDChrome:nth-child(2) div.area.content.no_trajectory:nth-child(4) div.block.B6:nth-child(1) div.block.B6:nth-child(2) div.block.B4:nth-child(1) div.element.B4.ghcite.noBottomPadding:nth-child(1) div.str3s.str3s_small.str3s_type_small div.cell.cwide.layout1:nth-child(2) > a:nth-child(1)")).click();
        ((JavascriptExecutor)driver).executeScript("scroll(0,400)");

        //Check if "send to friend" link exists
        boolean isEnable = driver.findElement(By.xpath("//span[@class='ASLtextIcon'][contains(text(),'send to friend')]")).isEnabled();
        if (isEnable){
            System.out.println("Send to friend link exists\n");
        }else {
            System.out.println("Send to friend link doesn't exists\n");
        }

        //Open send to friend link and fill the information
        driver.findElement(By.xpath("//span[@class='ASLtextIcon'][contains(text(),'send to friend')]")).click();
        String primaryURL = driver.getWindowHandle();
        Set<String> allHandles = driver.getWindowHandles();

       for(String sub:driver.getWindowHandles()) {
           driver.switchTo().window(sub);
       }

        driver.findElement(By.id("txtTo")).sendKeys("friend@email.com");
        driver.findElement(By.id("txtFromName")).sendKeys("Tomer Elyasim");
        driver.findElement(By.id("txtFromAddress")).sendKeys("mymail@email.com");
        driver.findElement(By.id("txtRemarks")).sendKeys("This is a test");
        Thread.sleep(2000);
        driver.close();
        driver.switchTo().window(primaryURL);
        driver.navigate().back();
        driver.manage().window().maximize();
        ((JavascriptExecutor)driver).executeScript("scroll(0,100)");

        //Bonus part
        WebElement updatesIframe = driver.findElement(By.xpath("//iframe[@src='/Ext/Comp/Ticker/Dhtml_Flash_Ticker/0,12114,L-3089-253-150,00.html?js=1']"));
        driver.switchTo().frame(updatesIframe);

        //Assigning variables to some update in update section
        String someUpdate1 = driver.findElement(By.xpath("//div[@id='bTicker']")).getAttribute("style");
        Thread.sleep(3000);
        String someUpdate2 = driver.findElement(By.xpath("//div[@id='bTicker']")).getAttribute("style");

        isUpdateMatch(someUpdate1,someUpdate2);

        //Hovering with the mouse on the updates section in order to stop it
        WebElement mouseHoverUpdate = driver.findElement(By.xpath("//div[@id='bTicker']//a[contains(@class,'tickerTextA')][contains(text(),'Cyprus court delays hearing for Brit in false rape')]"));
        action.moveToElement(mouseHoverUpdate).perform();
        someUpdate1 = driver.findElement(By.xpath("//div[@id='bTicker']")).getAttribute("style");
        someUpdate2 = driver.findElement(By.xpath("//div[@id='bTicker']")).getAttribute("style");

        isUpdateMatch(someUpdate1,someUpdate2);

        driver.quit();

   }

   public static void isUpdateMatch(String a, String b ){
       if (a.equalsIgnoreCase(b)){
           System.out.println("Updates section is not moving!\n");
       }else {
           System.out.println("Updates section is moving!\n");
       }
    }

}
