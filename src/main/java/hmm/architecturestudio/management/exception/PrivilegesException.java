package hmm.architecturestudio.management.exception;

public class PrivilegesException extends Exception {

    public PrivilegesException(String requiredPrivilege) {
        super("You dont have the required privilege: " + requiredPrivilege);
    }

}