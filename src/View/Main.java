package View;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author AYU7-WN
 */
public class Main {

    public static void main(String[] args) {

        String vlc = registerVLC();

        while (vlc == null) {
            vlc = registerVLC();
        }

        setLookAndFeel();
        new Reproductor(vlc).setVisible(true);

    }

    static boolean registerDLL(File file) {

        File[] files = file.listFiles();

        for (File f : files) {
            if (f.getName().equals("libvlc.dll")) {
                return true;
            }
        }

        for (File f : files) {
            if (f.getName().equals("libvlccore.dll")) {
                return true;
            }
        }

        return false;
    }

    static String registerVLC() {

        String os = System.getProperty("os.name");
        String arch = System.getProperty("os.arch");

        if (os.contains("Windows") && arch.contains("64")) {
            File f = new File("C:/Program Files/VideoLAN/VLC/");
            if (f.exists()) {
                return f.getAbsolutePath();
            }
        } else {
            File f = new File("C:/Program Files (x86)/VideoLAN/VLC/");
            if (f.exists()) {
                return f.getAbsolutePath();
            }
        }

        JOptionPane.showMessageDialog(null, "CAMINHO PADRÃO DO VLC NÃO ENCONTRADO");
        return null;

    }

    static String findVLC() {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione caminho do VLC...");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int op = fileChooser.showOpenDialog(null);

        if (op == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (registerDLL(file)) {
                return file.getAbsolutePath();
            } else {
                JOptionPane.showMessageDialog(null, "CAMINHO DO VLC NÃO REGISTRADO");
            }
        } else {
            System.exit(0);
        }

        return null;
    }

    static void setLookAndFeel() {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reproductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

}
