package Runner;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(
        features = {"src/test/resources"},
        glue = {"StepDefinition"},
        plugin = {"pretty",
                "html:target/cucumber-reports/cucumber-pretty"
        },
        tags = {"@api"}
)

public class TestRunner {
}
