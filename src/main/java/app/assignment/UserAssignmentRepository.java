package app.assignment;

public interface UserAssignmentRepository {

    boolean assign(UserAssignment assignment);

    boolean unassign(UserAssignment unassignment);

    boolean isAssigned(UserAssignment assignmentStatus);

    boolean unassignAllUsers(String accountId);
}
