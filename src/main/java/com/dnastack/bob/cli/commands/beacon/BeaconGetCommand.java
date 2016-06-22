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

package com.dnastack.bob.cli.commands.beacon;

import com.dnastack.bob.cli.commands.Command;
import com.dnastack.bob.cli.exceptions.ExecutionException;
import com.dnastack.bob.cli.utils.Json;
import com.dnastack.bob.client.BeaconNetworkClient;
import com.dnastack.bob.client.exceptions.ForbiddenException;
import com.dnastack.bob.client.exceptions.InternalException;
import com.dnastack.bob.client.exceptions.NotFoundException;
import com.dnastack.bob.service.dto.BeaconDto;
import org.kohsuke.args4j.Option;

/**
 * Stores parameters and actually loads beacon.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class BeaconGetCommand extends Command {
    public static final String NAME = "get";

    public static final String BEACON_ID_OPTION_KEY = "-i";

    @Option(name = BEACON_ID_OPTION_KEY, aliases = "--id", required = true, usage = "Beacon ID")
    private String id;

    protected String doExecute(BeaconNetworkClient beaconNetworkClient) throws ExecutionException {
        BeaconDto beacon = getBeacon(beaconNetworkClient);
        return Json.pretty(beacon);
    }

    private BeaconDto getBeacon(BeaconNetworkClient beaconNetworkClient) throws ExecutionException {
        try {
            return beaconNetworkClient.getBeacon(id);
        } catch (ForbiddenException | InternalException | NotFoundException e) {
            throw new ExecutionException(e.getMessage(), e);
        }
    }

    @Override
    public String getDescription() {
        return "Loads information on beacon by id.";
    }
}
