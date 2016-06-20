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

import com.dnastack.bob.cli.utils.JsonHelper
import com.dnastack.bob.service.dto.BeaconDto
import com.dnastack.bob.service.dto.BeaconResponseDto
import com.dnastack.bob.service.dto.OrganizationDto
import com.github.tomakehurst.wiremock.common.Json

/**
 * Contains integration tests test data.
 * This data should match real data that Beacon Network returns, therefore can't be changed arbitrarily. The data can be
 * used for both mocked and integration tests.
 * Usually, integration test data is read form json files since real test data tend to be big and it'l also be easier to
 * update the data when needed.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class ITTestData {
    public static final OrganizationDto TEST_ORGANIZATION_AMPLAB =
            readFromJsonFile("organization/amplab.json", OrganizationDto)

    public static final BeaconResponseDto TEST_RESPONSE_AMPLAB =
            readFromJsonFile("response/brca-exchange.json", BeaconResponseDto)

    public static final List<BeaconResponseDto> TEST_RESPONSES =
            readCollectionFromJsonFile("responses/amplab-AND-brca-exchange.json", BeaconResponseDto)

    public static final BeaconDto TEST_BEACON_AMPLAB =
            readFromJsonFile("beacon/amplab.json", BeaconDto)

    private static <T> T readFromJsonFile(String jsonFileName, Class clazz) {
        String json = ITTestData.class.getResource("/it_test_data/$jsonFileName").text
        return (T) Json.read(json, clazz)
    }

    private static <T> T readCollectionFromJsonFile(String jsonFileName, Class clazz) {
        String json = ITTestData.class.getResource("/it_test_data/$jsonFileName").text
        return (T) JsonHelper.readCollection(json, clazz)
    }
}
