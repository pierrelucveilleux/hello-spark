package support.database;

import org.jooq.DSLContext;
import org.jooq.conf.Settings;

import java.sql.Connection;

import static org.jooq.SQLDialect.MYSQL;
import static org.jooq.conf.StatementType.PREPARED_STATEMENT;
import static org.jooq.impl.DSL.using;

public class DatabaseContext {

    public static DSLContext database(Connection connection) {
        return using(connection, MYSQL, new Settings().withStatementType(PREPARED_STATEMENT));
    }
}
