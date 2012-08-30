package org.robbins.flashcards.webservices.exceptions;

public class WebServicesError {
	private int error_id;
	private String error_name;
	private String error_message;

	public WebServicesError(int error_id, String error_name, String error_message) {
		this.error_id = error_id;
		this.error_name = error_name;
		this.error_message = error_message;
	}

	public int getError_id() {
		return error_id;
	}

	public void setError_id(int error_id) {
		this.error_id = error_id;
	}

	public String getError_name() {
		return error_name;
	}

	public void setError_name(String error_name) {
		this.error_name = error_name;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
}
