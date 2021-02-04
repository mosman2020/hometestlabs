package com.hometest.dto;

import lombok.Data;

@Data
public class EntityId<T> {
    private T id;
}