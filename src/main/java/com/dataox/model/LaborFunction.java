package com.dataox.model;

import lombok.Getter;

/**
 * Labor function from jobs.techstars.com/jobs
 */
@Getter
public enum LaborFunction {

    ACCOUNTING_AND_FINANCE("Accounting & Finance"),
    ADMINISTRATION("Administration"),
    DATA_SCIENCE("Data Science"),
    CUSTOMER_SERVICE("Customer Service"),
    DESIGN("Design"),
    IT("IT"),
    LEGAL("Legal"),
    MARKETING_AND_COMMUNICATIONS("Marketing & Communications"),
    OPERATIONS("Operations"),
    OTHER_ENGINEERING("Other Engineering"),
    PEOPLE_AND_HR("People & HR"),
    PRODUCT("Product"),
    QUALITY_ASSURANCE("Quality Assurance"),
    SALES_AND_BUSINESS_DEVELOPMENT("Sales & Business Development"),
    SOFTWARE_ENGINEERING("Software Engineering");

    private final String value;

    LaborFunction(String value) {
        this.value = value;
    }

    public static String fromString(String value) {
        for (LaborFunction laborFunction : LaborFunction.values()) {
            System.out.println(laborFunction.name());
            System.out.println(value);
            if (laborFunction.value.equalsIgnoreCase(value)) {
                return laborFunction.getValue();
            }
        }
        return null;
    }
}
