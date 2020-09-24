package com.waes.vinod.differenceapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * difference document model to store data in mongodb
 */
@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiffDocument {

    @Id
    private String id;
    private String left;
    private String right;
}
