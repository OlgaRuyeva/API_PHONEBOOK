package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegistrationTestsOKHTTP {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();
@Test
    public void RegistrationSuccess() throws IOException {
        AuthRequestDTO auth= AuthRequestDTO.builder()
                .username("oo22071922@gmail.com")
                .password("OO220719!bb")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(),200);

    AuthResponseDTO responseDTO = gson.fromJson(response.body().string(),AuthResponseDTO.class);//тут получаем
    // токен из джейсон делаем объект java
    System.out.print(responseDTO.getToken());

    }}
