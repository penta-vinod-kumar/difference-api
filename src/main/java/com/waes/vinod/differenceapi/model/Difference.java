package com.waes.vinod.differenceapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * difference values
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Difference {
    private Integer offset;
    private Integer length;
}
