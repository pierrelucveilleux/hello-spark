package app.account;

import org.jooq.Record;
import support.database.DatabaseException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.UUID;

import static app.account.PricingModel.fromValue;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;
import static support.database.DatabaseContext.database;


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
            if(accountRead != null) {
                return new Account((String) accountRead.getValue("id"), fromValue((String) accountRead.getValue("pricing")));
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Cannot find account with id:" + id, e);
        }
    }

    @Override
    public String create(PricingModel pricingModel) {
        String id = UUID.randomUUID().toString();
        try {
            int created = database(dataSource.getConnection())
                    .insertInto(table("account"),
                            field("id"), field("pricing"))
                    .values(id, pricingModel.name())
                    .returning()
                    .execute();
            if (created == 1) {
                return id;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Cannot create account with id:" + id, e);
        }

        throw new DatabaseException("Cannot create account with id:" + id);
    }

    @Override
    public void remove(String id) {
        try {
            database(dataSource.getConnection())
                    .delete(table("account")).where(field("id").eq(id))
                    .execute();
        } catch (SQLException e) {
            throw new DatabaseException("Cannot remove account with id:" + id, e);
        }
    }

    @Override
    public void update(Account account) {
        try {
            database(dataSource.getConnection())
                    .update(table("account"))
                    .set(field("pricing"), account.pricingModel().toValue())
                    .where(field("id").eq(account.id()))
                    .execute();
        } catch (SQLException e) {
            throw new DatabaseException("Cannot remove account with id:" + account.id(), e);
        }
    }
}
