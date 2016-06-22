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
import com.dnastack.bob.client.exceptions.NotFoundException;
import com.dnastack.bob.service.dto.OrganizationDto;
import org.kohsuke.args4j.Option;

/**
 * Stores parameters and actually loads organization.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class OrganizationGetCommand extends Command {
    public static final String NAME = "get";

    public static final String ORGANIZATION_ID_OPTION_KEY = "-i";

    @Option(name = ORGANIZATION_ID_OPTION_KEY, aliases = "--id", required = true, usage = "Organization ID")
    private String id;

    @Override
    public String doExecute(BeaconNetworkClient beaconNetworkClient) throws ExecutionException {
        OrganizationDto organization = getOrganization(beaconNetworkClient);
        return Json.pretty(organization);
    }

    private OrganizationDto getOrganization(BeaconNetworkClient beaconNetworkClient) throws ExecutionException {
        try {
            return beaconNetworkClient.getOrganization(id);
        } catch (ForbiddenException | InternalException | NotFoundException e) {
            throw new ExecutionException(e.getMessage(), e);
        }
    }

    @Override
    public String getDescription() {
        return "Loads information on organization by id.";
    }
}
