
import Views.LoginView;

public class Main {

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {

            LoginView login = new LoginView();

            login.setLocationRelativeTo(null);
            login.setVisible(true);

        });
    }
}
