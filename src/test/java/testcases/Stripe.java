package testcases;

import libraries.StripeUtils;
import maps.CollectionOfCharges;
import org.testng.annotations.Test;

import java.util.Locale;

import static org.testng.Assert.assertEquals;

/**
 * Created by tmaher on 2/16/2016.
 */
public class Stripe {

    private boolean checkMatchingValues(String testHeading, Object actualValue, Object expectedValue) {
        String successMessage = "\t* The Expected and Actual Values match. (PASS)\n";
        String failureMessage = "\t* The Expected and Actual Values do not match! (FAIL)\n";

        boolean doValuesMatch = false;

        System.out.println(testHeading);
        System.out.println("\t* Expected Value: " + expectedValue);
        System.out.println("\t* Actual Value: " + actualValue);

        if (actualValue.equals(expectedValue)) {
            System.out.println(successMessage);
            doValuesMatch = true;
        } else {
            System.out.println(failureMessage);
            doValuesMatch = false;
        }
        return doValuesMatch;
    }

    @Test
    public void test_validCountry() {
        StripeUtils stripe = new StripeUtils(Locale.US);
        // This will always pass.
    }

    @Test
    public void test_invalidCountry() {
        StripeUtils stripe = new StripeUtils(Locale.GERMANY);
        // This will always fail.
        // ERROR: Stripe is used only for US or CANADA. Country entered: de_DE
    }

    @Test
    public void test_returnedCollectionOfChargesParameters(){
        String expectedObjectType = "list";
        String expectedUrl = "/v1/charges";
        Integer numberOfChargesReturned = 2;
        StripeUtils stripe = new StripeUtils(Locale.US);
        CollectionOfCharges charges = stripe.getListOfCharges(numberOfChargesReturned);

        String actualObjectType = charges.getObjectType();
        checkMatchingValues("Verify that the Returned Collection Object types match", actualObjectType, expectedObjectType);
        assertEquals(actualObjectType, expectedObjectType, "ERROR: The Object types do not match!");

        String actualUrl = charges.getUrl();
        checkMatchingValues("Verify that the Returned Collection URLs match", actualUrl, expectedUrl);
        assertEquals(actualUrl, expectedUrl, "ERROR: The urls do not match!");
    }
}
