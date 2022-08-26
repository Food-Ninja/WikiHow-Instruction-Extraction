package view;

import model.ExtractedInstruction;
import model.VerbUsageSentence;
import utils.GlobalSettings;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

public class ResultVisualizer {
    public void visualizeResults(ArrayList<ExtractedInstruction> instructions, ArrayList<VerbUsageSentence> sentences) {
        final JFrame frame = new JFrame("JTable Demo");
        DefaultTableModel model = GlobalSettings.OVERVIEW_EXTRACTION ? createModelForOverview(instructions) :
                createModelForDetails(sentences);

        JTable table = new JTable(model);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(20);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 200);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        if(GlobalSettings.VISUALIZE_RESULTS) {
            frame.setVisible(true);
        }
    }

    private DefaultTableModel createModelForOverview(ArrayList<ExtractedInstruction> instructions) {
        String[] columns = new String[]{"#", "Step Description", "Step Headline", "Method", "Title",
                GlobalSettings.CATEGORY_EXCLUSION ? "Subcategories" : "Categories"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for(int i = 0; i < instructions.size(); i++) {
            ExtractedInstruction current = instructions.get(i);
            StringBuilder categories = new StringBuilder();
            boolean excludeParentCategory = GlobalSettings.CATEGORY_EXCLUSION && !GlobalSettings.ONLY_CATEGORY_FILTER;
            for(int c = excludeParentCategory ? 1 : 0; c < current.getCategories().size(); c++) {
                categories.append(current.getCategories().get(c)).append(" -> ");
            }
            if(categories.length() > 0) {
                categories = new StringBuilder(categories.substring(0, categories.length() - 3));
            }
            model.addRow(new Object[]{i+1, current.getDescription(), current.getInstruction(), current.getMethod(),
                    current.getHowTo(), categories.toString()});
        }
        return model;
    }

    private DefaultTableModel createModelForDetails(ArrayList<VerbUsageSentence> sentences) {
        String[] columns = new String[]{"#", "Verb", "Target Object", "Preposition", "Target Location", "Sentence"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for(int i = 0; i < sentences.size(); i++) {
            VerbUsageSentence current = sentences.get(i);
            model.addRow(new Object[]{i+1, current.getVerb(), current.getTargetObject(), current.getPreposition(),
                    current.getTargetLocation(), current.getSentence()});
        }
        return model;
    }
}