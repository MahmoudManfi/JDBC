package eg.edu.alexu.csd.oop.db.cs58;

public class StringQuery {

    public String Optimization(String query){

        query = query.trim().replaceAll("\\s+", " ").toLowerCase();

        query = simplificationForString(query);

        return query;
    }

    private String simplificationForString(String query) {

        for (int i = 0; i < query.length(); i++) {

            if (query.charAt(i) == '\'') {
                i++;
                while (i<query.length() && query.charAt(i) !='\'') i++;
            }
            if (i<query.length())
                if (query.charAt(i) == ',' || query.charAt(i) == '=' || query.charAt(i) == '>' || query.charAt(i) == '<' || query.charAt(i) == ')' || query.charAt(i) == '(') {

                    if (i-1>=0 && query.charAt(i-1) == ' ') {
                        query = query.substring(0,i-1) + query.substring(i,query.length()); i--;
                    }
                    if (i+1<query.length() && query.charAt(i+1) == ' ') {
                        query = query.substring(0,i+1) + query.substring(i+2,query.length());i--;
                    }

                }
        }
        return query;
    }

}
