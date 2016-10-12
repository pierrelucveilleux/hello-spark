package app.user;

import org.jooq.DSLContext;
import org.jooq.Record;
import support.database.DatabaseException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.UUID;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;
import static support.database.DatabaseContext.database;

public class DatabaseUserRepository implements UserRepository {

    private final DataSource dataSource;

    public DatabaseUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String create(User user) {
        String id = UUID.randomUUID().toString();
        try {
            DSLContext database = database(dataSource.getConnection());
            int created = database
                    .insertInto(table("user"), field("id"), field("name"), field("email"))
                    .values(id, user.name(), user.email())
                    .execute();
            if (created == 1) {
                return id;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Cannot create user:" + user.email(), e);
        }
        throw new DatabaseException("Cannot create user:" + user.email());
    }

    @Override
    public User findByEmail(String email) {
        try {
            Record userRead = database(dataSource.getConnection())
                    .select(field("id"), field("name"), field("email")).from(table("user"))
                    .where(field("email").eq(email))
                    .fetchOne();
            return new User((String) userRead.getValue("id"), (String) userRead.getValue("name"), (String) userRead.getValue("email"));
        } catch (SQLException e) {
            throw new DatabaseException("Cannot find user with email:" + email, e);
        }
    }
}
