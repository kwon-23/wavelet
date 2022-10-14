import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

class Handler implements URLHandler {
    private List<String> words = new ArrayList<>();
    private List<String> matchWords = new ArrayList<>();

    public String handleRequest(URI url){  
        if (url.getPath().equals("/")) {
            if (words.size() == 0){
                return String.format("Enter some words with /add?s=");
            }
            else {
                return String.format("Enter something to search with /search?s=.");
            }
        }
        else {
            System.out.println("Path: "+ url.getPath());
            if(url.getPath().contains("/add")){
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    words.add(parameters[1]);
                    return String.format("%s", parameters[1]);
                }
            }
            else if(url.getPath().contains("/search")){
                String[] parameters = url.getQuery().split("=");
                if(parameters[0].equals("s")) {
                    for(int x = 0; x < words.size(); x++){
                        if((words.get(x)).contains((parameters[1])) == true){
                            matchWords.add(words.get(x));
                        }
                    }
                    String result = "";
                    for(int y = 0; y < matchWords.size(); y++){
                        result += matchWords.get(y);
                    }
                    matchWords.clear();
                    return String.format("%s", result);
                }
            }
            else if(url.getPath().contains("/size")){
                return String.format(" %d", words.size());
            }
            return "404 Not Found";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number");
            return;
        }

        int port = Integer.parseInt(args[0]);
        Server.start(port, new Handler());
    }
}