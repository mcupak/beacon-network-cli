package com.dnastack.bob.cli.commands;

import com.dnastack.bob.cli.exceptions.ExecutionException;
import com.dnastack.bob.cli.utils.Json;
import com.dnastack.bob.client.BeaconNetworkClient;
import com.dnastack.bob.client.exceptions.ForbiddenException;
import com.dnastack.bob.client.exceptions.InternalException;
import com.dnastack.bob.service.dto.BeaconDto;

import java.util.List;

/**
 * Stores parameters and actually executes the beacons command.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
public class BeaconsCommand extends Command {
    public static final String NAME = "beacons";

    @Override
    public String doExecute(BeaconNetworkClient beaconNetworkClient) throws ExecutionException {
        List<BeaconDto> beacons;
        try {
            beacons = beaconNetworkClient.getBeacons();
        } catch (ForbiddenException | InternalException e) {
            throw new ExecutionException(e.getMessage(), e);
        }
        return Json.pretty(beacons);
    }

    @Override
    public String getDescription() {
        return "Loads information on all available beacons.";
    }
}
