package data;

import com.github.javafaker.Faker;
import models.CardInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    public static final String HOME_PAGE = "http://localhost:8080/";
    private static Faker faker = new Faker(new Locale("en"));

    private static int validYear = Integer.parseInt(getCurrentYear()) + 1;
    private static String numberApprovedCard = "4444 4444 4444 4441";
    private static String numberDeclinedCard = "4444 4444 4444 4442";

    public static CardInfo generateDataWithApprovedCard() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, getCurrentMonth(), String.valueOf(validYear), randomName, randomCvc);
    }

    public static CardInfo generateDataWithDeclineCard() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberDeclinedCard, getCurrentMonth(), String.valueOf(validYear), randomName, randomCvc);
    }

    public static CardInfo generateDataWithRandomCardNumber() {
        var randomName = faker.name().fullName();
        var randomCardNumber = faker.number().digits(16);
        var randomCvc = faker.number().digits(3);
        return new CardInfo(randomCardNumber, getCurrentMonth(), String.valueOf(validYear), randomName, randomCvc);
    }

    public static CardInfo generateDataWithApprovedCardAndParametrizedMonthAndYear(String month, String year) {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, month, year, randomName, randomCvc);

    }

    public static CardInfo generateDataWithParamLengthCardOwnerName(int length) {
        var randomName = faker.lorem().fixedString(length);
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, getCurrentMonth(), String.valueOf(validYear), randomName, randomCvc);
    }

    public static CardInfo generateDataWithParamCardOwnerName(String name) {
        var randomCvc = faker.number().digits(3);
        return new CardInfo(numberApprovedCard, getCurrentMonth(), String.valueOf(validYear), name, randomCvc);
    }

    public static CardInfo generateDataExpiredCardForMinusOneMonth() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        var currentMonth = Integer.parseInt(getCurrentMonth());
        var currentYear = Integer.parseInt(getCurrentYear());
        if (currentMonth == 1) {
            currentMonth = 12;
            currentYear = currentYear - 1; // если месяц первый, то необходимо отнять год и оставить 12 месяц
        } else currentMonth = currentMonth - 1;
        String minusOneFromCurrentMonth = "";
        if (currentMonth < 10) {
            minusOneFromCurrentMonth = "0" + currentMonth; // для вставки в поле даты формата 01(январь) и т.п.
        }
        return new CardInfo(numberApprovedCard, minusOneFromCurrentMonth,
                String.valueOf(currentYear), randomName, randomCvc);
    }

    public static CardInfo generateDataWithMaxDateMinusOneMonth() {
        var randomName = faker.name().fullName();
        var randomCvc = faker.number().digits(3);
        var currentMonth = Integer.parseInt(getCurrentMonth());
        var preMaxMonth = 0;
        var maxYear = Integer.parseInt(getCurrentYear()) + 5;
        if (currentMonth == 1) {
            preMaxMonth = 12;
            maxYear = maxYear - 1; // если месяц первый, то необходимо отнять год и оставить 12 месяц
        } else preMaxMonth = currentMonth - 1;
        String strPreMaxMonth = "";
        if (preMaxMonth < 10) {
            strPreMaxMonth = "0" + preMaxMonth; // для вставки в поле даты формата 01(январь), 02(февраль) и т.д.
        }
        return new CardInfo(numberApprovedCard, strPreMaxMonth,
                String.valueOf(maxYear), randomName, randomCvc);
    }

    public static String getCurrentMonth() {
        LocalDate date = LocalDate.now();
        var currentMonth = date.format(DateTimeFormatter.ofPattern("MM"));
        return currentMonth;
    }

    public static String getCurrentYear() {
        LocalDate date = LocalDate.now();
        var currentYear = date.format(DateTimeFormatter.ofPattern("yy"));
        return currentYear;
    }
}