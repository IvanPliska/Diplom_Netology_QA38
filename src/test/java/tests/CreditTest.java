package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import pages.MainPage;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.HOME_PAGE;

public class CreditTest {
    MainPage mainPage = open(HOME_PAGE, MainPage.class);

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @BeforeEach
    void setUP() {
        Configuration.holdBrowserOpen = true;
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
    @AfterEach
    void tearDown() {
        closeWindow();
    }


    @Test
    @DisplayName("Should approved credit card with approved test card")
    void shouldSuccessTransactionWithCreditCard() {
        var toCreditPage = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should reject credit card with reject test card")
    void shouldNotSuccessTransactionWithCreditCard() {
        var toCreditPage = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithDeclineCard();
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkErrorMessDeclineFromBank();
    }

    @Test
    @DisplayName("Should approved credit card with month by one date")
    void shouldSuccessTransactionWithMonthWithoutZero() {
        var toCreditPage = mainPage.creditPage();
        var validYear = Integer.parseInt(DataHelper.getCurrentYear()) + 1;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear
                ("5", String.valueOf(validYear));
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkApprovedMessFromBank();
    }
    @Test
    @DisplayName("Should approved credit card with approved test card and max date")
    void shouldSuccessTransactionWithMaxAllowedDate() {
        var toCreditPage = mainPage.creditPage();
        var currentMonth = DataHelper.getCurrentMonth();
        var maxYear = Integer.parseInt(DataHelper.getCurrentYear()) + 5;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear(currentMonth,
                String.valueOf(maxYear));
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved credit card with approved test card and max date minus 1 month")
    void shouldSuccessTransactionWithPreMaxAllowedDate() {
        var toCreditPage = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithMaxDateMinusOneMonth();
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved credit card with approved test card and min date")
    void shouldSuccessTransactionWithMinAllowedDate() {
        var toCreditPage = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear
                (DataHelper.getCurrentMonth(),DataHelper.getCurrentYear());
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved credit card with approved test card and min date next month)")
    void shouldSuccessTransactionWithPreMinAllowedDate() {
        var toCreditPage = mainPage.creditPage();
        var nextMonth = Integer.parseInt(DataHelper.getCurrentMonth()) + 1;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear
                (String.valueOf(nextMonth),DataHelper.getCurrentYear());
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should credit payment card with approved test card and max length card owner's name")
    void shouldSuccessTransactionMaxLengthCardOwnerName() {
        var toCreditPage = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(21);
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved credit card with approved test card and min length card owner's name")
    void shouldSuccessTransactionMinLengthCardOwnerName() {
        var toCreditPage = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(3);
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should decline credit card with random test card")
    void shouldDeclineWithRandomCreditCard() {
        var toCreditPage = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithRandomCardNumber();
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkErrorMessDeclineFromBank();
    }

    @Test
    @DisplayName("Should to show red warning with empty card number")
    void shouldShowWarningMessWithEmptyCardNumber() {
        var toCreditPage = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setNumber("");
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkWarningUnderCardNumberField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty month")
    void shouldShowWarningMessWithEmptyMonth() {
        var toCreditPage = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setMonth("");
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkWarningUnderMonthField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty year ")
    void shouldShowWarningMessWithEmptyYear() {
        var toCreditPage = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setYear("");
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkWarningUnderYearField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty card owner")
    void shouldShowWarningMessWithEmptyCardOwner() {
        var toCreditPage = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setHolder("");
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkWarningUnderCardOwnerField("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Should to show red warning with empty cvc")
    void shouldShowWarningMessWithEmptyCvc() {
        var toCreditPage = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setCvc("");
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkWarningUnderCvcField("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty all field")
    void shouldShowWarningMessWithEmptyAllField() {
        var toCreditPage = mainPage.creditPage();
        toCreditPage.clickProceedButton();
        toCreditPage.checkWarningUnderCardNumberField("Неверный формат");
        toCreditPage.checkWarningUnderMonthField("Неверный формат");
        toCreditPage.checkWarningUnderYearField("Неверный формат");
        toCreditPage.checkWarningUnderCardOwnerField("Поле обязательно для заполнения");
        toCreditPage.checkWarningUnderCvcField("Неверный формат");
    }

    @Test
    @DisplayName("Should not to show red warning with empty all field after filled field")
    void shouldNotShowWarningMessAfterEmptyAllField() {
        var toCreditPage = mainPage.creditPage();
        toCreditPage.clickProceedButton();
        toCreditPage.checkWarningUnderCardNumberField("Неверный формат");
        toCreditPage.checkWarningUnderMonthField("Неверный формат");
        toCreditPage.checkWarningUnderYearField("Неверный формат");
        toCreditPage.checkWarningUnderCardOwnerField("Поле обязательно для заполнения");
        toCreditPage.checkWarningUnderCvcField("Неверный формат");

        var cardInfo = DataHelper.generateDataWithApprovedCard();
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkApprovedMessFromBank();
        toCreditPage.notCheckWarningUnderAllFields();
    }

    @Test
    @DisplayName("Should to show red warning with expired card for year")
    void shouldShowWarningMessWithExpiredCardForYear() {
        var toCreditPage = mainPage.creditPage();
        var currentMonth = DataHelper.getCurrentMonth();
        var lastYear = Integer.parseInt(DataHelper.getCurrentYear()) - 1;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear(currentMonth,
                String.valueOf(lastYear));
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkWarningUnderYearField("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Should to show red warning with expired card for month")
    void shouldShowWarningMessWithExpiredCardForMonth() {
        var toCreditPage = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataExpiredCardForMinusOneMonth();
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkWarningUnderMonthField("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should to show red warning with 00 month")
    void shouldShowWarningMessWithZeroZeroMonth() {
        var toCreditPage = mainPage.creditPage();
        var validYear = Integer.parseInt(DataHelper.getCurrentYear()) + 1;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear
                ("00", String.valueOf(validYear));
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkWarningUnderMonthField("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should to show red warning with invalid month data")
    void shouldShowWarningMessWithInvalidMonthData() {
        var toCreditPage = mainPage.creditPage();
        var currentYear = DataHelper.getCurrentYear();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear("19",
                currentYear);
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkWarningUnderMonthField("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should o show red warning with more max length card owner's name")
    void shouldShowWarningMessWithMoreMaxLengthCardOwnerName() {
        var toCreditPage = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(22);
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkWarningUnderCardOwnerField("Имя не должно быть длиннее 21 символа");
    }

    @Test
    @DisplayName("Should o show red warning with less min length card owner's name")
    void shouldShowWarningMessWithLessMinLengthCardOwnerName() {
        var toCreditPage = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(2);
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkWarningUnderCardOwnerField("Имя не должно быть короче 3 символов");
    }
    @Test
    @DisplayName("Should o show red warning with card owner's name is written in Cyrillic")
    void shouldShowWarningMessWithCyrillicCardOwnerName() {
        var toCreditPage = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerName("НАРУТО УЗУМАКИ");
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkWarningUnderCardOwnerField("Неверный формат");
    }

    @Test
    @DisplayName("Should o show red warning with card owner's name with numbers")
    void shouldShowWarningMessWithNumbersCardOwnerName() {
        var toCreditPage = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerName("НАРУТО999 УЗУМАКИ");
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkWarningUnderCardOwnerField("Неверный формат");
    }

    @Test
    @DisplayName("Should o show red warning with card owner's name with special symbols")
    void shouldShowWarningMessWithSpecSymbolsCardOwnerName() {
        var toCreditPage = mainPage.creditPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerName("!№;%%:?*()");
        toCreditPage.insertValidCreditCardDataForBank(cardInfo);
        toCreditPage.checkWarningUnderCardOwnerField("Неверный формат");
    }
}
