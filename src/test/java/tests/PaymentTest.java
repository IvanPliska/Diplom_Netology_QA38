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

public class PaymentTest {

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
    @DisplayName("Should approved payment card with approved test card")
    void shouldSuccessTransactionWithPaymentCard() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }


    @Test
    @DisplayName("Should reject payment card with reject test card")
    void shouldNotSuccessTransactionWithPaymentCard() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithDeclineCard();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkErrorMessDeclineFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with month by one date")
    void shouldSuccessTransactionWithMonthWithoutZero() {
        var toPaymentPage = mainPage.paymentPage();
        var validYear = Integer.parseInt(DataHelper.getCurrentYear()) + 1;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear
                ("5", String.valueOf(validYear));
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card by 5 date")
    void shouldSuccessTransactionWithMaxDate() {
        var toPaymentPage = mainPage.paymentPage();
        var currentMonth = DataHelper.getCurrentMonth();
        var maxYear = Integer.parseInt(DataHelper.getCurrentYear()) + 5;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear(currentMonth,
                String.valueOf(maxYear));
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card and max date minus 1 month")
    void shouldSuccessTransactionWithPreMaxAllowedDate() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithMaxDateMinusOneMonth();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card and min date")
    void shouldSuccessTransactionWithMinAllowedDate() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear
                (DataHelper.getCurrentMonth(), DataHelper.getCurrentYear());
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card and min date next month)")
    void shouldSuccessTransactionWithPreMinAllowedDate() {
        var toPaymentPage = mainPage.paymentPage();
        var nextMonth = Integer.parseInt(DataHelper.getCurrentMonth()) + 1;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear
                (String.valueOf(nextMonth), DataHelper.getCurrentYear());
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card and max length card owner's name")
    void shouldSuccessTransactionMaxLengthCardOwnerName() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(21);
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should approved payment card with approved test card and min length card owner's name")
    void shouldSuccessTransactionMinLengthCardOwnerName() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(3);
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
    }

    @Test
    @DisplayName("Should decline payment card with random test card")
    void shouldDeclineWithRandomPaymentCard() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithRandomCardNumber();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkErrorMessDeclineFromBank();
    }

    @Test
    @DisplayName("Should to show red warning with empty card number")
    void shouldShowMessWithEmptyCardNumber() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setNumber("");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardNumber("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty month")
    void shouldShowMessWithEmptyMonth() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setMonth("");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderMonth("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty year")
    void shouldShowMessWithEmptyYear() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setYear("");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderYear("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty card owner")
    void shouldShowMessWithEmptyCardOwner() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setHolder("");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardOwner("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Should to show red warning with empty cvc")
    void shouldShowMessWithEmptyCvc() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithApprovedCard();
        cardInfo.setCvc("");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCvc("Неверный формат");
    }

    @Test
    @DisplayName("Should to show red warning with empty all field")
    void shouldShowMessWithEmptyAllField() {
        var toPaymentPage = mainPage.paymentPage();
        toPaymentPage.clickProceedButton();
        toPaymentPage.checkWarningUnderCardNumber("Неверный формат");
        toPaymentPage.checkWarningUnderMonth("Неверный формат");
        toPaymentPage.checkWarningUnderYear("Неверный формат");
        toPaymentPage.checkWarningUnderCardOwner("Поле обязательно для заполнения");
        toPaymentPage.checkWarningUnderCvc("Неверный формат");
    }

    @Test
    @DisplayName("Should not to show red warning with empty all field after write field")
    void shouldNotShowMessAfterEmptyAllField() {
        var toPaymentPage = mainPage.paymentPage();
        toPaymentPage.clickProceedButton();
        toPaymentPage.checkWarningUnderCardNumber("Неверный формат");
        toPaymentPage.checkWarningUnderMonth("Неверный формат");
        toPaymentPage.checkWarningUnderYear("Неверный формат");
        toPaymentPage.checkWarningUnderCardOwner("Поле обязательно для заполнения");
        toPaymentPage.checkWarningUnderCvc("Неверный формат");

        var cardInfo = DataHelper.generateDataWithApprovedCard();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkApprovedMessFromBank();
        toPaymentPage.notCheckWarningUnderAllFields();
    }

    @Test
    @DisplayName("Should to show red warning with expired card for year")
    void shouldShowWarningMessWithExpiredCardForYear() {
        var toPaymentPage = mainPage.paymentPage();
        var currentMonth = DataHelper.getCurrentMonth();
        var lastYear = Integer.parseInt(DataHelper.getCurrentYear()) - 1;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear(currentMonth,
                String.valueOf(lastYear));
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderYear("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Should to show red warning with expired card for month")
    void shouldShowWarningMessWithExpiredCardForMonth() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataExpiredCardForMinusOneMonth();
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderMonth("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should to show red warning with 00 month")
    void shouldShowWarningMessWithZeroZeroMonth() {
        var toPaymentPage = mainPage.paymentPage();
        var validYear = Integer.parseInt(DataHelper.getCurrentYear()) + 1;
        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear
                ("00", String.valueOf(validYear));
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderMonth("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should to show red warning with invalid month data")
    void shouldShowWarningMessWithInvalidMonthData() {
        var toPaymentPage = mainPage.paymentPage();
        var currentYear = DataHelper.getCurrentYear();

        var cardInfo = DataHelper.generateDataWithApprovedCardAndParametrizedMonthAndYear("78",
                currentYear);
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderMonth("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Should o show red warning with more max length card owner's name")
    void shouldShowWarningMessWithMoreMaxLengthCardOwnerName() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(22);
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardOwner("Имя не должно быть длиннее 21 символа");
    }

    @Test
    @DisplayName("Should o show red warning with less min length card owner's name")
    void shouldShowWarningMessWithLessMinLengthCardOwnerName() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamLengthCardOwnerName(2);
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardOwner("Имя не должно быть короче 3 символов");
    }

    @Test
    @DisplayName("Should o show red warning with card owner's name is written in Cyrillic")
    void shouldShowWarningMessWithCyrillicCardOwnerName() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerName("НАРУТО УЗУМАКИ");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardOwner("Неверный формат");
    }

    @Test
    @DisplayName("Should o show red warning with card owner's name with numbers")
    void shouldShowWarningMessWithNumbersCardOwnerName() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerName("НАРУТО999 УЗУМАКИ");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardOwner("Неверный формат");
    }

    @Test
    @DisplayName("Should o show red warning with card owner's name with special symbols")
    void shouldShowWarningMessWithSpecSymbolsCardOwnerName() {
        var toPaymentPage = mainPage.paymentPage();
        var cardInfo = DataHelper.generateDataWithParamCardOwnerName("!№;%%:?*()");
        toPaymentPage.insertValidPaymentCardDataForBank(cardInfo);
        toPaymentPage.checkWarningUnderCardOwner("Неверный формат");
    }
}