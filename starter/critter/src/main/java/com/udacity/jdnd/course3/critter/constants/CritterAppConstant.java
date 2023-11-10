package com.udacity.jdnd.course3.critter.constants;

/**
 * CritterAppConstant.
 *
 * @author NguyenT4.
 * @since 2023/10/22.
 */
public class CritterAppConstant {

    /**
     * TableName.
     */
    public class TableName {
        /**
         * CUSTOMER.
         */
        public static final String CUSTOMER = "customers";

        /**
         * PET.
         */
        public static final String PET = "pets";

        /**
         * EMPLOYEE.
         */
        public static final String EMPLOYEE = "employees";

        /**
         * SCHEDULE.
         */
        public static final String SCHEDULE = "schedules";
    }

    /**
     * CustomerTable.
     */
    public class CustomerTable {
        /**
         * PERSON_ID.
         */
        public static final String PERSON_ID = "person_id";

        /**
         * PERSON_ID_FK.
         */
        public static final String PERSON_ID_FK = "PERSON_ID_FK";
    }

    /**
     * MessagePattem.
     */
    public class MessagePattem {
        public final static String RESOURCE_NOT_FOUND = "{0} not found with {1} : {2}";
        public final static String SAVE_ERROR = "{0} save error !";
    }
}
