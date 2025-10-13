import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class GitTester {

    public static void main(String[] args) throws Exception {
        // 1) Prepare a working project
        File inner = new File("myProgram/inner");
        inner.mkdirs();

        Files.writeString(Paths.get("myProgram/hello.txt"),
                "hello from top level\n", StandardCharsets.UTF_8);
        Files.writeString(Paths.get("myProgram/inner/world.txt"),
                "hello from inner folder\n", StandardCharsets.UTF_8);

        // 2) Use the wrapper
        GitWrapper gw = new GitWrapper();

        // Ensure repo is initialized (creates git/, objects, index, HEAD)
        gw.init();

        // 3) Stage files
        gw.add("myProgram/hello.txt");
        gw.add("myProgram/inner/world.txt");

        // 4) Commit
        String commitHash = gw.commit("John Doe", "Initial commit");
        System.out.println("Committed: " + commitHash);
    }
}
