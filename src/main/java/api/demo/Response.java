package api.demo;

public class Response {
    private String header;
    private String message;
    private int statusCode;

    public Response() {
    }

    public Response(String message, int statusCode, String header) {
        this.message = message;
        this.statusCode = statusCode;
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return "Response{" +
                "header='" + header + '\'' +
                ", message='" + message + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }
}
