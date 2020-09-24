package com.waes.vinod.differenceapi.controller;

import com.waes.vinod.differenceapi.exception.IdentifierNotExistException;
import com.waes.vinod.differenceapi.exception.NoContentDefinedOneOrMoreSidesException;
import com.waes.vinod.differenceapi.model.Request;
import com.waes.vinod.differenceapi.model.Response;
import com.waes.vinod.differenceapi.model.Side;
import com.waes.vinod.differenceapi.service.DifferenceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * provides all service endpoints starting at '/v1/diff'.
 */
@RestController
@RequestMapping(value = "/v1/diff/{id}")
@RequiredArgsConstructor
@Api(value = "provides endpoints to feed and identify diff of 2 base64 encoded strings ")
public class DifferenceController {

    private final DifferenceService service;

    /**
     * endpoint to store given base64 data on side
     *
     * @param id      document Id
     * @param side    data storage side(LEFT/RIGHT)
     * @param request data
     */
    @PutMapping(value = "/{side}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "add base64 encoded string on either left or right side of document")
    public void addLeft(@ApiParam(value = "identifier of document") @PathVariable("id") String id, @ApiParam(value = "side of document possible values (LEFT, RIGHT)", required = true, allowableValues = "LEFT,RIGHT") @PathVariable("side") String side, @RequestBody Request request) {
        service.add(id, request, Side.valueOf(side.toUpperCase()));
    }

    /**
     * endpoint to find the differences of document
     *
     * @param id document id
     * @return observations of comparison of document both sides
     * @throws IdentifierNotExistException             when provided ID doesn't exist
     * @throws NoContentDefinedOneOrMoreSidesException when provided ID does not have one side(LEFT/RIGHT) of document
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("provides differences between two base64 encoded strings")
    public Response difference(@PathVariable("id") String id) throws IdentifierNotExistException, NoContentDefinedOneOrMoreSidesException {
        return service.difference(id);

    }
}
