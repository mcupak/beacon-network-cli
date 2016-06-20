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

package com.dnastack.bob.cli.commands.response;

import com.dnastack.bob.cli.commands.Command;
import com.dnastack.bob.cli.converters.EnumByIdOptionHandler;
import com.dnastack.bob.cli.exceptions.ExecutionException;
import com.dnastack.bob.cli.utils.Json;
import com.dnastack.bob.client.BeaconNetworkClient;
import com.dnastack.bob.client.exceptions.ForbiddenException;
import com.dnastack.bob.client.exceptions.InternalException;
import com.dnastack.bob.client.exceptions.NotFoundException;
import com.dnastack.bob.service.dto.AlleleDto;
import com.dnastack.bob.service.dto.BeaconResponseDto;
import com.dnastack.bob.service.dto.ChromosomeDto;
import com.dnastack.bob.service.dto.ReferenceDto;
import org.kohsuke.args4j.Option;

/**
 * Stores parameters and actually queries the beacon.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class ResponseGetCommand extends Command {
    public static final String NAME = "get";

    public static final String CHROMOSOME_OPTION_KEY = "-c";
    public static final String POSITION_OPTION_KEY = "-p";
    public static final String ALLELE_OPTION_KEY = "-a";
    public static final String REFERENCE_OPTION_KEY = "-r";
    public static final String BEACON_ID_OPTION_KEY = "-i";

    @Option(name = CHROMOSOME_OPTION_KEY, aliases = "--chromosome", required = true, handler = EnumByIdOptionHandler.class,
            usage = "Chromosome")
    private ChromosomeDto chromosome;

    @Option(name = POSITION_OPTION_KEY, aliases = "--position", required = true, usage = "Position")
    private Long position;

    @Option(name = ALLELE_OPTION_KEY, aliases = "--allele", required = true, handler = EnumByIdOptionHandler.class,
            usage = "Allele")
    private AlleleDto allele;

    @Option(name = REFERENCE_OPTION_KEY, aliases = "--reference", required = true, handler = EnumByIdOptionHandler.class,
            usage = "Reference")
    private ReferenceDto reference;

    @Option(name = BEACON_ID_OPTION_KEY, aliases = "--id", required = true, usage = "Beacon ID")
    private String beaconId;

    @Override
    public String doExecute(BeaconNetworkClient beaconNetworkClient) throws ExecutionException {
        BeaconResponseDto response = getBeaconResponse(beaconNetworkClient);
        return Json.pretty(response);
    }

    private BeaconResponseDto getBeaconResponse(BeaconNetworkClient beaconNetworkClient) throws ExecutionException {
        try {
            return beaconNetworkClient.getResponse(chromosome, position, allele, reference, beaconId);
        } catch (ForbiddenException | InternalException | NotFoundException e) {
            throw new ExecutionException(e.getMessage(), e);
        }
    }

    @Override
    public String getDescription() {
        return "Requests beacon information on the specified genetic mutation.\r\n" +
                "Beacon.response = true, when the response is YES.\r\n" +
                "Beacon.response = false or null, when the response is NO, or the beacon had problems answering the query.";
    }
}
