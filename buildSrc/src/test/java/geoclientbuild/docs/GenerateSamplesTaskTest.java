package geoclientbuild.docs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.CopyOption;
import java.nio.file.StandardCopyOption;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static geoclientbuild.docs.DocumentationPlugin.GENERATE_SAMPLES_TASK_NAME;

// See https://github.com/spring-projects/spring-boot/blob/main/buildSrc/src/test/java/org/springframework/boot/build/ConventionsPluginTests.java
public class GenerateSamplesTaskTest {

	private static final String API_KEY_ENV_VARIABLE_NAME = "GEOCLIENT_API_KEY";
	private static final String API_KEY_DEFAULT_VALUE = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static final String BASE_URI_ENV_VARIABLE_NAME = "GEOCLIENT_BASE_URI";
	private static final String BASE_URI_DEFAULT_VALUE = "http://localhost:8080/geoclient/v2";
	private static final String JAVA_USE_SYSTEM_PROXY_VARIABLE_NAME = "JAVA_USE_SYSTEM_PROXY";
	private static final String JAVA_USE_SYSTEM_PROXY_DEFAULT_VALUE = "false";

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String apiKey;
	private String baseUri;
    private File buildFile;
	private String javaSystemProxy;
    private File projectDir;
	private File requestsFile;
	private Map<String, String> testEnvironment;

    @BeforeEach
    void setup(@TempDir File projectDir) throws IOException {
        this.projectDir = projectDir;
		this.apiKey = environmentVariable(API_KEY_ENV_VARIABLE_NAME, API_KEY_DEFAULT_VALUE);
		this.baseUri = environmentVariable(BASE_URI_ENV_VARIABLE_NAME, BASE_URI_DEFAULT_VALUE);
		this.javaSystemProxy = environmentVariable(JAVA_USE_SYSTEM_PROXY_VARIABLE_NAME, JAVA_USE_SYSTEM_PROXY_DEFAULT_VALUE);
        this.buildFile = new File(this.projectDir, "build.gradle");
		this.requestsFile = new File(this.projectDir, "requests.json");
        File settingsFile = new File(this.projectDir, "settings.gradle");
		this.testEnvironment = new HashMap<>();
		this.testEnvironment.put(API_KEY_ENV_VARIABLE_NAME, this.apiKey);
		this.testEnvironment.put(BASE_URI_ENV_VARIABLE_NAME, this.baseUri);
		this.testEnvironment.put(JAVA_USE_SYSTEM_PROXY_VARIABLE_NAME, this.javaSystemProxy);
        try (PrintWriter out = new PrintWriter(new FileWriter(settingsFile))) {
            out.println("rootProject.name = 'samples-test'");
        }
		Path cwd = Path.of("src", "main", "resources", "requests.json");
		Files.copy(cwd, Path.of(this.requestsFile.toURI()), StandardCopyOption.REPLACE_EXISTING);
		logger.warn("Project requests file: '{}' (exists? {}).", cwd.toFile().getAbsolutePath(), cwd.toFile().exists());
		logger.warn("TestKit requests file: '{}' (exists? {}).", this.requestsFile.getPath(), this.requestsFile.exists());
		logger.warn("{}: using default value? {}.", API_KEY_ENV_VARIABLE_NAME, API_KEY_DEFAULT_VALUE.equals(this.apiKey));
		logger.warn("{}: '{}' (default value? {}).", BASE_URI_ENV_VARIABLE_NAME, this.baseUri, BASE_URI_DEFAULT_VALUE.equals(this.baseUri));
		logger.warn("{}: '{}' (default value? {}).", JAVA_USE_SYSTEM_PROXY_VARIABLE_NAME, this.javaSystemProxy, JAVA_USE_SYSTEM_PROXY_DEFAULT_VALUE.equals(this.javaSystemProxy));
    }

	@Test
	void testGenerateSamplesTaskWorks() throws IOException {
		try (PrintWriter out = new PrintWriter(new FileWriter(this.buildFile))) {
			out.println("plugins {");
			out.println("    id 'geoclientbuild-docs'");
			out.println("}");
			out.println();
			out.println("def headers = ['Cache-Control': 'no-cache', 'Ocp-Apim-Subscription-Key': '" + this.apiKey + "']");
			out.println("tasks.named('generateSamples') {");
			out.println("    requestsFile = file('" + this.requestsFile.getAbsolutePath() + "')");
			out.println("    destinationDirectory = file('" + this.projectDir.getAbsolutePath() + "/build')");
			out.println("    serviceUrl = '" + this.baseUri + "'");
			out.println("    httpHeaders = ['Cache-Control': 'no-cache', 'Ocp-Apim-Subscription-Key': '" + this.apiKey + "']");
			out.println();
            out.println("    doLast {");
			out.println("        println \"requestsFile: ${requestsFile.get()}\"");
			out.println("        println \"httpHeaders: ${httpHeaders.get()}\"");
			out.println("    }");
			out.println("}");
		}
		assertThat(runGradle(this.testEnvironment, GENERATE_SAMPLES_TASK_NAME, "-Djava.net.useSystemProxies=" + this.javaSystemProxy, "--stacktrace").getOutput())
			.contains("requestsFile: " + this.requestsFile.getAbsolutePath())
			.contains("httpHeaders: [Cache-Control:no-cache, Ocp-Apim-Subscription-Key:");
	}

	private BuildResult runGradle(Map<String, String> environment, String... args) {
		return GradleRunner.create()
			.withProjectDir(this.projectDir)
			.withEnvironment(environment)
			.withArguments(args)
			.withPluginClasspath()
			.forwardOutput()
			.build();
	}

	private String environmentVariable(String name, String defaultValue) {
		String result = System.getenv(name);
		if(result != null) {
			return result;
		}
		return defaultValue;
	}
}
