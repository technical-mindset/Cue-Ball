package com.cueball.portal.response;

import lombok.Data;

@Data
public class GenericListResponse<T> {
    private int count;
    private T data;

    public GenericListResponse(T response, int count) {
        this.count = count;
        this.data = response;
    }

    public static <T> GenericListResponse<T> success(T response, int count) {
        return new GenericListResponse<T>(response, count);
    }

    public static <T> GenericListResponse<T> error(T data) {
        return new GenericListResponse<T>(data, 0);
    }

    public static <T> GenericListResponse<T> internalServerError(T data) {
        return new GenericListResponse<T>(data, 0);
    }
}
