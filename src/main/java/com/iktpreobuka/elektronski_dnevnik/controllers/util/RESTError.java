package com.iktpreobuka.elektronski_dnevnik.controllers.util;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.elektronski_dnevnik.security.Views;

public class RESTError {
	@JsonView(Views.Public.class)
    private int code;

    @JsonView(Views.Public.class)
    private String message;

	public RESTError(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public RESTError() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
