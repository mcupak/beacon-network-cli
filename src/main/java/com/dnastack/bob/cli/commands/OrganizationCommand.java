package com.dnastack.bob.cli.commands;

import com.dnastack.bob.cli.exceptions.ExecutionException;
import com.dnastack.bob.cli.utils.Json;
import com.dnastack.bob.client.BeaconNetworkClient;
import com.dnastack.bob.client.exceptions.ForbiddenException;
import com.dnastack.bob.client.exceptions.InternalException;
import com.dnastack.bob.client.exceptions.NotFoundException;
import com.dnastack.bob.service.dto.OrganizationDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kohsuke.args4j.Option;

/**
 * Stores parameters and actually executes the organization command.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrganizationCommand extends Command {
    public static final String NAME = "organization";

    @Option(name = "-i", aliases = "--id", required = true, usage = "Organization ID")
    private String id;

    @Override
    public String doExecute(BeaconNetworkClient beaconNetworkClient) throws ExecutionException {
        OrganizationDto organization;
        try {
            organization = beaconNetworkClient.getOrganization(id);
        } catch (ForbiddenException | InternalException | NotFoundException e) {
            throw new ExecutionException(e.getMessage(), e);
        }
        return Json.pretty(organization);
    }

    @Override
    public String getDescription() {
        return "Loads information on organization by id.";
    }
}
