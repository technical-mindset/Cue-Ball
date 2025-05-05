package com.cueball.portal.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {

	public static final GenericResponse SUCCESS = new GenericResponse(200, "200", "SUCESS"),
			ERROR = new GenericResponse(200, "500", "Internal Server Error");

	private int status;
	private String message;
	private T response;
	
	public GenericResponse(GenericResponse<T> genResp) {
		this.status = genResp.getStatus();
		this.response = genResp.getResponse();
		this.message = genResp.getMessage();
	}

	public static <T> GenericResponse<T> success(T response) {
		return new GenericResponse<T>(200, "success", response);
	}

	public static <T> GenericResponse<T> error(T response) {
		return new GenericResponse<T>(400, "bad response", response);
	}

	public static <T> GenericResponse<T> internalServerError(T response) {
		return new GenericResponse<T>(500, "Internal Server Error", response);
	}

	public static <T> GenericResponse<T> genericMessage(T response, String message, int code) {
		return new GenericResponse<T>(code, message, response);
	}
}
