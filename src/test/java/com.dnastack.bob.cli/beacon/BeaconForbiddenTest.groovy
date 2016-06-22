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

package com.dnastack.bob.cli.beacon

import com.dnastack.bob.cli.BaseCliTest
import com.dnastack.bob.cli.commands.beacon.BeaconCommand
import com.dnastack.bob.cli.commands.beacon.BeaconGetCommand
import com.github.tomakehurst.wiremock.common.Json
import org.apache.http.HttpStatus

import static com.dnastack.bob.cli.ITTestData.TEST_BEACON_AMPLAB
import static com.dnastack.bob.cli.TestData.TEST_ERROR_FORBIDDEN
import static com.dnastack.bob.client.BeaconNetworkRetroService.BEACONS_PATH
import static com.github.tomakehurst.wiremock.client.WireMock.*
import static org.assertj.core.api.Assertions.assertThat

/**
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
class BeaconForbiddenTest extends BaseCliTest {
    @Override
    void setupMappings() {
        MOCK_BOB_SERVER.stubFor(get(urlEqualTo("/$BEACONS_PATH/$TEST_BEACON_AMPLAB.id"))

                .willReturn(aResponse()
                .withStatus(HttpStatus.SC_FORBIDDEN)
                .withBody(Json.write(TEST_ERROR_FORBIDDEN))))
    }

    @Override
    boolean isIntegrationTestingSupported() {
        return false
    }

    @Override
    String[] getClientTestArguments() {
        return [BeaconCommand.NAME,
                BeaconGetCommand.NAME,
                BeaconGetCommand.BEACON_ID_OPTION_KEY, TEST_BEACON_AMPLAB.id]
    }

    @Override
    void doTest(String clientOutput, String clientErrorOutput, int clientExitValue) {
        assertThat(clientErrorOutput?.trim()).isEqualTo(TEST_ERROR_FORBIDDEN.message)
        assertExitValueIsError(clientExitValue)
    }
}