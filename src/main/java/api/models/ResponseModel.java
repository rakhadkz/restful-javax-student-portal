package api.models;

public class ResponseModel {
    private int status;
    private String message;

    public ResponseModel() {
    }

    public ResponseModel(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseModel(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
