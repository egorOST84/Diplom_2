package common;

import org.junit.BeforeClass;

import static specs.RestAssuredSpecs.*;

public class BaseTest {

    public static BaseSteps baseSteps;
    public static RegistrationSteps registrationSteps;

    @BeforeClass
    public static void setUp() {
        setUpSpec(requestSpec(), responseSpec());
        baseSteps = new BaseSteps();
        registrationSteps = new RegistrationSteps();
    }
}
