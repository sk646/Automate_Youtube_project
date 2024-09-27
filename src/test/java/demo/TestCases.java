package demo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.idealized.Javascript;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import demo.utils.ExcelDataProvider;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider{ // Lets us read the data
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);
                
                driver.manage().window().maximize();
                
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
         @Test(enabled=true)
        public void testCase01() throws InterruptedException{
           System.out.println("StartTestcase 01" );    
           long start = System.currentTimeMillis();
            driver.get("https://www.youtube.com/");
            String URL = driver.getCurrentUrl();
            System.out.println(URL);
          
            WebElement abt =driver.findElement(By.xpath("//a[text()='About']"));
            Wrappers.elementclick(driver, abt);
            String actualUrl = driver.getCurrentUrl();
            String ExpectedUrl= "https://about.youtube/";
            assertEquals(actualUrl, ExpectedUrl);
            Thread.sleep(2000);
            System.out.println(actualUrl);
            if(actualUrl.contains("about")){
                System.out.println("Testcase Pass");
            }else{
                System.out.println("Testcase fail");
            }

            List<WebElement> messageElements = driver.findElements(By.xpath("//main[@id='content']//following::p[contains(@class,'lb-font-display-3 lb-font-color-text-primary')]"));
            for(WebElement messageElement: messageElements) { 
            String txt = messageElement.getText();
            System.out.println(txt);
            }
            
            driver.navigate().back();
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            System.out.println("Testcase 01 took: " + (float) elapsedTime / 1000 + "seconds");
            System.out.println("EndTestcase 01" );
            
        }
        
        @Test(enabled=true)
        public void testCase02() throws InterruptedException{
        System.out.println("StartTestcase 02" );
            long start = System.currentTimeMillis();
            driver.get("https://www.youtube.com/");
            Thread.sleep(3000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement movieElement = driver.findElement(By.xpath("//yt-formatted-string[text()='Films']"));
            wait.until(ExpectedConditions.elementToBeClickable(movieElement));
            Wrappers.elementclick(driver, movieElement);
            Thread.sleep(3000);
            JavascriptExecutor je = (JavascriptExecutor)driver;
            je.executeScript("window.scrollBy(0,300)", "");
            WebElement arrowbuttonElement = driver.findElement(By.xpath("//span[contains(text(),'Top selling')]//ancestor::div[@id='dismissible']//div[@id='right-arrow']//button"));
                // Wrappers.elementclick(driver, arrowbuttonElement);
            Wrappers.clickrightarrowButton(driver, 8, arrowbuttonElement);
                
            List<WebElement> Parentmovieslists = driver.findElements(By.xpath("//span[contains(text(),'Top selling')]//ancestor::div[@id='dismissible']//ytd-grid-movie-renderer[contains(@class, 'horizontal-list-renderer')]"));
               if (!Parentmovieslists.isEmpty()) {
                   WebElement endmovieElement = Parentmovieslists.get(Parentmovieslists.size() - 1);

                    String filmcategory = endmovieElement.findElement(By.xpath(".//span[contains(@class,'renderer-metadata')]")).getText();
                    String filmrating = endmovieElement.findElement(By.xpath(".//div[contains(@class,'badge-style-type-simple style-scope')]//p")).getText();

                     SoftAssert soft = new SoftAssert();

                     if (filmrating.contains("A")) {
                        System.out.println("The movie is rated 'A' for Mature.");
                        } else {
                        System.out.println("The movie is not rated 'A'. current rating is: " + filmrating);
                        }

                soft.assertFalse(filmrating.contains("A"), "The movie rating is not marked 'A' for Mature.");
                  Thread.sleep(3000);
                        
                soft.assertTrue(filmcategory.contains("Animation"),
                "Movie category is not in expected categories (Comedy, Animation, Drama).");
                soft.assertAll();
                }
                  
                long end = System.currentTimeMillis();
                long elapsedTime = end - start;
                System.out.println("Testcase 02 took: " + (float) elapsedTime / 1000 + "seconds");
                System.out.println("EndTestcase 02" );
        }

        @Test(enabled=true)
        public void testCase03() throws InterruptedException{
        System.out.println("StartTestcase 03" );
         long start = System.currentTimeMillis();
         driver.get("https://www.youtube.com/");
         Thread.sleep(3000);
         System.out.println("wait 2");
                
        WebElement musicElement = driver.findElement(By.xpath("//yt-formatted-string[text()='Music']"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(16));
         wait.until(ExpectedConditions.elementToBeClickable(musicElement));
         Wrappers.elementclick(driver, musicElement);
         Thread.sleep(3000);
         JavascriptExecutor je = (JavascriptExecutor)driver;
         je.executeScript("window.scrollBy(0,300)", "");
         WebElement arrowbutton= driver.findElement(By.xpath("//span[text()=\"India's Biggest Hits\"]//ancestor::div[@id='dismissible']//div[@id='right-arrow']//button"));
         Wrappers.clickrightarrowButton(driver, 8, arrowbutton);
         System.out.println("wait 2");

         List<WebElement> exrtremerighElement = driver.findElements(By.xpath("//span[@id='title' and contains(text(),'Biggest Hits')]//ancestor::div[@id='dismissible']//div[contains(@class,'flex-container')]"));
         SoftAssert soft = new SoftAssert();

          if (!exrtremerighElement.isEmpty()) {
              WebElement lastplaylist = exrtremerighElement.get(exrtremerighElement.size() - 1);
   
              String titleElement = lastplaylist.findElement(By.xpath(".//h3[contains(@class,'style-scope')]")).getText();
              System.out.println("The exrtremerighElement Name is: " + titleElement);
              Thread.sleep(2000);
              System.out.println("Wait 3");
   
              String trackcount = lastplaylist.findElement(By.xpath(".//p[@id='video-count-text']")).getText();
              System.out.println("The Track count number is: " + trackcount);
              int count = Integer.parseInt(trackcount.replaceAll("[^0-9]", ""));
              System.out.println(count);
              soft.assertTrue(count<=50,"lastplaylist" +titleElement+"minimum 50 tracks");
              soft.assertAll();
              }
              long end = System.currentTimeMillis();
              long elapsedTime = end - start;
              System.out.println("Testcase 03 took: " + (float) elapsedTime / 1000 + "seconds");
              System.out.println("EndTestcase 03" );
        }

        @Test(enabled=true)
        public void testCase04() throws InterruptedException{
         System.out.println("StartTestcase 04" );
         long start = System.currentTimeMillis();
         WebElement newsElement = driver.findElement(By.xpath("(//yt-formatted-string[text()='News'])[1]"));
         Wrappers.elementclick(driver, newsElement);
         Thread.sleep(3000);
         JavascriptExecutor je = (JavascriptExecutor)driver;
         je.executeScript("window.scrollBy(0,300)", "");
         WebElement latestElement = driver.findElement(By.xpath("//span[text()='Latest news posts']"));
         Wrappers.elementclick(driver, latestElement);

         List<WebElement> listsElements = driver.findElements(By.xpath("//span[text()='Latest news posts']//ancestor::div[@id='dismissible']//div[@id='header']/../div[@id='body']"));
         for(int i=0;i<=2;i++){
         String txt = listsElements.get(i).getText();
         System.out.println(txt);
         }
         List<WebElement> titleElements = driver.findElements(By.xpath("//span[text()='Latest news posts']//ancestor::div[@id='dismissible']//div[@id='header']/..//div[@id='author']"));
         for(int i=0;i<=2;i++){
          String titletxt = titleElements.get(i).getText();
          System.out.println(titletxt );
         }
         Thread.sleep(3000);       
         List<WebElement> likecouElements = driver.findElements(By.xpath("//span[text()='Latest news posts']//ancestor::div[@id='dismissible']//div[@id='header']/..//span[@id='vote-count-middle']"));
            for(int i=0;i<=2;i++){
             String list = likecouElements.get(i).getText(); 
             Integer lis = Integer.valueOf(list); 
             Thread.sleep(3000);  
          List<Integer> likecount = new ArrayList<>();
          likecount.add(lis);
          int Sum =0;
          for(int k=0;k<likecount.size();k++){
          Sum+=likecount.get(k);
          System.out.println(Sum);
          }
         }
         long end = System.currentTimeMillis();
         long elapsedTime = end - start;
         System.out.println("Testcase 04 took: " + (float) elapsedTime / 1000 + "seconds");
         System.out.println("EndTestcase 04");    
         }
        
         @Test(enabled=true,dataProvider="excelData")
        public void testCase05(String searchtexts) throws InterruptedException{
           System.out.println("StartTestcase 05" );
           long start = System.currentTimeMillis();
           driver.get("https://www.youtube.com/");
           WebElement searchElement = driver.findElement(By.xpath("//input[@id='search']"));
           Wrappers.enterText(searchElement, searchtexts);
           searchElement.sendKeys(Keys.ENTER); 
           Thread.sleep(3000);

           long totalViews = 0;
           long targetViews = 100000000;
           
           while (totalViews < targetViews) {
            List<WebElement> videoslikes = driver.findElements(By.xpath(
                            "//ytd-video-renderer//div[@id='metadata-line']/..//span[contains(text(), 'views')]"));

            for (WebElement videolike : videoslikes) {
                    String viewtext = videolike.getText();
                    long videoviews = Wrappers.convertToNumbericvalue(viewtext);

                    totalViews = totalViews + videoviews;

                    System.out.println("Video views : " + videoviews + "| Total View : " + totalViews);

                    if (totalViews >= targetViews) {
                            System.out.println("Reached the Traget 10Cr");
                            break;
                    }
            }
            if (totalViews < targetViews) {
                JavascriptExecutor je = (JavascriptExecutor)driver;
                je.executeScript("window.scrollBy(0,1500)", "");
                Thread.sleep(3000);
              
            }
         long end = System.currentTimeMillis();
         long elapsedTime = end - start;
         System.out.println("Testcase 05 took: " + (float) elapsedTime / 1000 + "seconds");
         System.out.println("EndTestcase 05");    

        }

 }
}
