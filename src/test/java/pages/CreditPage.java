package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class CreditPage {
    private SelenideElement heading = Selenide.$x("//h3[text()='Кредит по данным карты']");

    private SelenideElement cardNumber = Selenide.$x("//span[text()='Номер карты']" +
            "/following-sibling::span/input");
    private SelenideElement month = Selenide.$x("//span[text()='Месяц']" +
            "/following-sibling::span/input");
    private SelenideElement year = Selenide.$x("//span[text()='Год']" +
            "/following-sibling::span/input");
    private SelenideElement cardOwner = Selenide.$x("//span[text()='Владелец']" +
            "/following-sibling::span/input");
    private SelenideElement cvc = Selenide.$x("//span[text()='CVC/CVV']" +
            "/following-sibling::span/input");

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

    public CreditPage() {
        heading.shouldBe(visible);
    }

    public void insertValidCreditCardDataForBank(DataHelper.CardInfo cardInfo) {
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


    public void checkWarningUnderCardNumberField(String warningText) {
        warningCardNumber.shouldBe(visible);
        warningCardNumber.shouldHave(text(warningText));
    }


    public void checkWarningUnderMonthField(String warningText) {
        warningMonth.shouldBe(visible);
        warningMonth.shouldHave(text(warningText));
    }


    public void checkWarningUnderYearField(String warningText) {
        warningYear.shouldBe(visible);
        warningYear.shouldHave(text(warningText));
    }


    public void checkWarningUnderCardOwnerField(String warningText) {
        warningCardOwner.shouldBe(visible);
        warningCardOwner.shouldHave(text(warningText));
    }

    public void checkWarningUnderCvcField(String warningText) {
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
