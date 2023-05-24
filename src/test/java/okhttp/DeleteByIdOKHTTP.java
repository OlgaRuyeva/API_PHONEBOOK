package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.DeleteByIdResponseDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteByIdOKHTTP {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoib28yMjA3MTlAZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE2ODU1MzQ4OTgsImlhdCI6MTY4NDkzNDg5OH0.E0Gy3f22FwMoRQQYhffvnxcWAvZsww4tk19yxQ5jZdo";
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    String id;
   @BeforeMethod
   public void PreCondition() throws IOException {
        ContactDTO contactDTO = ContactDTO.builder()
                .id("")
                .name("Pola")
                .lastName("Sova")
                .email("qwq@gmail.com")
                .phone("123456789101")
                .address("NY")
                .description("BF")
                .build();
        RequestBody body =  RequestBody.create(gson.toJson(contactDTO),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body)
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();

        DeleteByIdResponseDTO dto = gson.fromJson(response.body().string(),DeleteByIdResponseDTO.class);
        System.out.println(dto.getMessage());

        String[] allId  = dto.getMessage().split(":"); // Contact was added! ID: 5548a72b-17f5-4171-ae7e-dbdfacd38fc5
       //String id ="" + allId[1]+""; "0" "1"
       id = allId[1].trim();

    }

    @Test
    public void deleteContactByIdSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/"+id)
                .delete()
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();

        Assert.assertEquals(response.code(),200);
        DeleteByIdResponseDTO dto = gson.fromJson(response.body().string(),DeleteByIdResponseDTO.class);
        Assert.assertEquals(dto.getMessage(),"Contact was deleted!");
        System.out.println(dto.getMessage());
    }


}
