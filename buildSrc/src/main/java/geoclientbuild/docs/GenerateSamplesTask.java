package geoclientbuild.docs;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

//import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

abstract public class GenerateSamplesTask extends DefaultTask {

    private final ObjectMapper mapper = new ObjectMapper();

    @Input
    abstract public MapProperty<String, String> getHttpHeaders();

    @Input
    abstract public Property<String> getServiceUrl();

    @InputFile
    abstract public RegularFileProperty getRequestsFile();

    @OutputDirectory
    abstract public DirectoryProperty getDestinationDirectory();

    @TaskAction
    public void generateSamples() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        File file = getRequestsFile().getAsFile().get();
        List<Request> requests = loadRequests(file);
        RestClient restClient = new RestClient(getServiceUrl().get(), getHttpHeaders().get());
        for(Request request: requests) {
            try {
                String response = restClient.call(request);
                getLogger().lifecycle(response);
            } catch (Exception e) {
                getLogger().error(e.getMessage());
                throw new RuntimeException("Build failed:", e);
            }
        }
    }

    private List<Request> loadRequests(File file) {
        try {
            JsonNode node = mapper.readTree(file);
            JsonNode requestsNode = node.get("requests");
            TypeReference<List<Request>> typeReference = new TypeReference<List<Request>>() {};
            List<Request> requests = mapper.convertValue(requestsNode, typeReference);
            return requests;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String format(String response) throws JsonProcessingException {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        JsonNode root = mapper.readTree(response);
        return mapper.writeValueAsString(root);
    }
}
