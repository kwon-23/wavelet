import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    int num = 0;

    public String handleRequest(URI url){
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<String> matchWords = new ArrayList<String>();
        String result = "";
        if (url.getPath().equals("/")) {
            if (words.size() == 0){
                return String.format("Enter some words with /add?s=.");
            }
            else {
                // Maybe print out all the words? not quite sure how just yet
                //for(int i = 0; i < words.size(); i++){}
                return String.format("Enter something to search with /search?s=.");
            }
        }
        else {
            System.out.println("Path: "+ url.getPath());
            if(url.getPath().contains("/add")){
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    words.add((parameters[1]));
                    return String.format("%s", parameters[1]);
                }
            }
            else if(url.getPath().contains("/search")){
                String[] parameters = url.getQuery().split("=");
                if(parameters[0].equals("s")) {
                    for(int x = 0; x < words.size(); x++){
                        if((words.get(x)).contains((String)(parameters[1])) == true){
                            matchWords.add(words.get(x));
                        }
                    }
                    for(int y = 0; y < matchWords.size(); y++){
                        result += matchWords.get(y);
                    }
                    matchWords.clear();
                    return String.format("%s", result);
                }
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