/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cmd_leonardo_samuel;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author hnleo
 */
public class CMDFunciones {

    private File file = null;

    public void setFile(String dir) {
        file = new File(dir);
    }

    public boolean nuevaCarpeta() throws IOException {
        if (file.mkdir()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean crearArchivo() throws IOException {
        if (file.createNewFile()) {
            return true;
        } else {
            return false;
        }

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
    
    public File getFile(){
        return file;
    }
    
    public boolean isDirectorio(){
        return file.isDirectory() ? true : false;
    }
}
