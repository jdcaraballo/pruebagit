package prueba.subiendoGit.deuna.runners;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "src.test.java.prueba.subiendoGit.deunastepdefinitions.TestManualStep.java",
        tags = {"@SubiendoGit"},
        strict = true


//        Prueba de commit desde git
)
public class TestManualRunner {
}
