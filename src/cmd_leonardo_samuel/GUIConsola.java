
import cmd_leonardo_samuel.CMDFunciones;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;

public class GUIConsola extends JFrame {

    private JTextArea textArea;
    private CMDFunciones mf = new CMDFunciones();
    private String prompt = System.getProperty("user.dir") + (">");

    public GUIConsola() {
        setTitle("Administrador - Command Prompt");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);
        textArea.setCaretColor(Color.WHITE);

        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    String fullText = textArea.getText();
                    int lastPromptIndex = fullText.lastIndexOf(prompt);
                    String command = fullText.substring(lastPromptIndex + prompt.length()).trim();

                    comandos(command);

                    textArea.append("\n" + prompt);
                }
            }
        });

        textArea.setText(prompt);

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);

        setVisible(true);
    }

    private void comandos(String command) {
        try {

            String[] textosEnConsola = command.trim().split("\\s+", 2);

            String comando = textosEnConsola[0];
            String argumento = textosEnConsola.length > 1 ? textosEnConsola[1] : "";

            switch (comando.toLowerCase()) {
                case ("mkdir"):
                    mf.setFile(argumento);
                    if (mf.nuevaCarpeta()) {
                        textArea.append("\nDirectorio creado exitosamente.");
                    } else {
                        textArea.append("\nOcurrio un error al crear el directorio.");
                    }
                    break;

                case ("mfile"):
                    mf.setFile(argumento);
                    if (mf.crearArchivo()) {
                        textArea.append("\nArchivo creado exitosamente.");
                    } else {
                        textArea.append("\nOcurrio un error al crear el directorio.");
                    }
                    break;

                case ("rm"):
                    mf.setFile(argumento);
                    if (mf.borrar(mf.getFile())) {
                        if (mf.isDirectorio()) {
                            textArea.append("\nDirectorio eliminado exitosamente.");
                        } else if (!mf.isDirectorio()) {
                            textArea.append("\nArchivo eliminado exitosamente.");
                        } else {
                            textArea.append("\nOcurrio un error. Argumento no reconocido");
                        }
                    }

                    break;
                    
                case("cd"):
                    
                    break;
                case ("exit"):
                    textArea.append("\nCerrando...");
                    System.exit(0);
                    break;

                default:

                    textArea.append("\nComando no reconocido: " + command);
                    break;

            }

        } catch (InputMismatchException e) {
            textArea.append("\nPor favor ingrese un comando correcto.");
        } catch (NullPointerException e) {
            textArea.append("\nDebes seleccionar un archivo/directorio por lo menos.");
        } catch (IOException e) {
            textArea.append("\nError en Disco: " + e.getMessage());
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUIConsola());
    }
}
