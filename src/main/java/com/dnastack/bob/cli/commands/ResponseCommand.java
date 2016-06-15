package com.dnastack.bob.cli.commands;

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
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kohsuke.args4j.Option;

/**
 * Stores parameters and actually executes the response command.
 *
 * @author Artem (tema.voskoboynick@gmail.com)
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseCommand extends Command {
    public static final String NAME = "response";

    @Option(name = "-c", aliases = "--chromosome", required = true, handler = EnumByIdOptionHandler.class,
            usage = "Chromosome")
    private ChromosomeDto chromosome;

    @Option(name = "-p", aliases = "--position", required = true, usage = "Position")
    private Long position;

    @Option(name = "-a", aliases = "--allele", required = true, handler = EnumByIdOptionHandler.class, usage = "Allele")
    private AlleleDto allele;

    @Option(name = "-r", aliases = "--reference", required = true, handler = EnumByIdOptionHandler.class,
            usage = "Reference")
    private ReferenceDto reference;

    @Option(name = "-i", aliases = "--id", required = true, usage = "Beacon ID")
    private String beaconId;

    @Override
    public String doExecute(BeaconNetworkClient beaconNetworkClient) throws ExecutionException {
        BeaconResponseDto response;
        try {
            response = beaconNetworkClient.getResponse(chromosome, position, allele, reference, beaconId);
        } catch (ForbiddenException | InternalException | NotFoundException e) {
            throw new ExecutionException(e.getMessage(), e);
        }
        return Json.pretty(response);
    }

    @Override
    public String getDescription() {
        return "Requests beacon information on the specified genetic mutation.\r\n" +
                "Beacon.response = true, when the response is YES.\r\n" +
                "Beacon.response = false or null, when the response is NO, or the beacon had problems answering the query.";
    }
}
