package common;

import org.junit.BeforeClass;

import static specs.RestAssuredSpecs.*;

public class BaseTest {

    public static BaseSteps baseSteps;

    @BeforeClass
    public static void setUp() {
        setUpSpec(requestSpec(), responseSpec());
        baseSteps = new BaseSteps();
    }
}
