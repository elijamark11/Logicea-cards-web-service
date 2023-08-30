package com.logicea.cards.helper.base;


import com.logicea.cards.helper.dto.APIResponse;

public interface BaseServiceInterface<T> {
    APIResponse create(T t) throws Exception;

    APIResponse update(T t) throws Exception;

    APIResponse delete(long id) throws Exception;

    APIResponse getById(long id) throws Exception;

    APIResponse pagedFetching(Integer pageNo, Integer pageSize, String sortBy, String filterBy, String filterValue, boolean asc) throws Exception;
}