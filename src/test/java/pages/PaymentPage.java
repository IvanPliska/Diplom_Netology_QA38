package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import models.CardInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class PaymentPage {
    private SelenideElement heading = Selenide.$x("//h3[text()='Оплата по карте']");

    private SelenideElement cardNumber = Selenide.$x("//span[text()='Номер карты']/following-sibling::span/input");
    private SelenideElement month = Selenide.$x("//span[text()='Месяц']/following-sibling::span/input");
    private SelenideElement year = Selenide.$x("//span[text()='Год']/following-sibling::span/input");
    private SelenideElement cardOwner = Selenide.$x("//span[text()='Владелец']/following-sibling::span/input");
    private SelenideElement cvc = Selenide.$x("//span[text()='CVC/CVV']/following-sibling::span/input");

    private SelenideElement proceedBtn = Selenide.$x("//span[text()='Продолжить']");
    private SelenideElement errorMessWithDecline = Selenide.$x("//div[text()='Ошибка!" +
            " Банк отказал в проведении операции.']");
    private SelenideElement approvedMess = Selenide.$x("//div[text()='Операция одобрена Банком.']");


    private SelenideElement warningCardNumber = Selenide.$x("//span[text()='Номер карты']" +
            "/following-sibling::span[@class='input__sub']");
    private SelenideElement warningMonth = Selenide.$x("//span[text()='Месяц']" +
            "/following-sibling::span[@class='input__sub']");
    private SelenideElement warningYear = Selenide.$x("//span[text()='Год']" +
            "/following-sibling::span[@class='input__sub']");
    private SelenideElement warningCardOwner = Selenide.$x("//span[text()='Владелец']" +
            "/following-sibling::span[@class='input__sub']");
    private SelenideElement warningCvc = Selenide.$x("//span[text()='CVC/CVV']" +
            "/following-sibling::span[@class='input__sub']");

    public PaymentPage() {
        heading.shouldBe(visible);
    }

    public void insertValidPaymentCardDataForBank(CardInfo cardInfo) {
        cardNumber.setValue(cardInfo.getNumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        cardOwner.setValue(cardInfo.getHolder());
        cvc.setValue(cardInfo.getCvc());
        proceedBtn.click();
    }

    public void clickProceedButton() {
        proceedBtn.click();
    }

    public void checkErrorMessDeclineFromBank() {
        errorMessWithDecline.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void checkApprovedMessFromBank() {
        approvedMess.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void checkWarningUnderCardNumber(String warningText) {
        warningCardNumber.shouldBe(visible);
        warningCardNumber.shouldHave(text(warningText));
    }

    public void checkWarningUnderMonth(String warningText) {
        warningMonth.shouldBe(visible);
        warningMonth.shouldHave(text(warningText));
    }

    public void checkWarningUnderYear(String warningText) {
        warningYear.shouldBe(visible);
        warningYear.shouldHave(text(warningText));
    }

    public void checkWarningUnderCardOwner(String warningText) {
        warningCardOwner.shouldBe(visible);
        warningCardOwner.shouldHave(text(warningText));
    }

    public void checkWarningUnderCvc(String warningText) {
        warningCvc.shouldBe(visible);
        warningCvc.shouldHave(text(warningText));
    }

    public void notCheckWarningUnderAllFields() {
        warningCardNumber.shouldNotBe(visible);
        warningMonth.shouldNotBe(visible);
        warningYear.shouldNotBe(visible);
        warningCardOwner.shouldNotBe(visible);
        warningCvc.shouldNotBe(visible);
    }
}
