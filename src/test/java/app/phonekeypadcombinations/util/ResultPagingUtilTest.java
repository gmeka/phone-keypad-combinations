package app.phonekeypadcombinations.util;

import app.phonekeypadcombinations.model.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResultPagingUtilTest {

    private List<String> combinations;

    @BeforeEach
    public void setUp() {
        combinations = IntStream.range(1, 21).mapToObj((n) -> ""+n).collect(Collectors.toList());
    }

    @Test
    void paginateResponse() {

        Result result = ResultPagingUtil.PaginateResponse(combinations, "123", 0, 4);

        assertNotNull(result);
        assertEquals(20, result.getResultCount());
        assertEquals(4, result.getPageSize());
        assertEquals(4, result.getCombinations().size());
        assertEquals("1", result.getCombinations().get(0));
        assertEquals("4", result.getCombinations().get(3));
    }

    @Test
    void paginateResponse_paginate() {

        Result result = ResultPagingUtil.PaginateResponse(combinations, "123", 3, 6);

        assertNotNull(result);
        assertEquals(20, result.getResultCount());
        assertEquals(6, result.getPageSize());
        assertEquals(2, result.getCombinations().size());
        assertEquals("19", result.getCombinations().get(0));
        assertEquals("20", result.getCombinations().get(1));
    }
}