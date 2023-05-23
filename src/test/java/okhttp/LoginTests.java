package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTests {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();//make запрос request to server ...similar to button SEND

    @Test
    public void LoginSuccess() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("noa@gmail.com").password("Nnoa12345$").build();
        RequestBody body = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();
        Response responce  = client.newCall(request).execute();

        Assert.assertTrue(responce.isSuccessful());
        Assert.assertEquals(responce.code(),200);

        AuthResponseDTO responseDTO = gson.fromJson(responce.body().string(),AuthResponseDTO.class);//тут получаем
                                                                                // токен из джейсон делаем объект java
        System.out.print(responseDTO.getToken());

    }
    @Test
    public void WrongEmail() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("noagmail.com").password("Nnoa12345$").build();
        RequestBody body = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();
        Response responce  = client.newCall(request).execute();
        Assert.assertFalse(responce.isSuccessful());
        Assert.assertEquals(responce.code(),401);

        ErrorDTO errorDTO = gson.fromJson(responce.body().string(),ErrorDTO.class);

        Assert.assertEquals(errorDTO.getStatus(),401);
        Assert.assertEquals(errorDTO.getMessage(),"Login or Password incorrect");
        Assert.assertEquals(errorDTO.getPath(),"/v1/user/login/usernamepassword");

    }
    @Test
    public void WrongPassword() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("noa@gmail.com").password("Nnoa123").build();
        RequestBody body = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();
        Response responce  = client.newCall(request).execute();

        Assert.assertFalse(responce.isSuccessful());
        Assert.assertEquals(responce.code(),401);

        ErrorDTO errorDTO = gson.fromJson(responce.body().string(),ErrorDTO.class);
        Assert.assertEquals(errorDTO.getMessage(),"Login or Password incorrect");

    }
    @Test
    public void loginUnregisteredUser() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("mom@gmail.com").password("Nnoa12345$").build();
        RequestBody body = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();
        Response responce  = client.newCall(request).execute();

        Assert.assertFalse(responce.isSuccessful());
        Assert.assertEquals(responce.code(),401);

        ErrorDTO errorDTO = gson.fromJson(responce.body().string(),ErrorDTO.class);
        Assert.assertEquals(errorDTO.getMessage(),"Login or Password incorrect");

    }
}
