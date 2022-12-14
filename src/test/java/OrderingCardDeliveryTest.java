import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class OrderingCardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldMakeCardOrder() {
        $("[data-test-id=city] input").setValue("Тула");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generateDate(3));
        $("[data-test-id=name] input").setValue("Петров Лев");
        $("[data-test-id=phone] input").setValue("+79532223322");
        $("[data-test-id=agreement]").click();
        $$("button").findBy(Condition.exactText("Забронировать")).shouldBe(visible, Duration.ofSeconds(15)).click() ;
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + generateDate(3)), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
