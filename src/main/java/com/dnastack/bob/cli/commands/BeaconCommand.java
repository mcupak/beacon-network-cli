package com.dnastack.bob.cli.commands;

import com.dnastack.bob.cli.exceptions.ExecutionException;
import com.dnastack.bob.cli.utils.Json;
import com.dnastack.bob.client.BeaconNetworkClient;
import com.dnastack.bob.client.exceptions.ForbiddenException;
import com.dnastack.bob.client.exceptions.InternalException;
import com.dnastack.bob.client.exceptions.NotFoundException;
import com.dnastack.bob.service.dto.BeaconDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kohsuke.args4j.Option;

/**
 * Stores parameters and actually executes the beacon command.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BeaconCommand extends Command {
    public static final String NAME = "beacon";

    @Option(name = "-i", aliases = "--id", required = true, usage = "Beacon ID")
    private String id;

    protected String doExecute(BeaconNetworkClient beaconNetworkClient) throws ExecutionException {
        BeaconDto beacon;
        try {
            beacon = beaconNetworkClient.getBeacon(id);
        } catch (ForbiddenException | InternalException | NotFoundException e) {
            throw new ExecutionException(e.getMessage(), e);
        }
        return Json.pretty(beacon);
    }

    @Override
    public String getDescription() {
        return "Loads information on beacon by id.";
    }
}
