package app.account;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.conf.Settings;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import static app.account.Account.PricingModel.fromValue;
import static org.jooq.SQLDialect.MYSQL;
import static org.jooq.conf.StatementType.PREPARED_STATEMENT;
import static org.jooq.impl.DSL.*;


public class DatabaseAccountRepository implements AccountRepository {

    private final DataSource dataSource;

    public DatabaseAccountRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Account find(String id) {
        try {
            Record accountRead = database(dataSource.getConnection())
                    .select(field("id"), field("pricing")).from(table("account"))
                    .where(field("id").eq(id))
                    .fetchOne();
            return new Account((String)accountRead.getValue("id"), fromValue((String) accountRead.getValue("pricing")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String create(Account.PricingModel pricingModel) {
        try {
            String id = UUID.randomUUID().toString();
            int created = database(dataSource.getConnection())
                    .insertInto(table("account"),
                            field("id"), field("pricing"))
                    .values(id, pricingModel.name())
                    .returning()
                    .execute();
            if(created == 1) {
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private DSLContext database(Connection connection) {
        return using(connection, MYSQL, new Settings().withStatementType(PREPARED_STATEMENT));
    }
}
