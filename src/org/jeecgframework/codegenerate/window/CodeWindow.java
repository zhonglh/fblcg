//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.jeecgframework.codegenerate.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.jeecgframework.codegenerate.generate.CodeGenerate;
import org.jeecgframework.codegenerate.pojo.CreateFileProperty;
import org.jeecgframework.codegenerate.pojo.DbConfig;

public class CodeWindow extends JFrame {
    private static final long serialVersionUID = -5324160085184088010L;
    private static String entityPackage = "test";
    private static String entityName = "TestEntity";
    private static String tableName = "t00_company";
    private static String ftlDescription = "分公司";
    private static int fieldRowNum = 1;
    private static String primaryKeyPolicy = "uuid";
    private static String sequenceCode = "";
    String[] planets = new String[]{"uuid", "identity", "sequence"};
    
    private DbConfig dbContig = null;

    public CodeWindow() {
        JPanel jp = new JPanel();
        this.setContentPane(jp);
        jp.setLayout(new GridLayout(14, 2));
        JLabel infolbl = new JLabel("提示:");
        final JLabel showlbl = new JLabel();
        JLabel packagebl = new JLabel("包名（小写）：");
        final JTextField packagefld = new JTextField();
        JLabel entitylbl = new JLabel("实体类名（首字母大写）：");
        final JTextField entityfld = new JTextField();
        JLabel tablejbl = new JLabel("表名：");
        final JTextField tablefld = new JTextField(20);
        JLabel tablekeyjbl = new JLabel("主键生成策略：");
        final JComboBox tablekeyfld = new JComboBox(this.planets);
        JLabel sequence_lb = new JLabel("主键SEQUENCE：(oracle序列名)");
        final JTextField sequence_fld = new JTextField(20);
        JLabel titlelbl = new JLabel("功能描述：");
        final JTextField titlefld = new JTextField();
        JLabel fieldRowNumlbl = new JLabel("行字段数目：");
        JTextField fieldRowNumfld = new JTextField();
        fieldRowNumfld.setText(fieldRowNum + "");
        ButtonGroup bg = new ButtonGroup();
        final JRadioButton jsp = new JRadioButton("Table风格(form)");
        jsp.setSelected(true);
        final JRadioButton jsp_row = new JRadioButton("Div风格(form)");
        bg.add(jsp);
        bg.add(jsp_row);
        final JCheckBox actionButton = new JCheckBox("Action");
        actionButton.setSelected(true);
        final JCheckBox jspButton = new JCheckBox("Jsp");
        jspButton.setSelected(true);
        final JCheckBox serviceIButton = new JCheckBox("ServiceI");
        serviceIButton.setSelected(true);
        final JCheckBox serviceImplButton = new JCheckBox("ServiceImpl");
        serviceImplButton.setSelected(true);
        JCheckBox pageButton = new JCheckBox("Page");
        pageButton.setSelected(true);
        final JCheckBox entityButton = new JCheckBox("Entity");
        entityButton.setSelected(true);
        jp.add(infolbl);
        jp.add(showlbl);
        jp.add(packagebl);
        jp.add(packagefld);
        jp.add(entitylbl);
        jp.add(entityfld);
        jp.add(tablejbl);
        jp.add(tablefld);
        jp.add(tablekeyjbl);
        jp.add(tablekeyfld);
        jp.add(sequence_lb);
        jp.add(sequence_fld);
        jp.add(titlelbl);
        jp.add(titlefld);
        jp.add(fieldRowNumlbl);
        jp.add(fieldRowNumfld);
        jp.add(actionButton);
        jp.add(jspButton);
        jp.add(serviceIButton);
        jp.add(serviceImplButton);
        jp.add(pageButton);
        jp.add(entityButton);
        jp.add(jsp);
        jp.add(jsp_row);
        JButton confirmbtn = new JButton("生成");
        confirmbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!"".equals(packagefld.getText())) {
                    CodeWindow.entityPackage = packagefld.getText();
                    if(!"".equals(entityfld.getText())) {
                        CodeWindow.entityName = entityfld.getText();
                        if(!"".equals(titlefld.getText())) {
                            CodeWindow.ftlDescription = titlefld.getText();
                            if(!"".equals(tablefld.getText())) {
                                CodeWindow.tableName = tablefld.getText();
                                CodeWindow.primaryKeyPolicy = (String)tablekeyfld.getSelectedItem();
                                if(CodeWindow.primaryKeyPolicy.equals("sequence")) {
                                    if("".equals(sequence_fld.getText())) {
                                        showlbl.setForeground(Color.red);
                                        showlbl.setText("主键生成策略为sequence时，序列号不能为空！");
                                        return;
                                    }

                                    CodeWindow.sequenceCode = sequence_fld.getText();
                                }

                                CreateFileProperty createFileProperty = new CreateFileProperty();
                                if(jsp.isSelected()) {
                                    createFileProperty.setJspMode("01");
                                }

                                if(jsp_row.isSelected()) {
                                    createFileProperty.setJspMode("02");
                                }

                                if(actionButton.isSelected()) {
                                    createFileProperty.setActionFlag(true);
                                }

                                if(jspButton.isSelected()) {
                                    createFileProperty.setJspFlag(true);
                                }

                                if(serviceIButton.isSelected()) {
                                    createFileProperty.setServiceIFlag(true);
                                }

                                if(serviceImplButton.isSelected()) {
                                    createFileProperty.setServiceImplFlag(true);
                                }

                                if(entityButton.isSelected()) {
                                    createFileProperty.setEntityFlag(true);
                                }

                                try {
                                   
                                        (new CodeGenerate(dbContig,CodeWindow.entityPackage, CodeWindow.entityName, CodeWindow.tableName, CodeWindow.ftlDescription, createFileProperty, CodeWindow.fieldRowNum, CodeWindow.primaryKeyPolicy, CodeWindow.sequenceCode)).generateToFile();
                                        showlbl.setForeground(Color.red);
                                        showlbl.setText("成功生成增删改查->功能：" + CodeWindow.ftlDescription);
                                
                                } catch (Exception var4) {
                                    showlbl.setForeground(Color.red);
                                    showlbl.setText(var4.getMessage());
                                }

                            } else {
                                showlbl.setForeground(Color.red);
                                showlbl.setText("表名不能为空！");
                            }
                        } else {
                            showlbl.setForeground(Color.red);
                            showlbl.setText("描述不能为空！");
                        }
                    } else {
                        showlbl.setForeground(Color.red);
                        showlbl.setText("实体类名不能为空！");
                    }
                } else {
                    showlbl.setForeground(Color.red);
                    showlbl.setText("包名不能为空！");
                }
            }
        });
        JButton extbtn = new JButton("退出");
        extbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CodeWindow.this.dispose();
                System.exit(0);
            }
        });
        jp.add(confirmbtn);
        jp.add(extbtn);
        this.setTitle("JEECG代码生成器[单表模型]");
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
        this.setSize(new Dimension(600, 400));
        this.setResizable(false);
        this.setLocationRelativeTo(this.getOwner());
    }

    public static void main(String[] args) {
        try {
            (new CodeWindow()).pack();
        } catch (Exception var2) {
            System.out.println(var2.getMessage());
        }

    }
}
