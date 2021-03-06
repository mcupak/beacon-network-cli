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
import com.dnastack.bob.cli.exceptions.ExecutionException;
import com.dnastack.bob.client.BeaconNetworkClient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.spi.SubCommand;
import org.kohsuke.args4j.spi.SubCommandHandler;
import org.kohsuke.args4j.spi.SubCommands;

/**
 * Parent command for all response related commands.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseCommand extends Command {
    public static final String NAME = "response";

    @Argument(handler = SubCommandHandler.class, metaVar = "Response Command", required = true,
            usage = "Response commands. To get help on each command, type -h with the corresponding command.")
    @SubCommands({
            @SubCommand(name = ResponseGetCommand.NAME, impl = ResponseGetCommand.class),
            @SubCommand(name = ResponseGetAllCommand.NAME, impl = ResponseGetAllCommand.class)
    })
    private Command command;

    @Override
    public String doExecute(BeaconNetworkClient beaconNetworkClient) throws ExecutionException {
        return command.execute(beaconNetworkClient);
    }

    @Override
    public String getDescription() {
        return "Beacon response commands.";
    }
}
