package app.assignment;

import support.database.DatabaseException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;
import static support.database.DatabaseContext.database;

public class DatabaseUserAssignmentRepository implements UserAssignmentRepository {

    private final DataSource dataSource;

    public DatabaseUserAssignmentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean assign(UserAssignment assignment) {
        try {
            int count = database(dataSource.getConnection())
                    .insertInto(table("user_assignment"),
                            field("user_id"), field("account_id"))
                    .values(assignment.userId(), assignment.accountId())
                    .execute();
            return count == 1;
        } catch (SQLException e) {
            throw new DatabaseException("Cannot unassigne user : " + assignment.userId() + " with account:" + assignment.accountId(), e);
        }
    }

    @Override
    public boolean unassign(UserAssignment unassignment) {
        try {
            int count = database(dataSource.getConnection())
                    .delete(table("user_assignment"))
                    .where(field("user_id").eq(unassignment.userId())).and(field("account_id").eq(unassignment.accountId()))
                    .execute();
            return count == 1;
        } catch (SQLException e) {
            throw new DatabaseException("Cannot unassign user : " + unassignment.userId() + " with account:" + unassignment.accountId(), e);
        }
    }

    @Override
    public boolean isAssigned(UserAssignment assignmentStatus) {
        try {
            List<Object> fetch = database(dataSource.getConnection())
                    .select(field("user_id")).from(table("user_assignment"))
                    .where(field("user_id").eq(assignmentStatus.userId())).and(field("account_id").eq(assignmentStatus.accountId()))
                    .fetch(field("user_id"));
            return !fetch.isEmpty();
        } catch (SQLException e) {
            throw new DatabaseException("Cannot verify assignment", e);
        }
    }

    @Override
    public boolean unassignAllUsers(String accountId) {
        try {
            int count = database(dataSource.getConnection())
                    .delete(table("user_assignment"))
                    .where(field("account_id").eq(accountId))
                    .execute();
            return count > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Cannot unassign users from account : " + accountId, e);
        }
    }
}
