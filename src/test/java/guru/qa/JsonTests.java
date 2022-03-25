package guru.qa;

import guru.qa.domain.Playlist;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonTests {
    @Test
    void jsonTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Playlist playlist = mapper.readValue(Paths.get("src/test/resources/simple.json").toFile(), Playlist.class);
        assertThat(playlist.music).contains("Our Last Night - HUMBLE");
    }
}
