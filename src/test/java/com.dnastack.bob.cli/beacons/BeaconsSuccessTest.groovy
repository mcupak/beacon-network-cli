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

package com.dnastack.bob.cli.beacons

import com.dnastack.bob.cli.BaseMockedCliTest
import com.dnastack.bob.cli.TestData
import com.dnastack.bob.cli.commands.BeaconsCommand
import com.dnastack.bob.cli.utils.JsonHelper
import com.dnastack.bob.service.dto.BeaconDto
import com.github.tomakehurst.wiremock.common.Json

import static com.dnastack.bob.client.BeaconNetworkRetroService.BEACONS_PATH
import static com.github.tomakehurst.wiremock.client.WireMock.*
import static org.assertj.core.api.Assertions.assertThat

/**
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
class BeaconsSuccessTest extends BaseMockedCliTest {
    @Override
    void setupMappings() {
        BaseMockedCliTest.MOCK_BOB_SERVER.stubFor(get(urlEqualTo("/$BEACONS_PATH"))

                .willReturn(aResponse()
                .withBody(Json.write(TestData.TEST_BEACONS))))
    }

    @Override
    String[] getClientTestArguments() {
        return [BeaconsCommand.NAME]
    }

    @Override
    void doTest(String clientOutput, String clientErrorOutput, int clientExitValue) {
        def beacons = JsonHelper.readCollection(clientOutput, BeaconDto.class)
        assertThat(beacons).isEqualTo(TestData.TEST_BEACONS)

        assertExitValueIsSuccessful(clientExitValue)
    }
}
