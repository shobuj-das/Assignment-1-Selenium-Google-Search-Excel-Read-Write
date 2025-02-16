package org;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GoogleSearch extends DriverSetup{
    HandleExcelSheet handleExcelSheet = new HandleExcelSheet();
    public String URL = "https://google.com";
    By searchBoxLocator = By.name("q");

    @Test
    public void googleSuggestions() throws Exception {
        // get the sheet name
        String sheetName = handleExcelSheet.getDays();

        // get the search keywords from the excel file
        List<String> keyWords = new ArrayList<>(handleExcelSheet.readExcelSheet(sheetName));

        System.out.println("sheet: "+sheetName);
        for(String s:keyWords){
            System.out.println("Keywords: "+s);
        }

        driver.get(URL);
        for (int i=0; i<keyWords.size(); i++) {
            String searchWord = keyWords.get(i);
            if (searchWord == null || searchWord.trim().isEmpty()) {
                System.out.println("Skipping empty value");
                continue;
            }
            System.out.println("Search word: " + searchWord);
            driver.findElement(searchBoxLocator).clear();
            driver.findElement(searchBoxLocator).sendKeys(searchWord);
            Thread.sleep(2000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(searchBoxLocator));

            List<WebElement> suggestions = new ArrayList<>();
            suggestions = driver.findElements(By.className("wM6W7d"));

            String shortestSugestion = null;
            String longestSuggestion = null;

            if (suggestions.isEmpty()) {
                System.out.println("No suggestions found");
            } else {
                longestSuggestion = "";
                shortestSugestion = null;

                for (WebElement suggestion : suggestions) {
                    String text = suggestion.getText();
                   // System.out.println("--- Suggestion: " + text);
                    if (!text.isEmpty() && text != null) {
                        if (text.length() > longestSuggestion.length())
                            longestSuggestion = text;

                        if (shortestSugestion == null || text.length() < shortestSugestion.length())
                            shortestSugestion = text;
                    }
                }
            }
            handleExcelSheet.writeToExcelSheet(sheetName, i, longestSuggestion, shortestSugestion);
        }
    }
}
