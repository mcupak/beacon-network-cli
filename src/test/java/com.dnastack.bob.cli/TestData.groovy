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

import com.dnastack.bob.client.ErrorDto
import com.dnastack.bob.client.ErrorStatus
import com.dnastack.bob.service.dto.BeaconDto
import com.dnastack.bob.service.dto.OrganizationDto

import static com.dnastack.bob.cli.ITTestData.TEST_BEACON_AMPLAB
import static com.dnastack.bob.cli.ITTestData.TEST_ORGANIZATION_AMPLAB

/**
 * Contains general test data that can be used and changed any time,
 * whereas ITTestData {@link com.dnastack.bob.cli.ITTestData} data can't be changed arbitrarily.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class TestData {
    def public static final TEST_ORGANIZATION_BIOBASE = new OrganizationDto(
            "id": "biobase",
            "name": "Biobase - HGMD",
            "url": "http://www.genomics.cn/",
            "address": "Building NO.7, BGI Park, No.21 Hongan 3rd Street, Yantian District, Shenzhen 518083,China",
            "logo": "/9j/4AAQSkZJRgABAQAAAQABAA/bQFfOiigHCOC6/wCSLSTztkE/+3T/AIUUUV7tHrXmAv/Z" as byte[]
    );

    def public static final TEST_ORGANIZATIONS = [
            TEST_ORGANIZATION_AMPLAB,
            TEST_ORGANIZATION_BIOBASE
    ]

    def public static final TEST_BEACON_BOB = new BeaconDto(
            id: "hgmd",
            name: "Biobase - HGMD",
            organization: "University of California",
            description: "HGMD description",
            visible: true,
            enabled: true
    );

    def public static final TEST_BEACONS = [
            TEST_BEACON_AMPLAB,
            TEST_BEACON_BOB
    ];

    def public static final TEST_ERROR_FORBIDDEN = new ErrorDto(
            message: "Operation is forbidden",
            status: ErrorStatus.FORBIDDEN
    )
}
