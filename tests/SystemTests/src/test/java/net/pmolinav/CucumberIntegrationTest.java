package net.pmolinav;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import net.pmolinav.steps.BaseSystemTest;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
public class CucumberIntegrationTest extends BaseSystemTest {
}