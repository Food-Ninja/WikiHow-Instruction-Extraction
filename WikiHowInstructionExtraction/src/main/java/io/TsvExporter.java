package io;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TsvExporter {

    public static void exportAsTsvFile(JTable table, File file){
        try {
            TableModel model = table.getModel();
            if(!file.getAbsolutePath().endsWith(".tsv")) {
                file = new File(file.getAbsolutePath() + ".tsv");
            }
            FileWriter tsv = new FileWriter(file);

            for(int i = 0; i < model.getColumnCount(); i++){
                tsv.write(model.getColumnName(i) + "\t");
            }

            tsv.write("\n");

            for(int i=0; i< model.getRowCount(); i++) {
                for(int j=0; j < model.getColumnCount(); j++) {
                    tsv.write(model.getValueAt(i,j).toString()+"\t");
                }
                tsv.write("\n");
            }

            tsv.close();
            System.out.println("TSV Export finished successfully.");
        } catch(IOException ex) {
            System.out.println("TSV Export encountered the following problem:\n");
            ex.printStackTrace();
        }
    }
}