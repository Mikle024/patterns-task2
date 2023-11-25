package ru.netology.qa.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.qa.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AccountLoginNegativeTest {
    static {
        Configuration.headless = true;
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldGetErrorIfIncorrectLogin() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var notRegisteredUser = DataGenerator.Registration.getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login'] .button__text").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    public void shouldGetErrorIfIncorrectPassword() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var notRegisteredUser = DataGenerator.Registration.getUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login'] .button__text").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    public void shouldGetErrorIfAccountBanned() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login'] .button__text").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.text("Ошибка! Пользователь заблокирован"));
    }
}

