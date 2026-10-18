import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            // fall back to the default look and feel
        }
        SwingUtilities.invokeLater(() -> {
            String nameX = ask("Player 1 name (plays X):", "Player 1");
            String nameO = ask("Player 2 name (plays O):", "Player 2");
            new GameFrame(nameX, nameO).setVisible(true);
        });
    }

    private static String ask(String prompt, String fallback) {
        String s = JOptionPane.showInputDialog(null, prompt, fallback);
        return (s == null || s.trim().isEmpty()) ? fallback : s.trim();
    }
}
