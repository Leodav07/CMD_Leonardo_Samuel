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
import java.util.Date;

/**
 *
 * @author hnleo
 */
public class GestorCMD {
       private File file = null;

    public void setFile(String dir) {
        file = new File(dir);
    }

    public void crearArchivo() throws IOException {
        if (file.createNewFile()) {
            System.out.println("Archivo creado exitosamente.");
        } else {
            System.out.println("Error, no se pudo crear");
        }
    }

    public void crearFolder() throws IOException {
        if (file.mkdirs()) {
            System.out.println("Folder creado exitosamente.");
        } else {
            System.out.println("Error, no se pudo crear.");
        }
    }

    public boolean borrar(File f) throws IOException {
        return f.delete();
    }

    public boolean borrarTodo() throws IOException {
        if (file.delete()) {
            return true;
        } else {
            File archivos[] = file.listFiles();

            for (File archivo : archivos) {
                archivo.delete();
            }
            return file.delete();
        }
    }

    public void info() {
        if (file.exists()) {
            System.out.println("Si existe");
            System.out.println("\n Nombre: " + file.getName());
            System.out.println("\n Path: " + file.getPath());
            System.out.println("\n Absolute path: " + file.getAbsolutePath());
            System.out.println("\n Padre: " + file.getAbsoluteFile().getParentFile().getParent());
            System.out.println("\n Bytes: " + file.length());
            if (file.isFile()) {
                System.out.println("Es un archivo");
            } else if (file.isDirectory()) {
                System.out.println("Es un folder");
                System.out.println("Ultima modi: " + new Date(file.lastModified()));
            }
        } else {
            System.out.println("No existe");
        }
    }

    public File getFile() {
        return file;
    }

    public String mostrarDir(File arch) {
        if (arch.exists()) {
            if (arch.isDirectory()) {
                String names = "";
                int cont = 0;
                for (File f : arch.listFiles()) {
                    if (!f.isHidden()) {
                        names += (cont + 1) + " - " + (f.isFile() ? "File" : "<Dir>") + " - " + f.getName();
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

    private void tree(String dir, File f) {
        if(f.isDirectory()){
            System.out.println(dir + f.getName());
            for(File m : f.listFiles()){
                if(!m.isHidden()){
                    tree(dir + "--", f);
                }
            }
        }
    }
    
    public void tree(){
        tree("-", file);
    }
    
    public File regresarCarpeta(){
        File anterior = file.getParentFile();
        return anterior;
    }
    
    public String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
        return sdf.format(Calendar.getInstance());
    }
    
    public String getTime(){
        LocalTime hora = LocalTime.now();
        return String.valueOf(hora.getHour() + ": " + hora.getMinute());
    }
    
    public void escribirWr(File txt, String msg) throws IOException{
        BufferedWriter write;
        if(txt.exists()){
            if(!txt.isHidden() && txt.isFile()){
                write = new BufferedWriter(new FileWriter(txt, true));
                write.write(msg);
                write.newLine();
                write.close();
            }else{
                throw new IOException("Error, archivo es un directorio");
            }
        }else{
            throw new IOException("Error, Archivo no existe");
        }
    }
    
    
    public String leerFile(File txt) throws IOException{
        BufferedReader read;
        String msg = "";
        if(txt.exists()){
            if(!txt.isHidden() && txt.isFile()){
               read = new BufferedReader(new FileReader(txt));
               while(read.readLine() != null){
                 msg += read.readLine() + "\n";
               }
               read.close();
            }else{
                throw new IOException("Error, archivo es un directorio");
            }
        }else{
            throw new IOException("Error, Archivo no existe");
        }
        return msg;
    }
    
    

}
