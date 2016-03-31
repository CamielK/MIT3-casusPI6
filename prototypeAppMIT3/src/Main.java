import java.util.List;

public class Main {

    public static void main(String[] args) {
        //run main GUI
        GUI gui = new GUI();

        while (true) {
            try { Thread.sleep(1000); } catch (Exception e) { e.printStackTrace(); }
            System.out.println(gui.getMpaaRatingInput());
        }
    }
}