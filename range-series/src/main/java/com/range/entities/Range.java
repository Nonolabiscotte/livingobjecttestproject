package com.range.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

// NEW : RECORD class
public record Range (String label, int lowerBoundary, int upperBoundary) {
    public boolean isItemInBoundaries(int item) {
        return this.lowerBoundary() <= item && this.upperBoundary() > item;
    }
}
