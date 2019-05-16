package optvm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import optvm.api.requests.OptimizationRequest;
import optvm.entities.Cloud;
import optvm.entities.Datacenter;
import optvm.entities.Host;
import optvm.entities.VM;
import optvm.utils.OptVMFaker;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GenerateJSON {

    private static final String POLICY_ID = "5ce837aaa2f2550607b7bc20";

    public static void main(String[] args) {
        OptVMFaker faker = new OptVMFaker(1, 20, 5);

        OptimizationRequest request = new OptimizationRequest();
        List<Cloud> clouds = faker.makeFakeClouds(Arrays.asList("AWS", "AZURE", "Private", "GCP", "TEST"));

        Host host = faker.makeHost();
        VM vm = faker.makeVM();
        Cloud fromCloud = faker.makeCloud("AWS");
        Datacenter datacenter = faker.makeDatacenter();

        request.setClouds(clouds);
        request.setSourceHost(host);
        request.setSourceCloud(fromCloud);
        request.setSourceDC(datacenter);
        request.setVm(vm);
        request.setPolicyId(POLICY_ID);
        request.setSubsetSize(4);

        Gson gson = new Gson();
        Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter file = new FileWriter("request_pretty.json")) {
            file.write(gsonPretty.toJson(request));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter file = new FileWriter("request.json")) {
            file.write(gson.toJson(request));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
