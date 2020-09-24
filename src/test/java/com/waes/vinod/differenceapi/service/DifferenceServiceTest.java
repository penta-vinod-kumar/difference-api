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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DifferenceServiceTest {

    @Mock
    private DifferenceRepository repository;

    @InjectMocks
    DifferenceService service;

    @Test
    public void saveLeft() {
        Request request = Request.builder().value("jhaldskfnkadsj=").build();
        String id = UUID.randomUUID().toString();
        service.add(id, request, Side.LEFT);
        verify(repository).save(eq(DiffDocument.builder().id(id).left(request.getValue()).build()));
    }

    @Test
    public void saveRight() {
        Request request = Request.builder().value("jhaldskfnkadsj=").build();
        String id = UUID.randomUUID().toString();
        service.add(id, request, Side.RIGHT);
        verify(repository).save(eq(DiffDocument.builder().id(id).right(request.getValue()).build()));
    }

    @Test
    public void differenceWithSameContentOnBothSides() throws Exception {
        String id = UUID.randomUUID().toString();
        DiffDocument diffDocument = DiffDocument.builder().id(id).left("jhaldskfnkadsj=").right("jhaldskfnkadsj=").build();
        when(repository.findById(eq(id))).thenReturn(Optional.of(diffDocument));
        Response response = service.difference(id);
        assertEquals(DifferenceType.EQUALS, response.getDifferenceType(), "Response not formed as expected");
    }

    @Test
    public void differenceWithDifferentSizeContentOnBothSides() throws Exception {
        String id = UUID.randomUUID().toString();
        DiffDocument diffDocument = DiffDocument.builder().id(id).left("jhaldskfnkadsj=").right("jhaldsksdfnkadsj=").build();
        when(repository.findById(eq(id))).thenReturn(Optional.of(diffDocument));
        Response response = service.difference(id);
        assertEquals(DifferenceType.DIFFERENT_SIZE, response.getDifferenceType(), "Response not formed as expected");
    }

    @Test
    public void differenceWithDifferentContentOnBothSides() throws Exception {
        String id = UUID.randomUUID().toString();
        DiffDocument diffDocument = DiffDocument.builder().id(id).left("jhaldskfnkadsj=").right("jhadcskfnkadsj=").build();
        when(repository.findById(eq(id))).thenReturn(Optional.of(diffDocument));
        Response response = service.difference(id);
        assertEquals(DifferenceType.DIFFERENT_CONTENT, response.getDifferenceType(), "Response not formed as expected");
        assertEquals(1, response.getDifferences().size(), "Response not formed as expected");
        assertEquals(Difference.builder().length(2).offset(3).build(), response.getDifferences().get(0), "Response not formed as expected");
    }

    @Test
    public void differenceWithNoContentOnLeftSides() {
        assertThrows(NoContentDefinedOneOrMoreSidesException.class, () -> {
            String id = UUID.randomUUID().toString();
            DiffDocument diffDocument = DiffDocument.builder().id(id).left("jhaldskfnkadsj=").build();
            when(repository.findById(eq(id))).thenReturn(Optional.of(diffDocument));
            service.difference(id);
        });
    }

    @Test
    public void differenceWithNoDocumentWithGivenID() {
        assertThrows(IdentifierNotExistException.class, () -> {
            String id = UUID.randomUUID().toString();
            when(repository.findById(eq(id))).thenReturn(Optional.empty());
            service.difference(id);
        });

    }
}