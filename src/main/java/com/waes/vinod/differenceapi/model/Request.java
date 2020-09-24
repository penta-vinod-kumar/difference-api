package com.waes.vinod.differenceapi.model;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * request model
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    @ApiParam("value of base64 encoded string")
    private String value;
}
