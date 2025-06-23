package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        @Test
        public void testCase01() throws InterruptedException {
                driver.get("https://www.youtube.com/");
                Assert.assertTrue(driver.getCurrentUrl().contains("youtube"));
                WebElement aboutLink = driver.findElement(By.linkText("About"));
                Wrappers.clickElement(driver, aboutLink);
                Thread.sleep(2000);
                List<WebElement> aboutTexts = driver.findElements(By.className("lb-font-color-text-primary"));
                for (WebElement elem : aboutTexts) {
                        System.out.println(elem.getText());
                }
        }

        @Test
        public void testCase02() throws InterruptedException {
                driver.get("https://www.youtube.com/");
                // Assert.assertTrue(driver.getCurrentUrl().contains("youtube"));
                WebElement moviesLink = driver.findElement(By.xpath("//a[@title='Films' or @title='Movies']"));
                Wrappers.clickElement(driver, moviesLink);

                By topSellingMoviesLocator = By.xpath(
                                "//span[text()='Top selling']//ancestor::div[contains(@class,'grid-subheader')]/following-sibling::div[@id='contents']");
                HashMap<String, String> movieDetail = Wrappers.verifyFilmCategory(driver, topSellingMoviesLocator);
                Thread.sleep(2000);
                SoftAssert sa = new SoftAssert();
                sa.assertTrue(movieDetail.get("Rating").equals("U/A"), "The movie is not marked “A” for Mature");
                String category = movieDetail.get("Category");
                boolean status = (category.equals("Comedy") || category.equals("Drama")
                                || category.equals("Animation"));
                sa.assertTrue(status, "The movie category does not exists ex: \"Comedy\", \"Animation\", \"Drama\".");
                sa.assertAll();
        }

        @Test
        public void testCase03() throws InterruptedException {
                driver.get("https://www.youtube.com/");
                WebElement musicLink = driver.findElement(By.xpath("//a[@title='Music']"));
                Wrappers.clickElement(driver, musicLink);

                HashMap<String, String> playlistData = Wrappers.getPlaylistAndTracks(driver,
                                By.xpath("(//div[@id='contents-container'])[1]"));
                System.out.println("Title: " + playlistData.get("Title"));
                System.out.println("No. of Tracks: " + playlistData.get("Tracks"));
                SoftAssert sa = new SoftAssert();
                int noOfTracks = Integer.parseInt(playlistData.get("Tracks").replaceAll("[^0-9]", ""));
                sa.assertTrue(noOfTracks <= 50, "The number of tracks listed is not less than or equal to 50");
                sa.assertAll();
                Thread.sleep(2000);
        }

        @Test
        public void testCase04() throws InterruptedException {
                driver.get("https://www.youtube.com/");
                WebElement newsTabLink = driver.findElement(By.xpath("//a[@title='News']"));
                Wrappers.clickElement(driver, newsTabLink);
                By newContainerLocator = By.xpath(
                                "//span[text()='Latest news posts']/ancestor::div[@id='rich-shelf-header-container']/following-sibling::div[@id='contents-container']");
                Wrappers.getLatestNewsDetails(driver,newContainerLocator);
                Thread.sleep(2000);
        }

        @Test(dataProvider = "excelData")
        public void testCase05(String searchText) throws InterruptedException {
                driver.get("https://www.youtube.com/");
                Wrappers.search(driver, searchText);
                // Thread.sleep(3000);
                Wrappers.getViewCounts(driver, 100000000);
                Thread.sleep(2000);
        }

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
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
                driver.manage().window().maximize();
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}