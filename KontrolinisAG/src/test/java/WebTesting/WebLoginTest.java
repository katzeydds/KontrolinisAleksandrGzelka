package WebTesting;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WebLoginTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void testWebLogin() {
        List<Product> testData = new ArrayList<>();
        testData.add(new Product("HUMMINGBIRD PRINTED SWEATER", 35.9, 20, "M"));

        for (Product product : testData) {
            driver.get("http://192.168.89.19");

            WebElement searchBox = driver.findElement(By.cssSelector("input.ui-autocomplete-input"));
            searchBox.sendKeys(product.getName());
            searchBox.sendKeys(Keys.RETURN);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Hummingbird Printed Sweater")));
            assertThat(productLink.isDisplayed()).isTrue();

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productLink);
            productLink.click();

            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.add-to-cart")));
            addToCartButton.click();

            WebElement continueShoppingButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Continue shopping')]")));
            continueShoppingButton.click();
        }
    }

    private static class Product {
        private final String name;
        private final double price;
        private final int discount;
        private final String size;

        public Product(String name, double price, int discount, String size) {
            this.name = name;
            this.price = price;
            this.discount = discount;
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int getDiscount() {
            return discount;
        }

        public String getSize() {
            return size;
        }
    }
}
