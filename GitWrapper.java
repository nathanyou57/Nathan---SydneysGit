import java.io.File;

public class GitWrapper { 

    private CopyFile git = new CopyFile();

    /**
     * Initializes a new Git repository.
     * This method creates the necessary directory structure
     * and initial files (index, HEAD) required for a Git repository.
     */
    public void init() throws Exception {
        git.initializerepo(".");
        addRecursively(new File("."));
        git.commit("Git", "Initial Commit");
    };

    

    /**
     * Stages a file for the next commit.
     * This method adds a file to the index file.
     * If the file does not exist, it throws an IOException.
     * If the file is a directory, it throws an IOException.
     * If the file is already in the index, it does nothing.
     * If the file is successfully staged, it creates a blob for the file.
     * @param filePath The path to the file to be staged.
     */
    public void add(String filePath) throws Exception{
        git.storeFileObj(new File(filePath));
        git.storeFileInd(new File(filePath));
    };

    /**
     * Creates a commit with the given author and message.
     * It should capture the current state of the repository by building trees based on the index file,
     * writing the tree to the objects directory,
     * writing the commit to the objects directory,
     * updating the HEAD file,
     * and returning the commit hash.
     * 
     * The commit should be formatted as follows:
     * tree: <tree_sha>
     * parent: <parent_sha>
     * author: <author>
     * date: <date>
     * summary: <summary>
     *
     * @param author  The name of the author making the commit.
     * @param message The commit message describing the changes.
     * @return The SHA1 hash of the new commit.
     */
    public String commit(String author, String message) throws Exception{
        return git.commit(author, message);
    };

     /**
     * EXTRA CREDIT:
     * Checks out a specific commit given its hash.
     * This method should read the HEAD file to determine the "checked out" commit.
     * Then it should update the working directory to match the
     * state of the repository at that commit by tracing through the root tree and
     * all its children.
     *
     * @param commitHash The SHA1 hash of the commit to check out.
     */
    public void checkout(String commitHash) {
        // to-do: implement functionality here

    }



    public void addRecursively(File file) throws Exception {
        // Skip unwanted directories and files
        String name = file.getName();
        if (name.equals("git") || name.equals("src") || name.equals(".git") || name.equals("README.md")) {
            return;
        }
    
        // If it's a regular file, stage it and stop recursion
        if (file.isFile()) {
            add(file.getPath());
            return;
        }
    
        // If it's a directory, loop through its contents
        File[] contents = file.listFiles();
        if (contents == null || contents.length == 0) {
            return;
        }
    
        for (File child : contents) {
            addRecursively(child);
        }
    }
    
}