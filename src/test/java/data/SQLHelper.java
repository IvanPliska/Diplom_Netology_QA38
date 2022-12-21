package data;

import lombok.SneakyThrows;
import models.CreditCardData;
import models.PaymentCardData;
import models.TableOrderEntity;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static QueryRunner runner  = new QueryRunner();
    private static  String url = System.getProperty("db.url");
    // данные url активируются в application.properties для каждого СУБД
    // при тестировании MySQL url = "jdbc:mysql://localhost:3306/app"
    // при тестировании PostgreSQL url = "jdbc:postgresql://localhost:5432/app"

    private static  String userName = System.getProperty("db.username");
    private static  String password = System.getProperty("db.password");


    public SQLHelper(){
    }

    @SneakyThrows
    private static Connection getConn() {
        return DriverManager.getConnection(url, userName, password);
    }

    public static CreditCardData getCreditCardData() {
        var cardDataSQL =  "SELECT * FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            var result = runner.query(conn, cardDataSQL,
                    new BeanHandler<>(CreditCardData.class));
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static PaymentCardData getPaymentCardData() {
        var cardDataSQL =  "SELECT * FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            var result = runner.query(conn, cardDataSQL,
                    new BeanHandler<>(PaymentCardData.class));
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static TableOrderEntity getTableOrderEntity() {
        var orderEntityDataSQL =  "SELECT * FROM order_entity ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            var result = runner.query(conn, orderEntityDataSQL,
                    new BeanHandler<>(TableOrderEntity.class));
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var conn = getConn();
        runner.execute(conn, "DELETE FROM order_entity");
        runner.execute(conn, "DELETE FROM payment_entity");
        runner.execute(conn, "DELETE FROM credit_request_entity");
    }
}
