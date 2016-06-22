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

package com.dnastack.bob.cli.commands.organization;

import com.dnastack.bob.cli.commands.Command;
import com.dnastack.bob.cli.exceptions.ExecutionException;
import com.dnastack.bob.cli.utils.Json;
import com.dnastack.bob.client.BeaconNetworkClient;
import com.dnastack.bob.client.exceptions.ForbiddenException;
import com.dnastack.bob.client.exceptions.InternalException;
import com.dnastack.bob.service.dto.OrganizationDto;

import java.util.List;

/**
 * Stores parameters and actually loads organizations.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class OrganizationGetAllCommand extends Command {
    public static final String NAME = "get-all";

    @Override
    public String doExecute(BeaconNetworkClient beaconNetworkClient) throws ExecutionException {
        List<OrganizationDto> organizations = getOrganizations(beaconNetworkClient);
        return Json.pretty(organizations);
    }

    private List<OrganizationDto> getOrganizations(BeaconNetworkClient beaconNetworkClient) throws ExecutionException {
        try {
            return beaconNetworkClient.getOrganizations();
        } catch (ForbiddenException | InternalException e) {
            throw new ExecutionException(e.getMessage(), e);
        }
    }

    @Override
    public String getDescription() {
        return "Loads information on all available organizations.";
    }
}
