package com.waes.vinod.differenceapi.service;

import com.waes.vinod.differenceapi.exception.IdentifierNotExistException;
import com.waes.vinod.differenceapi.exception.NoContentDefinedOneOrMoreSidesException;
import com.waes.vinod.differenceapi.model.DiffDocument;
import com.waes.vinod.differenceapi.model.Difference;
import com.waes.vinod.differenceapi.model.DifferenceType;
import com.waes.vinod.differenceapi.model.Request;
import com.waes.vinod.differenceapi.model.Response;
import com.waes.vinod.differenceapi.model.Side;
import com.waes.vinod.differenceapi.repository.DifferenceRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * service class for difference controller with all the implementations needed to store, retrieve data data from mongodb and to compare sides of document
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class DifferenceService {
    private final DifferenceRepository repository;

    /**
     * adds data to document on given side
     *
     * @param id      id of document
     * @param request requested data
     * @param side    side of document (LEFT/RIGHT)
     */
    public void add(@NonNull String id, Request request, Side side) {
        log.debug("adding {} to {} with content {}", side, id, request.getValue());
        DiffDocument diffDocument = repository.findById(id).orElse(new DiffDocument());
        repository.save(DiffDocument.builder()
                .id(id)
                .left(Side.LEFT.equals(side) ? request.getValue() : diffDocument.getLeft())
                .right(Side.RIGHT.equals(side) ? request.getValue() : diffDocument.getRight())
                .build());
    }

    /**
     * finds the difference between two sides for given document id
     *
     * @param id document id
     * @return response with observations
     * @throws NoContentDefinedOneOrMoreSidesException when no content present for one side of document
     * @throws IdentifierNotExistException             when document doesn't exist for give ID
     */
    public Response difference(@NonNull String id) throws NoContentDefinedOneOrMoreSidesException, IdentifierNotExistException {
        log.debug("finding differences for {}", id);
        Optional<DiffDocument> document = repository.findById(id);
        if (!document.isPresent()) {
            throw new IdentifierNotExistException();
        }
        if (document.get().getLeft() == null || document.get().getRight() == null) {
            throw new NoContentDefinedOneOrMoreSidesException();
        }
        final DiffDocument diffDocument = document.get();

        final byte[] left = diffDocument.getLeft().getBytes();
        final byte[] right = diffDocument.getRight().getBytes();

        if (Arrays.equals(left, right)) {
            return Response.builder().differenceType(DifferenceType.EQUALS).build();
        }
        if (left.length != right.length) {
            return Response.builder().differenceType(DifferenceType.DIFFERENT_SIZE).build();
        }
        return Response.builder().differenceType(DifferenceType.DIFFERENT_CONTENT).differences(processDifferences(left, right)).build();
    }

    private List<Difference> processDifferences(byte[] left, byte[] right) {
        List<Difference> differences = new ArrayList<>();

        int length = 0;
        int offset = -1;
        for (int i = 0; i <= left.length; i++) {
            if (i < left.length && left[i] != right[i]) {
                length++;
                if (offset < 0) {
                    offset = i;
                }
            } else if (offset != -1) {
                differences.add(new Difference(offset, length));
                length = 0;
                offset = -1;
            }
        }
        return differences;
    }
}
