import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class OrderingCardDeliveryTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\annbo\\IdeaProjects\\CardDelivery\\driver\\win\\chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        open("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldMakeCardOrder() {
        $("[data-test-id=city]").setValue("Тула");
        $("[data-test-id=date]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date]").setValue(generateDate(5));
        $("[data-test-id=name]").setValue("Петров Лев");
        $("[data-test-id=phone]").setValue("+79532223322");
        $("[data-test-id=agreement]").click();
        $$("button").findBy(Condition.exactText("Забронировать")).click();
//        $$("button").findBy(Condition.exactText("Забронировать")).click().shouldBe(Duration.ofSeconds(15));
        $("[data-test-id=notification]").find(generateDate(3)).shouldBe(visible, Duration.ofSeconds(5));


    }

}
