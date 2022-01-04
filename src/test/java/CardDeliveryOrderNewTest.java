import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import Utility.Data;
import Utility.RegistrationInfo;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static Utility.Data.generateDate;

public class CardDeliveryOrderNewTest {


    @Test
    void shouldSendRegistrationForm() {
        RegistrationInfo info = Data.Registration.generateInfo("ru");
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue(info.getCity());
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), generateDate(4));
        $("[data-test-id='name'] input").setValue(info.getName());
        $("[data-test-id='phone'] input").setValue("+" + info.getPhone());
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(".button__text").shouldHave(Condition.text("Запланировать")).click();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Встреча успешно запланирована на  " + generateDate(4)), Duration.ofSeconds(15));
        $("button.notification__closer").click();

        $("[data-test-id='city'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), info.getCity());
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), generateDate(7));
        $("[data-test-id='name'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), info.getName());
        $("[data-test-id='phone'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), "+" + info.getPhone());
        $(".button__text").shouldHave(Condition.text("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__content").shouldBe(Condition.visible)
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(15));
        $$("button .button__text").find(Condition.text("Перепланировать")).click();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Встреча успешно запланирована на  " + generateDate(7)), Duration.ofSeconds(15));

    }

    @Test
    public void shouldCheckingTheFormNoPhone() {
        RegistrationInfo info = Data.Registration.generateInfo("ru");
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue(info.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), generateDate(4));
        $("[data-test-id='name'] input").setValue(info.getName());
        $("[data-test-id='agreement']").click();
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));

    }

    @Test
    public void shouldCheckingTheFormNoCity() {
        RegistrationInfo info = Data.Registration.generateInfo("ru");
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), generateDate(4));
        $("[data-test-id='name'] input").setValue(info.getName());
        $("[data-test-id='phone'] input").setValue("+" + info.getPhone());
        $("[data-test-id='agreement']").click();
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));

    }

    @Test
    public void shouldCheckingTheFormNoCheckbox() {
        RegistrationInfo info = Data.Registration.generateInfo("ru");
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue(info.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), generateDate(4));
        $("[data-test-id='name'] input").setValue(info.getName());
        $("[data-test-id='phone'] input").setValue("+" + info.getPhone());
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $("[data-test-id='agreement'] .checkbox__text").shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }

    @Test
    public void shouldCheckingTheFormNoAdministrativeCenter() {
        RegistrationInfo info = Data.Registration.generateInfo("ru");
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Бугульма");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), generateDate(4));
        $("[data-test-id='name'] input").setValue(info.getName());
        $("[data-test-id='phone'] input").setValue("+" + info.getPhone());
        $("[data-test-id='agreement']").click();
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $("[data-test-id='city'].input_invalid .input__sub").
                shouldHave(Condition.text("Доставка в выбранный город недоступна"));

    }
}