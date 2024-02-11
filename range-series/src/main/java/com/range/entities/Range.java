package com.range.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Range {
    /* Range name */
    String label;
    /* Range Lower boundary*/
    
    int lowerBoundary;
    /* Range Upper boundary */
    int upperBoundary;

    public boolean isItemInBoundaries(int item) {
        return this.getLowerBoundary() <= item && this.getUpperBoundary() > item;
    }
}
