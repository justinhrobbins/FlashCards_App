
package org.robbins.flashcards.webservices.exceptions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tag")
@XmlAccessorType(XmlAccessType.FIELD)
public class WebServicesError {

    private int errorId;

    private String errorName;

    private String errorMessage;

    public WebServicesError() {
    }

    public WebServicesError(int errorId, String errorName, String errorMessage) {
        this.errorId = errorId;
        this.errorName = errorName;
        this.errorMessage = errorMessage;
    }

    public int getErrorId() {
        return errorId;
    }

    public void setErrorId(int errorId) {
        this.errorId = errorId;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
