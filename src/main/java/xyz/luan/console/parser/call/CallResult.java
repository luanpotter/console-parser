package xyz.luan.console.parser.call;

public enum CallResult {
    SUCCESS(0), ERROR(1), INVALID_COMMAND(2), QUIT(0);

    private int responseCode;

    private CallResult(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
