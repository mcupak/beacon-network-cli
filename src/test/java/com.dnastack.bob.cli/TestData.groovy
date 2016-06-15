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
import com.dnastack.bob.service.dto.BeaconResponseDto
import com.dnastack.bob.service.dto.OrganizationDto
import com.dnastack.bob.service.dto.QueryDto

import static com.dnastack.bob.service.dto.AlleleDto.A
import static com.dnastack.bob.service.dto.ChromosomeDto.CHR9
import static com.dnastack.bob.service.dto.ReferenceDto.*

/**
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
class TestData {
    def static final TEST_ORGANIZATION_AMPLAB = new OrganizationDto(
            id: "amplab",
            name: "AMPLab, UC Berkeley",
            url: "https://amplab.cs.berkeley.edu/",
            address: "465 Soda Hall, MC-1776, Berkeley, CA, United States",
            logo: "iVBORw5ErkJggg==" as byte[]
    );

    def static final TEST_ORGANIZATION_BIOBASE = new OrganizationDto(
            "id": "biobase",
            "name": "Biobase - HGMD",
            "url": "http://www.genomics.cn/",
            "address": "Building NO.7, BGI Park, No.21 Hongan 3rd Street, Yantian District, Shenzhen 518083,China",
            "logo": "/9j/4AAQSkZJRgABAQAAAQABAA/bQFfOiigHCOC6/wCSLSTztkE/+3T/AIUUUV7tHrXmAv/Z" as byte[]
    );

    def static final TEST_ORGANIZATIONS = [
            TEST_ORGANIZATION_AMPLAB,
            TEST_ORGANIZATION_BIOBASE
    ]

    def static final TEST_BEACON_AMPLAB = new BeaconDto(
            id: "amplab",
            name: "AMPLab",
            organization: "AMPLab, UC Berkeley",
            visible: true,
            enabled: true,
            supportedReferences: [
                    HG38,
                    HG18,
                    HG19
            ]
    );

    def static final TEST_BEACON_BOB = new BeaconDto(
            id: "hgmd",
            name: "Biobase - HGMD",
            organization: "University of California",
            description: "HGMD description",
            visible: true,
            enabled: true
    );

    def static final TEST_QUERY = new QueryDto(
            allele: A,
            chromosome: CHR9,
            position: 12345,
            reference: HG18
    )

    def static final TEST_RESPONSE_AMPLAB = new BeaconResponseDto(
            beacon: TEST_BEACON_AMPLAB,
            query: TEST_QUERY,
            response: true,
            info: [
                    "Clinical_significance_citations": "PMID:21990134",
                    "Assertion_method_citation"      : "http://enigmaconsortium.org/documents/ENIGMA_Rules_2015-03-26.pdf"
            ]
    )

    def static final TEST_RESPONSE_BOB = new BeaconResponseDto(
            beacon: TEST_BEACON_BOB,
            query: TEST_QUERY,
            response: true,
            info: [
                    "Date_last_evaluated": "8/10/15",
                    "HGVS_protein"       : "p.(Tyr856His)",
            ]
    )

    def static final TEST_RESPONSES = [
            TEST_RESPONSE_AMPLAB,
            TEST_RESPONSE_BOB
    ]

    def static final TEST_ERROR_FORBIDDEN = new ErrorDto(
            message: "Operation is forbidden",
            status: ErrorStatus.FORBIDDEN
    )

    def static final TEST_BEACONS = [
            TEST_BEACON_AMPLAB,
            TEST_BEACON_BOB
    ];
}
