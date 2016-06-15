/*
 * Copyright 2016 Artem (tema.voskoboynick@gmail.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.dnastack.bob.cli

import com.github.tomakehurst.wiremock.WireMockServer
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterSuite
import org.testng.annotations.BeforeSuite
import org.testng.annotations.Test

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import static org.assertj.core.api.Assertions.assertThat

/**
 * Mock class for CLI integration tests.
 * For each test, CLI is run in a separate process with mocked client url, its outputs are read and tested
 * against the test checks.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
abstract class BaseMockedCliTest {
    static final def MOCK_BOB_PORT = 8089
    static final def MOCK_BOB_SERVER = new WireMockServer(wireMockConfig().port(MOCK_BOB_PORT))
    static final String[] MOCKED_CLI_PROCESS_BUILDER_DEFAULTS = [
            "java",
            "-cp", System.getProperty("java.class.path"),
            Cli.class.getName(),
            "--url",
            new URL("http", "localhost", MOCK_BOB_PORT, "").toString()
    ]

    @BeforeSuite
    void startServer() {
        MOCK_BOB_SERVER.start();
    }

    @AfterSuite
    void stopServer() {
        MOCK_BOB_SERVER.stop();
    }

    @AfterMethod
    void resetMappings() {
        MOCK_BOB_SERVER.resetMappings();
    }

    @Test
    void test() {
        setupMappings()
        def executionResult = executeClientAndCollectOutput()
        doTest(
                executionResult.clientOutput,
                executionResult.clientErrorOutput,
                executionResult.clientExitValue
        )
    }

    ExecutionResult executeClientAndCollectOutput() {
        def cliProcess = new ProcessBuilder(
                (MOCKED_CLI_PROCESS_BUILDER_DEFAULTS + getClientTestArguments()) as String[]
        ).start()

        def standardOutput = new StringBuilder()
        def standardErrorOutput = new StringBuilder()
        cliProcess.waitForProcessOutput(standardOutput, standardErrorOutput)

        return new ExecutionResult(
                clientOutput: standardOutput,
                clientErrorOutput: standardErrorOutput,
                clientExitValue: cliProcess.exitValue()
        )
    }

    void setupMappings() {}

    abstract String[] getClientTestArguments();

    abstract void doTest(String clientOutput, String clientErrorOutput, int clientExitValue);

    void assertExitValueIsSuccessful(int clientExitValue) {
        assertThat(clientExitValue).isEqualTo(0)
    }

    void assertExitValueIsError(int clientExitValue) {
        assertThat(clientExitValue).isEqualTo(1)
    }

    private static class ExecutionResult {
        String clientOutput
        String clientErrorOutput
        int clientExitValue
    }
}
