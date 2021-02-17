package myapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class ResponseContainer<T> {

    @SerializedName("message")
    private final String message;
    private final boolean error;

    @SerializedName("mysqli_error_message")
    private final String mysqliErrorMessage;

    private final T object;

    public ResponseContainer(String message, boolean error, String mysqliErrorMessage, T object) {
        this.message = message;
        this.error = error;
        this.mysqliErrorMessage = mysqliErrorMessage;
        this.object = object;
    }

    public static ResponseContainer parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        ResponseContainer responseContainer = gson.fromJson(response, ResponseContainer.class);
        return responseContainer;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }

    public String getMysqliErrorMessage() {
        return mysqliErrorMessage;
    }

    public T getObject() {
        return object;
    }
}
