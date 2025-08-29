package cmd_leonardo_samuel;

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
    public String prompt;
    private File directorio;

    public GUIConsola() {
        setTitle("Administrador - Command Prompt");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        directorio = new File(System.getProperty("user.dir"));
        prompt = directorio.getAbsolutePath() + (">");
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

                    if (mf.nuevaCarpeta(directorio, argumento)) {
                        textArea.append("\nDirectorio creado exitosamente.");
                    } else {
                        textArea.append("\nOcurrio un error al crear el directorio.");
                    }
                    break;

                case ("mfile"):

                    if (mf.crearArchivo(directorio, argumento)) {
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

                case ("cd"):
                    File nuevaDir = mf.cambiarCarpeta(directorio, argumento);
                    if (!nuevaDir.equals(directorio)) {
                        directorio = nuevaDir;
                        prompt = directorio.getAbsolutePath() + "> ";
                    } else {
                        textArea.append("\nOcurrió un error al cambiar de carpeta.");
                    }

                    break;

                case ("..."):
                    mf.setFile(directorio.getAbsolutePath());
                    File carpetaAnterior = mf.regresarCarpeta();
                    if (carpetaAnterior != null) {
                        directorio = carpetaAnterior;
                        mf.setFile(directorio.getAbsolutePath());
                        prompt = directorio.getAbsolutePath() + ">";

                    }
                    break;

                case ("dir"):
                    textArea.append(mf.mostrarDir(directorio));
                    break;

                case ("date"):
                    textArea.append("\n" + mf.getDate());
                    break;

                case ("time"):
                    textArea.append("\n" + mf.getTime());
                    break;

                case ("wr"):
                    String[] arg = argumento.split("\\s+", 2);
                    if (arg.length >= 2) {
                        String nombreArch = arg[0];
                        String msg = arg[1];
                        File archEscribir = new File(directorio, nombreArch);
                        mf.escribirWr(archEscribir, msg);
                        textArea.append("\nTexto escrito en el archivo.");
                    } else {
                        textArea.append("\nOcurrio un error, formato incorrecto.");
                    }
                    break;

                case ("rd"):
                    File archivoLeer = new File(directorio, argumento);

                    String contenido = mf.leerFile(archivoLeer);
                    if (!contenido.isEmpty()) {
                        textArea.append("\n-----------\n");
                        textArea.append("\n" + contenido);
                        textArea.append("\n-----------\n");
                    } else {
                        textArea.append("\nEl archivo está vacío");
                    }
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

    public void setPrompt(String promptNuevo) {
        prompt = promptNuevo;
    }

    public void setDirectorio(File directorioNuevo) {
        directorio = directorioNuevo;
    }

    
}
