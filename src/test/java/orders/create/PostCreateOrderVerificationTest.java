package orders.create;

import common.BaseSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pojo.OrderRequest;

import java.io.FileNotFoundException;

import static utilities.OrderGenerator.generateRandomOrder;
import static specs.RestAssuredSpecs.*;

@Feature("[Create order] POST api/orders")
public class PostCreateOrderVerificationTest {
    private OrderRequest orderReq;
    private BaseSteps baseSteps;
    @Before
    public void setUp() throws Exception {
        setUpSpec(requestSpec(), responseSpecCreateOrderVerified());
    }

    @Test
    @Ignore
    @DisplayName("Create order")
    @Description("This test verifies that an order can be created")
    @Severity(SeverityLevel.BLOCKER)
    public void createNewOrder() throws FileNotFoundException {
        baseSteps = new BaseSteps();
        orderReq = generateRandomOrder(2);
        //baseSteps.createNewOrder(accessToken, orderReq);
    }
}
