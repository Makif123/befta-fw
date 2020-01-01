package uk.gov.hmcts.befta.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import uk.gov.hmcts.befta.DefaultTestAutomationAdapter;
import uk.gov.hmcts.befta.data.HttpTestData;
import uk.gov.hmcts.befta.data.HttpTestDataSource;
import uk.gov.hmcts.befta.data.JsonStoreHttpTestDataSource;
import uk.gov.hmcts.befta.player.BackEndFunctionalTestScenarioContext;

public class DynamicValueInjectorTest {

    private static final String[] TEST_DATA_RESOURCE_PACKAGES = { "framework-test-data" };
    private static final HttpTestDataSource TEST_DATA_RESOURCE = new JsonStoreHttpTestDataSource(
            TEST_DATA_RESOURCE_PACKAGES);

    private BackEndFunctionalTestScenarioContext scenarioContext;

    @Mock
    private DefaultTestAutomationAdapter taAdapter;

    @Before
    public void prepareScenarioConext() {
        scenarioContext = new BackEndFunctionalTestScenarioContextForTest();

        scenarioContext.initializeTestDataFor("Simple-Test-Data-With-All-Possible-Dynamic-Values");
        
        BackEndFunctionalTestScenarioContext subcontext = new BackEndFunctionalTestScenarioContextForTest();
        subcontext.initializeTestDataFor("Token_Creation_Call");
        subcontext.getTestData().setActualResponse(subcontext.getTestData().getExpectedResponse());

        scenarioContext.setTheInvokingUser(scenarioContext.getTestData().getInvokingUser());
        scenarioContext.addChildContext(subcontext);
    }
    
    @Test
    public void shoudlInjectAllValues() {

        HttpTestData testData = scenarioContext.getTestData();

        DynamicValueInjector underTest = new DynamicValueInjector(taAdapter, testData, scenarioContext);

        Assert.assertEquals("[[DYNAMIC]]", testData.getRequest().getPathVariables().get("uid"));

        underTest.injectDataFromContext();

        Assert.assertEquals("mutlu.sancaktutar@hmcts.net", testData.getRequest().getPathVariables().get("email"));

        Assert.assertEquals("token value", testData.getRequest().getPathVariables().get("token"));
        Assert.assertEquals("token value at index 2", testData.getRequest().getPathVariables().get("token_2"));
        Assert.assertEquals("token value", testData.getRequest().getBody().get("event_token"));
    }

    class BackEndFunctionalTestScenarioContextForTest extends BackEndFunctionalTestScenarioContext {

        @Override
        public void initializeTestDataFor(String testDataId) {
            testData = TEST_DATA_RESOURCE.getDataForTestCall(testDataId);
        }
    }
}

