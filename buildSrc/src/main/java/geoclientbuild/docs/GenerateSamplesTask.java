package geoclientbuild.docs;

java.io.File;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

abstract public class GenerateSamplesTask extends DefaultTask {

    private final ObjectMapper mapper = new ObjectMapper();

    @Input
    abstract public MapProperty<String, String> getHttpHeaders();

    @InputFile
    abstract public RegularFileProperty getRequestsFile();

    @OutputDirectory
    abstract public DirectoryProperty getDestinationDirectory();

    @TaskAction
    public void generateSamples() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        File file = getRequestsFile().getAsFile().get();
        List<Request> requests = loadRequests(file);
        //RestClient restClient =
    }

    private List<Request> loadRequests(File file) {
        try {
            JsonNode node = mapper.readTree(file);
            JsonNode requestsNode = node.get("requests");
            TypeReference<List<Request>> typeReference = new TypeReference<List<Request>>() {};
            List<Request> requests = this.objectMapper.convertValue(requestsNode, typeReference);
            return requests;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String format(String response) {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        JsonNode root = mapper.readTree(response.body());
        return mapper.writeValueAsString(root);
    }
}
