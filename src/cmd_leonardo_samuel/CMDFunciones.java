/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cmd_leonardo_samuel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;

/**
 *
 * @author hnleo
 */
public class CMDFunciones {

    private File file = null;

    public void setFile(String dir) {
        file = new File(dir);
    }

    public boolean nuevaCarpeta(File directorio, String nombreCarpeta) throws IOException {
        File nuevaCarpeta = new File(directorio, nombreCarpeta);
        return nuevaCarpeta.mkdir();
    }

    public boolean crearArchivo(File directorio, String nombreArchivo) throws IOException {
        File nuevoArchivo = new File(directorio, nombreArchivo);
        return nuevoArchivo.createNewFile();

    }

    public boolean borrar(File file) throws IOException {
        if (file.isDirectory()) {

            if (file != null) {
                for (File f : file.listFiles()) {
                    borrar(f);
                }
            }

        }
        return file.delete();

    }

    public File getFile() {
        return file;
    }

    public boolean isDirectorio() {
        return file.isDirectory() ? true : false;
    }

    public File cambiarCarpeta(File directorio, String argumento) {
        if (argumento != null && !argumento.isEmpty()) {
            File nCarp = new File(argumento);
            if (!nCarp.isAbsolute()) {
                nCarp = new File(directorio, argumento);
            }
            if (nCarp.exists() && nCarp.isDirectory()) {
                return nCarp;
            } else {
                System.out.println("No se encuentra la ruta");
                return directorio;
            }
        }
        return directorio;
    }

    public String mostrarDir(File arch) {
        if (arch.exists()) {
            if (arch.isDirectory()) {
                String names = "";
                int cont = 0;
                for (File f : arch.listFiles()) {
                    if (!f.isHidden()) {
                        names += "\n" + (f.isFile() ? "File" : "<Dir>") + " - " + f.getName();
                    }
                }
                return names;
            } else {
                return ("Porfavor, use un directorio para mostrar informacion y no un archivo");
            }
        } else {
            return "Archivo no existe";
        }
    }

    public File regresarCarpeta() {
        File anterior = file.getParentFile();
        return anterior;
    }

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(Calendar.getInstance().getTime());
    }

    public String getTime() {
        LocalTime hora = LocalTime.now();
        return String.valueOf(hora.getHour() + ":" + hora.getMinute());
    }

    public void escribirWr(File txt, String msg) throws IOException {
        BufferedWriter write;
        if (txt.exists()) {
            if (!txt.isHidden() && txt.isFile()) {
                write = new BufferedWriter(new FileWriter(txt, true));
                write.write(msg);
                write.newLine();
                write.close();
            } else {
                throw new IOException("Error, archivo es un directorio");
            }
        } else {
            throw new IOException("Error, Archivo no existe");
        }
    }

    public String leerFile(File txt) throws IOException {
        BufferedReader read;
        String msg = "";
        String linea;

        if (txt.exists()) {
            if (!txt.isHidden() && txt.isFile()) {
                read = new BufferedReader(new FileReader(txt));

                while ((linea = read.readLine()) != null) {
                    msg += linea + "\n";
                }
                read.close();
            } else {
                throw new IOException("Error, archivo es un directorio");
            }
        } else {
            throw new IOException("Error, Archivo no existe");
        }
        return msg;
    }
}
