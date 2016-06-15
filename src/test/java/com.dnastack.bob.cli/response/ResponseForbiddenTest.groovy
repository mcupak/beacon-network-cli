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

package com.dnastack.bob.cli.response

import com.dnastack.bob.cli.BaseMockedCliTest
import com.dnastack.bob.cli.commands.ResponseCommand
import com.github.tomakehurst.wiremock.common.Json
import org.apache.http.HttpStatus

import static com.dnastack.bob.cli.TestData.getTEST_ERROR_FORBIDDEN
import static com.dnastack.bob.cli.TestData.getTEST_RESPONSE_AMPLAB
import static com.dnastack.bob.client.BeaconNetworkRetroService.*
import static com.github.tomakehurst.wiremock.client.WireMock.*
import static org.assertj.core.api.Assertions.assertThat

/**
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
class ResponseForbiddenTest extends BaseMockedCliTest {
    void setupMappings() {
        MOCK_BOB_SERVER.stubFor(get(urlPathEqualTo("/$RESPONSES_PATH/$TEST_RESPONSE_AMPLAB.beacon.id"))
                .withQueryParam(CHROMOSOME_KEY, equalTo(TEST_RESPONSE_AMPLAB.query.chromosome.toString()))
                .withQueryParam(POSITION_KEY, equalTo(TEST_RESPONSE_AMPLAB.query.position.toString()))
                .withQueryParam(ALLELE_KEY, equalTo(TEST_RESPONSE_AMPLAB.query.allele))
                .withQueryParam(REFERENCE_KEY, equalTo(TEST_RESPONSE_AMPLAB.query.reference.toString()))

                .willReturn(aResponse()
                .withStatus(HttpStatus.SC_FORBIDDEN)
                .withBody(Json.write(TEST_ERROR_FORBIDDEN))))
    }

    @Override
    String[] getClientTestArguments() {
        return [ResponseCommand.NAME,
                "-c", TEST_RESPONSE_AMPLAB.query.chromosome.toString(),
                "-p", TEST_RESPONSE_AMPLAB.query.position.toString(),
                "-a", TEST_RESPONSE_AMPLAB.query.allele,
                "-r", TEST_RESPONSE_AMPLAB.query.reference,
                "-i", TEST_RESPONSE_AMPLAB.beacon.id
        ]
    }

    @Override
    void doTest(String clientOutput, String clientErrorOutput, int clientExitValue) {
        assertThat(clientErrorOutput?.trim()).isEqualTo(TEST_ERROR_FORBIDDEN.getMessage())
        assertExitValueIsError(clientExitValue)
    }
}
