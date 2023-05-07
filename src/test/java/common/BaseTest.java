package common;

import org.junit.BeforeClass;

import static specs.RestAssuredSpecs.*;

public class BaseTest {

    public static BaseSteps baseSteps;
    public static RegistrationSteps registrationSteps;
    public static UserSteps userSteps;

    @BeforeClass
    public static void setUp() {
        setUpSpec(requestSpec(), responseSpec());
        baseSteps = new BaseSteps();
        registrationSteps = new RegistrationSteps();
        userSteps = new UserSteps();
    }
}
