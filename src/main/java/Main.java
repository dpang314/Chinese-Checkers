import javax.swing.JFrame;

class Main {
  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            JFrame.setDefaultLookAndFeelDecorated(true);
            new GUI();
        }
    });
  }
}