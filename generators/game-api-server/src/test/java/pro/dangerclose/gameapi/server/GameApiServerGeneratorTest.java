package pro.dangerclose.gameapi.server;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openapitools.codegen.ClientOptInput;
import org.openapitools.codegen.CodegenConstants;
import org.openapitools.codegen.DefaultGenerator;
import org.openapitools.codegen.TestUtils;
import org.openapitools.codegen.config.CodegenConfigurator;
import org.openapitools.codegen.languages.AbstractPhpCodegen;
import org.openapitools.codegen.languages.PhpSymfonyServerCodegen;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Path;
import static org.testng.Assert.*;

public class GameApiServerGeneratorTest {

    @Test
    public void testInitialConfigValues() throws Exception {
        final GameApiServerGenerator codegen = new GameApiServerGenerator();
        codegen.processOpts();

        Assert.assertEquals(codegen.additionalProperties().get(CodegenConstants.HIDE_GENERATION_TIMESTAMP), Boolean.TRUE);
        Assert.assertEquals(codegen.isHideGenerationTimestamp(), true);
    }

    @Test
    public void testSettersForConfigValues() throws Exception {
        final GameApiServerGenerator codegen = new GameApiServerGenerator();
        codegen.setHideGenerationTimestamp(false);
        codegen.processOpts();

        Assert.assertEquals(codegen.additionalProperties().get(CodegenConstants.HIDE_GENERATION_TIMESTAMP), Boolean.FALSE);
        Assert.assertEquals(codegen.isHideGenerationTimestamp(), false);
    }

    @Test
    public void testAdditionalPropertiesPutForConfigValues() throws Exception {
        final GameApiServerGenerator codegen = new GameApiServerGenerator();
        codegen.additionalProperties().put(CodegenConstants.HIDE_GENERATION_TIMESTAMP, false);
        codegen.processOpts();

        Assert.assertEquals(codegen.additionalProperties().get(CodegenConstants.HIDE_GENERATION_TIMESTAMP), Boolean.FALSE);
        Assert.assertEquals(codegen.isHideGenerationTimestamp(), false);
    }

    @Test
    public void testGeneratePing() throws Exception {
        Map<String, Object> properties = new HashMap<>();

        File output = Files.createTempDirectory("test").toFile();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("game-api-server")
                .setAdditionalProperties(properties)
                .setInputSpec("https://raw.githubusercontent.com/openapitools/openapi-generator/master/modules/openapi-generator/src/test/resources/3_0/ping.yaml") // or from the server/
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(clientOptInput).generate();

        Assert.assertEquals(files.size(), 34);
        TestUtils.ensureContainsFile(files, output, ".coveralls.yml");
        TestUtils.ensureContainsFile(files, output, ".gitignore");
        TestUtils.ensureContainsFile(files, output, ".openapi-generator-ignore");
        TestUtils.ensureContainsFile(files, output, ".openapi-generator/FILES");
        TestUtils.ensureContainsFile(files, output, ".openapi-generator/VERSION");
        TestUtils.ensureContainsFile(files, output, ".php_cs.dist");
        TestUtils.ensureContainsFile(files, output, ".travis.yml");
        TestUtils.ensureContainsFile(files, output, "autoload.php");
        TestUtils.ensureContainsFile(files, output, "composer.json");
        TestUtils.ensureContainsFile(files, output, "git_push.sh");
        TestUtils.ensureContainsFile(files, output, "phpunit.xml.dist");
        TestUtils.ensureContainsFile(files, output, "README.md");
        TestUtils.ensureContainsFile(files, output, "Api/ApiServer.php");
        TestUtils.ensureContainsFile(files, output, "Api/DefaultApiInterface.php");
        TestUtils.ensureContainsFile(files, output, "Controller/Controller.php");
        TestUtils.ensureContainsFile(files, output, "Controller/DefaultController.php");
        TestUtils.ensureContainsFile(files, output, "DependencyInjection/Compiler/GameApiApiPass.php");
        TestUtils.ensureContainsFile(files, output, "DependencyInjection/GameApiExtension.php");
        TestUtils.ensureContainsFile(files, output, "docs/Api/DefaultApiInterface.md");
        TestUtils.ensureContainsFile(files, output, "GameApiBundle.php");
        TestUtils.ensureContainsFile(files, output, "Resources/config/routing.yaml");
        TestUtils.ensureContainsFile(files, output, "Resources/config/services.yaml");
        TestUtils.ensureContainsFile(files, output, "Service/JmsSerializer.php");
        TestUtils.ensureContainsFile(files, output, "Service/SerializerInterface.php");
        TestUtils.ensureContainsFile(files, output, "Service/StrictJsonDeserializationVisitor.php");
        TestUtils.ensureContainsFile(files, output, "Service/StrictJsonDeserializationVisitorFactory.php");
        TestUtils.ensureContainsFile(files, output, "Service/SymfonyValidator.php");
        TestUtils.ensureContainsFile(files, output, "Service/TypeMismatchException.php");
        TestUtils.ensureContainsFile(files, output, "Service/DefaultDriverFactory.php");
        TestUtils.ensureContainsFile(files, output, "Service/ValidatorInterface.php");
        TestUtils.ensureContainsFile(files, output, "Tests/Api/DefaultApiInterfaceTest.php");
        TestUtils.ensureContainsFile(files, output, "Tests/AppKernel.php");
        TestUtils.ensureContainsFile(files, output, "Tests/Controller/ControllerTest.php");
        TestUtils.ensureContainsFile(files, output, "Tests/test_config.yaml");

        output.deleteOnExit();
    }

