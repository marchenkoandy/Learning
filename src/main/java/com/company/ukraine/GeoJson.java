package com.company.ukraine;

import lombok.Data;

@Data
public class GeoJson {
    private String type;
    private Geometry geometry;
}
