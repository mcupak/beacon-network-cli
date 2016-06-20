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

package com.dnastack.bob.cli.responses

import com.dnastack.bob.cli.BaseCliTest
import com.dnastack.bob.cli.commands.response.ResponseCommand
import com.dnastack.bob.cli.commands.response.ResponseGetAllCommand
import com.dnastack.bob.cli.utils.JsonHelper
import com.dnastack.bob.client.CommunicationConverter
import com.dnastack.bob.service.dto.BeaconResponseDto
import com.github.tomakehurst.wiremock.common.Json

import static com.dnastack.bob.cli.ITTestData.TEST_RESPONSES
import static com.dnastack.bob.client.BeaconNetworkRetroService.*
import static com.github.tomakehurst.wiremock.client.WireMock.*
import static org.assertj.core.api.Assertions.assertThat

/**
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
class ResponsesSuccessTest extends BaseCliTest {
    @Override
    void setupMappings() {
        MOCK_BOB_SERVER.stubFor(get(urlPathEqualTo("/$RESPONSES_PATH"))
                .withQueryParam(BEACONS_IDS_LIST_KEY, equalTo(CommunicationConverter.convertToString(TEST_RESPONSES.beacon.id as List)))
                .withQueryParam(ALLELE_KEY, equalTo(TEST_RESPONSES.query.first().allele))
                .withQueryParam(CHROMOSOME_KEY, equalTo(TEST_RESPONSES.query.first().chromosome.toString()))
                .withQueryParam(POSITION_KEY, equalTo(TEST_RESPONSES.query.first().position.toString()))
                .withQueryParam(REFERENCE_KEY, equalTo(TEST_RESPONSES.query.first().reference.toString()))

                .willReturn(aResponse()
                .withBody(Json.write(TEST_RESPONSES))))

    }

    @Override
    String[] getClientTestArguments() {
        return [ResponseCommand.NAME,
                ResponseGetAllCommand.NAME,
                ResponseGetAllCommand.CHROMOSOME_OPTION_KEY, TEST_RESPONSES.query.first().chromosome.toString(),
                ResponseGetAllCommand.POSITION_OPTION_KEY, TEST_RESPONSES.query.first().position.toString(),
                ResponseGetAllCommand.ALLELE_OPTION_KEY, TEST_RESPONSES.query.first().allele,
                ResponseGetAllCommand.REFERENCE_OPTION_KEY, TEST_RESPONSES.query.first().reference.toString(),
                ResponseGetAllCommand.BEACONS_IDS_OPTION_KEY, TEST_RESPONSES.beacon.id.join(",")
        ]
    }

    @Override
    void doTest(String clientOutput, String clientErrorOutput, int clientExitValue) {
        def responses = JsonHelper.readCollection(clientOutput, BeaconResponseDto.class)

        if (MOCKED_TESTING) {
            assertThat(responses).isEqualTo(TEST_RESPONSES)
        }
        assertExitValueIsSuccessful(clientExitValue)
    }
}