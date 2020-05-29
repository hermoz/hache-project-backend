package hmm.architecturestudio.management.util;

import java.util.*;

public class Constants {

    public static final String ADMIN_USERNAME = "admin";

    // Privilege constants
    public static final String CREATE_USERS = "CREATE_USERS";
    public static final String READ_USERS = "READ_USERS";
    public static final String UPDATE_USERS = "UPDATE_USERS";
    public static final String DELETE_USERS = "DELETE_USERS";

    public static final String CREATE_PROJECTS = "CREATE_PROJECTS";
    public static final String READ_PROJECTS = "READ_PROJECTS";
    public static final String UPDATE_PROJECTS = "UPDATE_PROJECTS";
    public static final String DELETE_PROJECTS = "DELETE_PROJECTS";

    public static final String CREATE_CUSTOMERS = "CREATE_CUSTOMERS";
    public static final String READ_CUSTOMERS = "READ_CUSTOMERS";
    public static final String UPDATE_CUSTOMERS = "UPDATE_CUSTOMERS";
    public static final String DELETE_CUSTOMERS = "DELETE_CUSTOMERS";

    // Role constants
    public static final String ADMINISTRATOR = "ADMINISTRATOR";
    public static final String MANAGER = "MANAGER";
    public static final String EMPLOYEE = "EMPLOYEE";

    // Project Type constants
    public static final String INTERIOR_DESIGN = "INTERIOR_DESIGN";
    public static final String BUILDING = "BUILDING";
    public static final String FAIR_STAND = "FAIR_STAND";
    public static final String HOUSING_CERTIFICATE = "HOUSING_CERTIFICATE";


    // Privileges List
    public static final List<String> PRIVILEGES_LIST = new ArrayList<String>() {
        {
            add(CREATE_USERS);
            add(READ_USERS);
            add(UPDATE_USERS);
            add(DELETE_USERS);

            add(CREATE_PROJECTS);
            add(READ_PROJECTS);
            add(UPDATE_PROJECTS);
            add(DELETE_PROJECTS);

            add(CREATE_CUSTOMERS);
            add(READ_CUSTOMERS);
            add(UPDATE_CUSTOMERS);
            add(DELETE_CUSTOMERS);
        }
    };

    // Role List
    public static final List<String> ROLES_LIST = new ArrayList<String>() {
        {
            add(ADMINISTRATOR);
            add(MANAGER);
            add(EMPLOYEE);
        }
    };

    // Administrator privileges
    private static final List<String> PRIVILEGES_BY_ADMINISTRATOR = new ArrayList<String>() {
        {
            add(CREATE_USERS);
            add(READ_USERS);
            add(UPDATE_USERS);
            add(DELETE_USERS);
            add(CREATE_PROJECTS);
            add(READ_PROJECTS);
            add(UPDATE_PROJECTS);
            add(DELETE_PROJECTS);
            add(CREATE_CUSTOMERS);
            add(READ_CUSTOMERS);
            add(UPDATE_CUSTOMERS);
            add(DELETE_CUSTOMERS);
        }
    };

    // Manager privileges
    private static final List<String> PRIVILEGES_BY_MANAGER = new ArrayList<String>() {
        {
            add(READ_USERS);
            add(CREATE_PROJECTS);
            add(READ_PROJECTS);
            add(UPDATE_PROJECTS);
            add(DELETE_PROJECTS);
            add(CREATE_CUSTOMERS);
            add(READ_CUSTOMERS);
            add(UPDATE_CUSTOMERS);
            add(DELETE_CUSTOMERS);
        }
    };

    // Employee privileges
    private static final List<String> PRIVILEGES_BY_EMPLOYEE = new ArrayList<String>() {
        {
            add(READ_PROJECTS);
            add(READ_USERS);
            add(READ_CUSTOMERS);
        }
    };


    // Privileges List indexed by Role
    public static final Map<String, List<String>> PRIVILEGES_BY_ROLE;
    static {
        Map<String, List<String>> privilegesByRole = new HashMap<>();
        privilegesByRole.put(Constants.ADMINISTRATOR, Constants.PRIVILEGES_BY_ADMINISTRATOR);
        privilegesByRole.put(Constants.MANAGER, Constants.PRIVILEGES_BY_MANAGER);
        privilegesByRole.put(Constants.EMPLOYEE, Constants.PRIVILEGES_BY_EMPLOYEE);

        PRIVILEGES_BY_ROLE = Collections.unmodifiableMap(privilegesByRole);
    };

    // Project Type List
    public static final List<String> PROJECT_TYPE_LIST = new ArrayList<String>() {
        {
            add(INTERIOR_DESIGN);
            add(BUILDING);
            add(FAIR_STAND);
            add(HOUSING_CERTIFICATE);
        }
    };

}