    @Test
    public void testGeneratePingWithDifferentSourceDirectory() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put(AbstractPhpCodegen.SRC_BASE_PATH, "src");

        File output = Files.createTempDirectory("test").toFile();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("game-api-server")
                .setAdditionalProperties(properties)
                .setInputSpec("https://raw.githubusercontent.com/openapitools/openapi-generator/master/modules/openapi-generator/src/test/resources/3_0/ping.yaml") // or from the server/
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));
        
        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(clientOptInput).generate();

        Assert.assertEquals(files.size(), 34);
        TestUtils.ensureContainsFile(files, output, ".coveralls.yml");
        TestUtils.ensureContainsFile(files, output, ".gitignore");
        TestUtils.ensureContainsFile(files, output, ".openapi-generator-ignore");
        TestUtils.ensureContainsFile(files, output, ".openapi-generator/FILES");
        TestUtils.ensureContainsFile(files, output, ".openapi-generator/VERSION");
        TestUtils.ensureContainsFile(files, output, ".php_cs.dist");
        TestUtils.ensureContainsFile(files, output, ".travis.yml");
        TestUtils.ensureContainsFile(files, output, "autoload.php");
        TestUtils.ensureContainsFile(files, output, "composer.json");
        TestUtils.ensureContainsFile(files, output, "git_push.sh");
        TestUtils.ensureContainsFile(files, output, "phpunit.xml.dist");
        TestUtils.ensureContainsFile(files, output, "README.md");
        TestUtils.ensureContainsFile(files, output, "docs/Api/DefaultApiInterface.md");
        TestUtils.ensureContainsFile(files, output, "src/Api/ApiServer.php");
        TestUtils.ensureContainsFile(files, output, "src/Api/DefaultApiInterface.php");
        TestUtils.ensureContainsFile(files, output, "src/Controller/Controller.php");
        TestUtils.ensureContainsFile(files, output, "src/Controller/DefaultController.php");
        TestUtils.ensureContainsFile(files, output, "src/DependencyInjection/Compiler/GameApiApiPass.php");
        TestUtils.ensureContainsFile(files, output, "src/DependencyInjection/GameApiExtension.php");
        TestUtils.ensureContainsFile(files, output, "src/GameApiBundle.php");
        TestUtils.ensureContainsFile(files, output, "src/Resources/config/routing.yaml");
        TestUtils.ensureContainsFile(files, output, "src/Resources/config/services.yaml");
        TestUtils.ensureContainsFile(files, output, "src/Service/JmsSerializer.php");
        TestUtils.ensureContainsFile(files, output, "src/Service/SerializerInterface.php");
        TestUtils.ensureContainsFile(files, output, "src/Service/StrictJsonDeserializationVisitor.php");
        TestUtils.ensureContainsFile(files, output, "src/Service/StrictJsonDeserializationVisitorFactory.php");
        TestUtils.ensureContainsFile(files, output, "src/Service/SymfonyValidator.php");
        TestUtils.ensureContainsFile(files, output, "src/Service/TypeMismatchException.php");
        TestUtils.ensureContainsFile(files, output, "src/Service/DefaultDriverFactory.php");
        TestUtils.ensureContainsFile(files, output, "src/Service/ValidatorInterface.php");
        TestUtils.ensureContainsFile(files, output, "src/Tests/Api/DefaultApiInterfaceTest.php");
        TestUtils.ensureContainsFile(files, output, "src/Tests/AppKernel.php");
        TestUtils.ensureContainsFile(files, output, "src/Tests/Controller/ControllerTest.php");
        TestUtils.ensureContainsFile(files, output, "src/Tests/test_config.yaml");

        output.deleteOnExit();
    }
}
