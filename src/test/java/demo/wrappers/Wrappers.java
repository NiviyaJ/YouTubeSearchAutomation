package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

    public static void clickElement(WebDriver driver, WebElement elementToClick) {
        // WebElement elementToClick = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView()", elementToClick);
        // js.executeScript("arguments[0].click()", elementToClick);
        elementToClick.click();
    }

    public static HashMap<String, String> verifyFilmCategory(WebDriver driver, By locator) {
        HashMap<String, String> movieDetails = new HashMap<>();
        try {
            WebElement topSellingContentSection = driver.findElement(locator);
            WebElement rightArrow = topSellingContentSection.findElement(By.xpath(".//div[@id='right-arrow']//button"));

            while (rightArrow.isDisplayed()) {
                clickElement(driver, rightArrow);
                Thread.sleep(2000);
            }
            WebElement rightMostMovie = topSellingContentSection
                    .findElement(By.xpath(".//ytd-grid-movie-renderer[last()]"));

            WebElement movieCategory = rightMostMovie
                    .findElement(By.xpath(".//a[contains(@class,'ytd-grid-movie-renderer')]/span"));
            String movieCategoryTxt = movieCategory.getText().replaceAll("[^a-zA-Z]", "");
            WebElement contentRating = rightMostMovie
                    .findElement(By.xpath(".//div[contains(@class,'badge-style-type-simple')]/p"));
            String contentRatingTxt = contentRating.getText();
            System.out.println(movieCategoryTxt);
            System.out.println(contentRatingTxt);
            movieDetails.put("Category", movieCategoryTxt);
            movieDetails.put("Rating", contentRatingTxt);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception occurred: " + e.getMessage());
        }
        return movieDetails;
    }

    public static HashMap<String, String> getPlaylistAndTracks(WebDriver driver, By locator) {
        HashMap<String, String> playListDetail = new HashMap<>();

        try {
            WebElement playlistContainer = driver.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({block: \"center\"})", playlistContainer);
            WebElement nonHiddenPlaylistsRightMost = playlistContainer
                    .findElement(By.xpath("(.//ytd-rich-item-renderer[not(@hidden)])[last()]"));
            String titleText = nonHiddenPlaylistsRightMost.findElement(By.tagName("h3")).getText();
            // System.out.println(titleText);
            String noOfTracks = nonHiddenPlaylistsRightMost.findElement(By.className("badge-shape-wiz__text"))
                    .getText();
            // System.out.println(noOfTracks);
            playListDetail.put("Title", titleText);
            playListDetail.put("Tracks", noOfTracks);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception occurred: " + e.getMessage());
        }
        return playListDetail;
    }

    public static void getLatestNewsDetails(WebDriver driver, By locator) {
        try {
            WebElement latestNewsComtainer = driver.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({block: \"center\"})", latestNewsComtainer);
            List<WebElement> latestNewsList = latestNewsComtainer
                    .findElements(By.xpath(".//ytd-rich-item-renderer[not(@hidden)]"));
            int totalLikes = 0;
            for (int i = 0; i < 3; i++) {
                WebElement latestNewsPost = latestNewsList.get(i);
                WebElement header = latestNewsPost.findElement(By.xpath(".//div[@id='author']//span"));
                System.out.println(header.getText());
                StringBuilder bodyText = new StringBuilder();
                List<WebElement> bodyElements = latestNewsPost
                        .findElements(By.xpath(".//yt-formatted-string[@id='home-content-text']/*"));

                for (WebElement body : bodyElements) {
                    bodyText.append(body.getText());
                }
                System.out.println(bodyText.toString() + "\n");
                String likes = latestNewsPost.findElement(By.id("vote-count-middle")).getText();
                totalLikes = totalLikes + Integer.parseInt(likes);
            }
            System.out.println("Total Likes: " + totalLikes);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    public static void search(WebDriver driver, String text) {
        try {
            WebElement searchBox = driver.findElement(By.name("search_query"));
            searchBox.clear();
            searchBox.sendKeys(text);
            // searchBox.sendKeys(Keys.ENTER);
            WebElement searchBtn = driver.findElement(By.xpath("//button[@title='Search']"));
            clickElement(driver, searchBtn);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception occurred: " + e.getMessage());
        }

    }

    public static void getViewCounts(WebDriver driver, long maxCount) {
        try {
            int videoCount = 1;
            // WebElement videoElement = driver.findElement(By.xpath("(//ytd-item-section-renderer//div[@id='contents']//ytd-video-renderer)["+(videoCount++)+"]"));
            long totalViewCount = 0;
            
            while(totalViewCount <= maxCount){
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//following-sibling::ytd-video-renderer")));
                WebElement videoElement = driver.findElement(By.xpath("(//ytd-item-section-renderer//div[@id='contents']//ytd-video-renderer)["+(videoCount++)+"]"));

                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView({block: \"center\"})", videoElement);

                WebElement videoView = videoElement.findElement(By.xpath(".//div[@id='metadata-line']/span"));
                String videoViewCountStr = videoView.getText().split(" ")[0];
                
                double currentVideoViewCount = Double.parseDouble(videoViewCountStr.replaceAll("[^0-9.]", ""));
                long multiplier = 0;
                String viewCountSuffix = videoViewCountStr.substring(videoViewCountStr.length() - 1);
                // System.out.println(videoViewCountStr);
                if(viewCountSuffix.equals("M") ){
                    multiplier = 1000000;
                }
                else if(viewCountSuffix.equals("K")){
                    multiplier = 1000;
                }
                else if(viewCountSuffix.equals("B")){
                    multiplier = 1000000000;
                }
                totalViewCount += currentVideoViewCount * multiplier;
                // System.out.println(totalViewCount >= maxCount);
                // System.out.println(totalViewCount+" : "+ maxCount);
                
                // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
                // wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//following-sibling::ytd-video-renderer")));
                // videoElement = videoElement.findElement(By.xpath(".//following-sibling::ytd-video-renderer"));
                
                Thread.sleep(1000);
            }
            //System.out.println(maxCount);
            // List<WebElement> videoViewsList = driver.findElements(By.xpath(
            //         "//ytd-search//span[contains(@class,'inline-metadata-item style-scope ytd-video-meta-block')][1]"));
            // System.out.println(videoViewsList.size());
            // JavascriptExecutor js = (JavascriptExecutor) driver;
            // for (WebElement video : videoViewsList) {
            //     js.executeScript("arguments[0].scrollIntoView({block: \"center\"})", video);
            //     Thread.sleep(1000);

            // }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception occurred: " + e.getMessage());
        }

    }
}
