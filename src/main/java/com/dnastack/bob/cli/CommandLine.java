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

package com.dnastack.bob.cli;

import com.dnastack.bob.cli.commands.*;
import com.dnastack.bob.cli.exceptions.ExecutionException;
import com.dnastack.bob.client.BeaconNetworkClient;
import com.dnastack.bob.client.BeaconNetworkClientImpl;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.SubCommand;
import org.kohsuke.args4j.spi.SubCommandHandler;
import org.kohsuke.args4j.spi.SubCommands;

/**
 * Represents total parsed command line.
 * It delegates its execution to the actual command instances.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
@Data
public class CommandLine {
    public static final String DEFAULT_BEACON_NETWORK_URL = "https://beacon-network.org/api/";

    @Argument(handler = SubCommandHandler.class, metaVar = "command", usage = "Use one of these commands. To get " +
            "help on each command, type -h with the corresponding command.")
    @SubCommands({
            @SubCommand(name = BeaconCommand.NAME, impl = BeaconCommand.class),
            @SubCommand(name = BeaconsCommand.NAME, impl = BeaconsCommand.class),
            @SubCommand(name = OrganizationCommand.NAME, impl = OrganizationCommand.class),
            @SubCommand(name = OrganizationsCommand.NAME, impl = OrganizationsCommand.class),
            @SubCommand(name = ResponseCommand.NAME, impl = ResponseCommand.class),
            @SubCommand(name = ResponsesCommand.NAME, impl = ResponsesCommand.class)
    })
    private Command command;

    @Option(name = "-u", aliases = "--url", usage = "Beacon Network URL (default = " + DEFAULT_BEACON_NETWORK_URL + ")")
    private String url;

    @Option(name = "-h", aliases = "--help", help = true)
    private Boolean showHelp;

    public String execute() throws ExecutionException {
        String beaconNetworkBaseUrl = StringUtils.isNotBlank(url) ? url : DEFAULT_BEACON_NETWORK_URL;
        BeaconNetworkClient beaconNetworkClient = new BeaconNetworkClientImpl(beaconNetworkBaseUrl);
        return command.execute(beaconNetworkClient);
    }
}
