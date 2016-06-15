package com.dnastack.bob.cli.commands;

import com.dnastack.bob.cli.exceptions.ExecutionException;
import com.dnastack.bob.client.BeaconNetworkClient;
import org.apache.commons.lang3.BooleanUtils;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.StringWriter;

/**
 * Represents a single beacon network cli command.
 * Command parameters get injected and then the command is ready to execute itself.
 * Each command has description and help message.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public abstract class Command {
    @Option(name = "-h", aliases = "--help", help = true)
    private Boolean showHelp;

    public String execute(BeaconNetworkClient beaconNetworkClient) throws ExecutionException {
        if (BooleanUtils.isTrue(showHelp)) {
            return getHelp();
        } else {
            return doExecute(beaconNetworkClient);
        }
    }

    protected abstract String doExecute(BeaconNetworkClient beaconNetworkClient) throws ExecutionException;

    protected String getHelp() {
        StringWriter help = new StringWriter();
        help.append(String.format("Description: %s\r\n", getDescription()));
        new CmdLineParser(this).printUsage(help, null);
        return help.toString();
    }

    public String getDescription() {
        return null;
    }
}
