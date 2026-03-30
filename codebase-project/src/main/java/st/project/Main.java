package st.project;

public class Main {

    public static boolean result = false;

    public static void main(String [] args){

            new GameGUI();
            result = true;
        
    }

    public boolean getIsInterface(){
        return result;
    }
}
