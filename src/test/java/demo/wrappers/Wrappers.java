package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

     public static void elementclick(WebDriver driver, WebElement element){
      try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();",element);
        } catch (Exception e) {
         e.printStackTrace();
        }
       }

       public static void enterText(WebElement element ,String text){
        try {
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
      }

      public static void clickrightarrowButton(WebDriver driver, int max, WebElement rightarrow) {
        for (int i = 0; i < max; i++) {
        try {
            if (rightarrow.isDisplayed()) {
                rightarrow.click();
                Thread.sleep(2000);
            } else{
                break;
            } 
        } catch (Exception e) {
            e.printStackTrace();
         }
        }
    }
      public static long convertToNumbericvalue(String Value){
         Value=Value.trim().toUpperCase();
         if (Value.endsWith(" VIEWS")) {
            Value = Value.substring(0, Value.length() - 6).trim();
        }

         char lastChar = Value.charAt(Value.length()-1);
         int multiplier =1;
         switch(lastChar) {
           case 'K':
              multiplier = 1000;
              break;
            case 'M':
               multiplier = 1000000;
              break;
            case 'B':
              multiplier = 1000000000;
               break;
              default:
              if(Character.isDigit(lastChar)) {
                return Long.parseLong(Value);
              }
              throw new IllegalArgumentException("Invalid format: "+Value);
         }

         String numericPart = Value.substring(0,Value.length()-1);
         double number = Double.parseDouble(numericPart);

         return (long) (number*multiplier);

      }

     }

    