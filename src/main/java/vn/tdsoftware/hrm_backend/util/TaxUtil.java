package vn.tdsoftware.hrm_backend.util;

import vn.tdsoftware.hrm_backend.util.constant.TaxConstant;

public class TaxUtil {
    public static int calculatePersonalIncomeTax(int salaryTaxable) {
        double tax;
        if (salaryTaxable <= TaxConstant.AMOUNT_RANGE_1) {
            tax = salaryTaxable * TaxConstant.RATE_LEVEL_1;
        } else if (salaryTaxable <= TaxConstant.AMOUNT_RANGE_2) {
            tax = TaxConstant.AMOUNT_LEVEL_1 * TaxConstant.RATE_LEVEL_1
                    + (salaryTaxable - TaxConstant.AMOUNT_RANGE_1) * TaxConstant.RATE_LEVEL_2;
        } else if (salaryTaxable <= TaxConstant.AMOUNT_RANGE_3) {
            tax = TaxConstant.AMOUNT_LEVEL_1 * TaxConstant.RATE_LEVEL_1
                    + TaxConstant.AMOUNT_LEVEL_2 * TaxConstant.RATE_LEVEL_2
                    + (salaryTaxable - TaxConstant.AMOUNT_RANGE_2) * TaxConstant.RATE_LEVEL_3;
        } else if (salaryTaxable <= TaxConstant.AMOUNT_RANGE_4) {
            tax = TaxConstant.AMOUNT_LEVEL_1 * TaxConstant.RATE_LEVEL_1
                    + TaxConstant.AMOUNT_LEVEL_2 * TaxConstant.RATE_LEVEL_2
                    + TaxConstant.AMOUNT_LEVEL_3 * TaxConstant.RATE_LEVEL_3
                    + (salaryTaxable - TaxConstant.AMOUNT_RANGE_3) * TaxConstant.RATE_LEVEL_4;
        } else if (salaryTaxable <= TaxConstant.AMOUNT_RANGE_5) {
            tax = TaxConstant.AMOUNT_LEVEL_1 * TaxConstant.RATE_LEVEL_1
                    + TaxConstant.AMOUNT_LEVEL_2 * TaxConstant.RATE_LEVEL_2
                    + TaxConstant.AMOUNT_LEVEL_3 * TaxConstant.RATE_LEVEL_3
                    + TaxConstant.AMOUNT_LEVEL_4 * TaxConstant.RATE_LEVEL_4
                    + (salaryTaxable - TaxConstant.AMOUNT_RANGE_4) * TaxConstant.RATE_LEVEL_5;
        } else if (salaryTaxable <= TaxConstant.AMOUNT_RANGE_6) {
            tax = TaxConstant.AMOUNT_LEVEL_1 * TaxConstant.RATE_LEVEL_1
                    + TaxConstant.AMOUNT_LEVEL_2 * TaxConstant.RATE_LEVEL_2
                    + TaxConstant.AMOUNT_LEVEL_3 * TaxConstant.RATE_LEVEL_3
                    + TaxConstant.AMOUNT_LEVEL_4 * TaxConstant.RATE_LEVEL_4
                    + TaxConstant.AMOUNT_LEVEL_5 * TaxConstant.RATE_LEVEL_5
                    + (salaryTaxable - TaxConstant.AMOUNT_RANGE_5) * TaxConstant.RATE_LEVEL_6;
        } else {
            tax = TaxConstant.AMOUNT_LEVEL_1 * TaxConstant.RATE_LEVEL_1
                    + TaxConstant.AMOUNT_LEVEL_2 * TaxConstant.RATE_LEVEL_2
                    + TaxConstant.AMOUNT_LEVEL_3 * TaxConstant.RATE_LEVEL_3
                    + TaxConstant.AMOUNT_LEVEL_4 * TaxConstant.RATE_LEVEL_4
                    + TaxConstant.AMOUNT_LEVEL_5 * TaxConstant.RATE_LEVEL_5
                    + TaxConstant.AMOUNT_LEVEL_6 * TaxConstant.RATE_LEVEL_6
                    + (salaryTaxable - TaxConstant.AMOUNT_RANGE_6) * TaxConstant.RATE_LEVEL_7;
        }
        return (int) tax;
    }
}
