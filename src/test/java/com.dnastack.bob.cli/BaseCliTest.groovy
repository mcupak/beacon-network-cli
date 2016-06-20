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
import org.apache.commons.lang.StringUtils
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterSuite
import org.testng.annotations.BeforeSuite
import org.testng.annotations.Test

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import static org.assertj.core.api.Assertions.assertThat

/**
 * Helper class for CLI tests.
 * For each test, CLI is run in a separate process, its outputs are read and tested against the test checks.
 * When the java property beaconNetwork.test.url is not specified, the Beacon Network server is mocked, otherwise tests
 * are run against the specified Beacon Network (usually a real one).
 * Not all tests might support integration testing against a real Beacon Network server - this is defined by
 * {@link com.dnastack.bob.cli.BaseCliTest#isIntegrationTestingSupported()}
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
abstract class BaseCliTest {
    static final def CLI_PROCESS_BUILDER_DEFAULTS = [
            "java",
            "-cp", System.getProperty("java.class.path"),
            Cli.class.getName(),
            CommandLine.BEACON_NETWORK_URL_OPTION_KEY
    ]
    static final def MOCK_BOB_PORT = 8089
    static final def MOCK_BOB_SERVER = new WireMockServer(wireMockConfig().port(MOCK_BOB_PORT))
    static boolean mockedTesting

    @BeforeSuite
    void startServer() {
        def beaconNetworkTestUrl = System.properties.getProperty("beaconNetwork.test.url")
        if (StringUtils.isNotBlank(beaconNetworkTestUrl)) {
            setupIntegrationTests(beaconNetworkTestUrl)
        } else {
            setupMockedTests()
        }
    }

    static void setupIntegrationTests(String beaconNetworkTestUrl) {
        mockedTesting = false
        CLI_PROCESS_BUILDER_DEFAULTS.add(beaconNetworkTestUrl)
    }

    static void setupMockedTests() {
        mockedTesting = true
        CLI_PROCESS_BUILDER_DEFAULTS.add(new URL("http", "localhost", MOCK_BOB_PORT, "").toString())
        MOCK_BOB_SERVER.start();
    }

    @AfterSuite
    void stopServer() {
        if (mockedTesting) {
            MOCK_BOB_SERVER.stop();
        }
    }

    @AfterMethod
    void resetMappings() {
        if (mockedTesting) {
            MOCK_BOB_SERVER.resetMappings();
        }
    }

    @Test
    void test() {
        if (!mockedTesting && !isIntegrationTestingSupported()) {
            return
        }

        if (mockedTesting) {
            setupMappings()
        }
        def executionResult = executeClientAndCollectOutput()
        doTest(
                executionResult.clientOutput,
                executionResult.clientErrorOutput,
                executionResult.clientExitValue
        )
    }

    ExecutionResult executeClientAndCollectOutput() {
        def cliProcess = new ProcessBuilder(
                ((CLI_PROCESS_BUILDER_DEFAULTS as String[]) + getClientTestArguments()) as String[]
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

    boolean isIntegrationTestingSupported() { return true }

    abstract void doTest(String clientOutput, String clientErrorOutput, int clientExitValue);

    static void assertExitValueIsSuccessful(int clientExitValue) {
        assertThat(clientExitValue).isEqualTo(0)
    }

    static void assertExitValueIsError(int clientExitValue) {
        assertThat(clientExitValue).isEqualTo(1)
    }

    private static class ExecutionResult {
        String clientOutput
        String clientErrorOutput
        int clientExitValue
    }
}
