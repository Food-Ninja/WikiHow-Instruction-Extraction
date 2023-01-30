package io;

import model.DeconstructedStepSentence;
import model.WikiHowStep;
import utils.GlobalSettings;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class ResultVisualizer {
    public void visualizeResults(ArrayList<WikiHowStep> steps, ArrayList<DeconstructedStepSentence> sentences) {
        if(!GlobalSettings.VISUALIZE_RESULTS) {
            return;
        }

        final JFrame frame = new JFrame("WikiHow Analysis Results");
        DefaultTableModel model = GlobalSettings.OVERVIEW_EXTRACTION ? createModelForOverview(steps) :
                createModelForDetails(sentences);

        JTable table = new JTable(model);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(20);
        table.setFillsViewportHeight(true);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JScrollPane scrollPanel = new JScrollPane(table);
        JButton btnExport = new JButton("Export as TSV");
        btnExport.setPreferredSize(new Dimension(250, 40));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.gridwidth = 3;
        buttonPanel.add(btnExport, constraints);

        JPanel panel= new JPanel(new BorderLayout());
        panel.add(buttonPanel, BorderLayout.PAGE_START);
        panel.add(scrollPanel, BorderLayout.CENTER);

        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 200);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);

        btnExport.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showSaveDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                TsvExporter.exportAsTsvFile(table, file);
            }
        });
    }

    private DefaultTableModel createModelForOverview(ArrayList<WikiHowStep> steps) {
        String[] columns = new String[]{"#", "Step Description", "Step Headline", "Method", "Title",
                GlobalSettings.CATEGORY_EXCLUSION ? "Subcategories" : "Categories"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for(int i = 0; i < steps.size(); i++) {
            WikiHowStep current = steps.get(i);
            StringBuilder categories = new StringBuilder();
            boolean excludeParentCategory = GlobalSettings.CATEGORY_EXCLUSION && !GlobalSettings.ONLY_CATEGORY_FILTER;
            for(int c = excludeParentCategory ? 1 : 0; c < current.getCategories().size(); c++) {
                categories.append(current.getCategories().get(c)).append(" -> ");
            }
            if(categories.length() > 0) {
                categories = new StringBuilder(categories.substring(0, categories.length() - 3));
            }
            model.addRow(new Object[]{i+1, current.getDescription(), current.getHeadline(), current.getMethod().getName(),
                    current.getMethod().getArticle().getTitle(), categories.toString()});
        }
        return model;
    }

    private DefaultTableModel createModelForDetails(ArrayList<DeconstructedStepSentence> sentences) {
        String[] columns = new String[]{"#", "Verb", "Before Preposition", "Preposition", "After Preposition", "Sentence"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for(int i = 0; i < sentences.size(); i++) {
            DeconstructedStepSentence current = sentences.get(i);
            model.addRow(new Object[]{i+1, current.getVerb(), current.getBeforePrep(), current.getPreposition(),
                    current.getAfterPrep(), current.getCompleteSentence()});
        }
        return model;
    }
}