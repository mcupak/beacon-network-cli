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

import com.dnastack.bob.cli.commands.Command;
import com.dnastack.bob.cli.commands.beacon.BeaconCommand;
import com.dnastack.bob.cli.commands.organization.OrganizationCommand;
import com.dnastack.bob.cli.commands.response.ResponseCommand;
import com.dnastack.bob.cli.exceptions.ExecutionException;
import com.dnastack.bob.client.BeaconNetworkClient;
import com.dnastack.bob.client.BeaconNetworkClientImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Builder;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.SubCommand;
import org.kohsuke.args4j.spi.SubCommandHandler;
import org.kohsuke.args4j.spi.SubCommands;

import java.io.StringWriter;

/**
 * Represents total parsed command line.
 * It delegates its execution to the actual command instances.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
@AllArgsConstructor
@Builder
@Data
@RequiredArgsConstructor
public class CommandLine {
    public static final String DEFAULT_BEACON_NETWORK_URL = "https://beacon-network.org/api/";

    public static final String BEACON_NETWORK_URL_OPTION_KEY = "-u";

    @Argument(handler = SubCommandHandler.class, metaVar = "Main Command", required = true, usage = "Use one of " +
            "these commands. To get help on each command, Type -h with the corresponding command.")
    @SubCommands({
            @SubCommand(name = BeaconCommand.NAME, impl = BeaconCommand.class),
            @SubCommand(name = OrganizationCommand.NAME, impl = OrganizationCommand.class),
            @SubCommand(name = ResponseCommand.NAME, impl = ResponseCommand.class)
    })
    private Command command;

    @Option(name = BEACON_NETWORK_URL_OPTION_KEY, aliases = "--url",
            usage = "Beacon Network URL (default = " + DEFAULT_BEACON_NETWORK_URL + ")")
    private String url;

    @Option(name = "-h", aliases = "--help", help = true)
    private Boolean showHelp;

    public String execute() throws ExecutionException {
        if (BooleanUtils.isTrue(showHelp)) {
            return getHelp();
        } else {
            return doExecute();
        }
    }

    private String doExecute() throws ExecutionException {
        String beaconNetworkBaseUrl = StringUtils.isNotBlank(url) ? url : DEFAULT_BEACON_NETWORK_URL;
        BeaconNetworkClient beaconNetworkClient = new BeaconNetworkClientImpl(beaconNetworkBaseUrl);
        return command.execute(beaconNetworkClient);
    }

    private String getHelp() {
        StringWriter help = new StringWriter();
        help.append("Beacon Network CLI Usage:\r\n");
        new CmdLineParser(this).printUsage(help, null);
        return help.toString();
    }
}
