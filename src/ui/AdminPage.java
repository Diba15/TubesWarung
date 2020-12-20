/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import Database.OperationDB;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Diba15
 */
public class AdminPage extends javax.swing.JFrame {

    private static final OperationDB op = new OperationDB();
    String namaUser;
    DefaultTableModel df, dfB, dfT;
    private int posSize, posX, posY;

    /**
     * Creates new form AdminPage
     *
     * @param nama
     * @param jabatan
     */
    public AdminPage(String nama, String jabatan) {
        setUndecorated(true);
        initComponents();
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
        namaUser = nama;
        //Customize JFrame
        df = (DefaultTableModel) tableKaryawan.getModel();
        dfB = (DefaultTableModel) tableBarang.getModel();
        dfT = (DefaultTableModel) tableTransaksi.getModel();
        tableKaryawan.getTableHeader().setFont(new Font("Open Sans Condensed", Font.BOLD, 12));
        tableBarang.getTableHeader().setFont(new Font("Open Sans Condensed", Font.BOLD, 12));
        tableTransaksi.getTableHeader().setFont(new Font("Open Sans Condensed", Font.BOLD, 12));
        setTitle("Lihat Barang");
        labelWelcome.setText("Selamat Datang " + nama);
        //Hide Panel 
        tkPanel.setVisible(false);
        tbPanel.setVisible(false);
        lkPanel.setVisible(false);
        ltPanel.setVisible(false);
        resetSearchK.setVisible(false);
        btnResetBarang.setVisible(false);
        idCodeField.setEditable(false);
        //Method Display Execute
        hideAddBarang();
        lihatKaryawan();
        displayBarang();
        displayTransaksi();
        //Sorting
        sortTableBarang();
        sortTableKaryawan();
        sortTableTransaksi();
        btnPanelLB.setBackground(new Color(101, 157, 189));
//        if login status is karyawan
        if (jabatan.equalsIgnoreCase("Karyawan")) {
            btnPanelLK.setVisible(false);
            btnPanelTK.setVisible(false);
        }
        //Draggable Func
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        int windowWidth = getWidth();
        int windowHeight = getHeight();
        setBounds(center.x - windowWidth / 2, center.y - windowHeight / 2, windowWidth,
            windowHeight);
    }

    public final void hideAddBarang() {
        labelEtcTB.setVisible(false);
        labelJMTB.setVisible(false);
        labelJMinTB1.setVisible(false);
        labelRasaMinTB1.setVisible(false);
        labelRasaTB.setVisible(false);
        fieldEtcTB.setVisible(false);
        fieldJMTB.setVisible(false);
        fieldJMinTB1.setVisible(false);
        fieldRasaMinTB1.setVisible(false);
        fieldRasaTB.setVisible(false);
    }

    public final void displayBarang() {
        try {
            ResultSet rsB = op.readData("SELECT * FROM barang");
            while (rsB.next()) {
                String namaBarang = rsB.getString("namaBarang");
                String jenisBarang = rsB.getString("jenisBarang");
                String jenis = rsB.getString("jenis");
                String idBarang = rsB.getString("id_barang");
                String rasa = rsB.getString("rasa");
                int hargaBarang = rsB.getInt("Harga");
                int qty = rsB.getInt("kuantitas");
                dfB.addRow(new Object[]{idBarang, namaBarang, jenisBarang});
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public final void displayTransaksi() {
        try {
            ResultSet rsT = op.readData("SELECT * FROM transaksi");
            while (rsT.next()) {
                String namaPelanggan = rsT.getString("nama");
                String kembalian = rsT.getString("kembalian");
                String idBarang = rsT.getString("id_Barang");
                String jumlahBeli = rsT.getString("jumlahBeli");
                int nominalUang = rsT.getInt("nominalUang");
                int no = rsT.getInt("no");
                String tanggal = rsT.getString("tanggalPembelian").substring(0, 16);
                dfT.addRow(new Object[]{no, namaPelanggan, idBarang, nominalUang, jumlahBeli, kembalian, tanggal});
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public final void lihatKaryawan() {
        try {
            ResultSet rs = op.readData("SELECT * FROM user WHERE level = 'karyawan'");
            int count = 1;
            while (rs.next()) {
                String nama = rs.getString("nama");
                int umur = rs.getInt("umur");
                String email = rs.getString("email");
                String noTelp = rs.getString("noTelp");
                String status = rs.getString("status");
                df.addRow(new Object[]{count, nama, umur, email, noTelp, status});
                count++;
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }

    public final void sortTableBarang() {
        int click = 1;
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableBarang.getModel());
        tableBarang.setRowSorter(sorter);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();

        int columnIndexToSort = 1;
        if (click % 2 == 0) {
            sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
        } else {
            sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));
        }
        click++;

        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }
    
    public final void sortTableKaryawan() {
        int click = 1;
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableKaryawan.getModel());
        tableKaryawan.setRowSorter(sorter);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();

        int columnIndexToSort = 1;
        if (click % 2 == 0) {
            sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
        } else {
            sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));
        }
        click++;

        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }
    
    public final void sortTableTransaksi() {
        int click = 1;
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableTransaksi.getModel());
        tableTransaksi.setRowSorter(sorter);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();

        int columnIndexToSort = 1;
        if (click % 2 == 0) {
            sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
        } else {
            sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));
        }
        click++;

        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        btnPanelLB = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        btnPanelTB = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        btnPanelLT = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        btnPanelTK = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        labelWelcome = new javax.swing.JLabel();
        btnLogout = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        btnPanelLK = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tkPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fieldNK = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        fieldUK = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        fieldEK = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        fieldNTK = new javax.swing.JTextField();
        submitTK = new javax.swing.JButton();
        fieldUsernameTK = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        fieldPasswordTK = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tbPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        fieldNTB = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        labelJMTB = new javax.swing.JLabel();
        fieldJMTB = new javax.swing.JTextField();
        labelRasaTB = new javax.swing.JLabel();
        fieldRasaTB = new javax.swing.JTextField();
        labelJMinTB1 = new javax.swing.JLabel();
        fieldJMinTB1 = new javax.swing.JTextField();
        labelRasaMinTB1 = new javax.swing.JLabel();
        fieldRasaMinTB1 = new javax.swing.JTextField();
        labelEtcTB = new javax.swing.JLabel();
        fieldEtcTB = new javax.swing.JTextField();
        ButtonTB = new javax.swing.JButton();
        makananRB = new javax.swing.JRadioButton();
        minumanRB = new javax.swing.JRadioButton();
        etcRB = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        fieldID = new javax.swing.JTextField();
        idCodeField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        fieldHarga = new javax.swing.JTextField();
        fieldQty = new javax.swing.JTextField();
        viewPanel = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        fieldCariBarang = new javax.swing.JTextField();
        btnCariBarang = new javax.swing.JButton();
        btnResetBarang = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBarang = new javax.swing.JTable();
        refreshTabelBarang = new javax.swing.JLabel();
        ltPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableTransaksi = new javax.swing.JTable();
        panelClearTransaksi = new javax.swing.JPanel();
        labelClearData = new javax.swing.JLabel();
        refreshTabelTransaksi = new javax.swing.JLabel();
        lkPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableKaryawan = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        fieldPencarianK = new javax.swing.JTextField();
        buttonSearchK = new javax.swing.JButton();
        resetSearchK = new javax.swing.JButton();
        refreshTabelKaryawan = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(101, 157, 189));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(85, 122, 149));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });

        btnPanelLB.setBackground(new java.awt.Color(255, 51, 51));
        btnPanelLB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPanelLB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPanelLBMouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Open Sans Condensed", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Lihat Barang");

        javax.swing.GroupLayout btnPanelLBLayout = new javax.swing.GroupLayout(btnPanelLB);
        btnPanelLB.setLayout(btnPanelLBLayout);
        btnPanelLBLayout.setHorizontalGroup(
            btnPanelLBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelLBLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnPanelLBLayout.setVerticalGroup(
            btnPanelLBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelLBLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnPanelTB.setBackground(new java.awt.Color(255, 51, 51));
        btnPanelTB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPanelTB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPanelTBMouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Open Sans Condensed", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Tambah Barang");

        javax.swing.GroupLayout btnPanelTBLayout = new javax.swing.GroupLayout(btnPanelTB);
        btnPanelTB.setLayout(btnPanelTBLayout);
        btnPanelTBLayout.setHorizontalGroup(
            btnPanelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelTBLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnPanelTBLayout.setVerticalGroup(
            btnPanelTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelTBLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnPanelLT.setBackground(new java.awt.Color(255, 51, 51));
        btnPanelLT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPanelLT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPanelLTMouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Open Sans Condensed", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("History Transaksi");

        javax.swing.GroupLayout btnPanelLTLayout = new javax.swing.GroupLayout(btnPanelLT);
        btnPanelLT.setLayout(btnPanelLTLayout);
        btnPanelLTLayout.setHorizontalGroup(
            btnPanelLTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelLTLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnPanelLTLayout.setVerticalGroup(
            btnPanelLTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelLTLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnPanelTK.setBackground(new java.awt.Color(255, 51, 51));
        btnPanelTK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPanelTK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPanelTKMouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Open Sans Condensed", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Tambah Karyawan");

        javax.swing.GroupLayout btnPanelTKLayout = new javax.swing.GroupLayout(btnPanelTK);
        btnPanelTK.setLayout(btnPanelTKLayout);
        btnPanelTKLayout.setHorizontalGroup(
            btnPanelTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelTKLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnPanelTKLayout.setVerticalGroup(
            btnPanelTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelTKLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        labelWelcome.setFont(new java.awt.Font("Open Sans Condensed", 0, 18)); // NOI18N
        labelWelcome.setForeground(new java.awt.Color(255, 255, 255));
        labelWelcome.setText("Selamat Datang");

        btnLogout.setBackground(new java.awt.Color(255, 51, 51));
        btnLogout.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Open Sans Condensed", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Log out");

        javax.swing.GroupLayout btnLogoutLayout = new javax.swing.GroupLayout(btnLogout);
        btnLogout.setLayout(btnLogoutLayout);
        btnLogoutLayout.setHorizontalGroup(
            btnLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLogoutLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel16)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        btnLogoutLayout.setVerticalGroup(
            btnLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLogoutLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnPanelLK.setBackground(new java.awt.Color(255, 51, 51));
        btnPanelLK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPanelLK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPanelLKMouseClicked(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Open Sans Condensed", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Lihat Karyawan");

        javax.swing.GroupLayout btnPanelLKLayout = new javax.swing.GroupLayout(btnPanelLK);
        btnPanelLK.setLayout(btnPanelLKLayout);
        btnPanelLKLayout.setHorizontalGroup(
            btnPanelLKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelLKLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnPanelLKLayout.setVerticalGroup(
            btnPanelLKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelLKLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(85, 122, 149));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel6MouseExited(evt);
            }
        });

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Open Sans Condensed Light", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("-");
        jLabel20.setToolTipText("");
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel20MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel20MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(24, 24, 24))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20)
        );

        jPanel7.setBackground(new java.awt.Color(85, 122, 149));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel7MouseExited(evt);
            }
        });

        jLabel21.setBackground(new java.awt.Color(255, 255, 255));
        jLabel21.setFont(new java.awt.Font("Open Sans Condensed", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("max");
        jLabel21.setToolTipText("");
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel21MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel21MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addGap(24, 24, 24))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel21)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnPanelLB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPanelTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPanelLT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPanelLK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPanelTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(labelWelcome)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 252, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnPanelTK, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnPanelLT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnPanelTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnPanelLB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnPanelLK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBackground(new java.awt.Color(101, 157, 189));

        tkPanel.setBackground(new java.awt.Color(101, 157, 189));

        jLabel1.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nama :");

        jLabel2.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Umur:");

        jLabel3.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("E-mail:");

        jLabel4.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("No Telp:");

        submitTK.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        submitTK.setText("Submit");
        submitTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitTKActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Username:");

        jLabel7.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Password:");

        javax.swing.GroupLayout tkPanelLayout = new javax.swing.GroupLayout(tkPanel);
        tkPanel.setLayout(tkPanelLayout);
        tkPanelLayout.setHorizontalGroup(
            tkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tkPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(tkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tkPanelLayout.createSequentialGroup()
                        .addGap(0, 831, Short.MAX_VALUE)
                        .addComponent(submitTK))
                    .addGroup(tkPanelLayout.createSequentialGroup()
                        .addGroup(tkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(tkPanelLayout.createSequentialGroup()
                                .addGroup(tkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addGap(0, 410, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(fieldPasswordTK, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fieldUsernameTK)
                            .addComponent(fieldNK)
                            .addComponent(fieldUK, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fieldEK, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fieldNTK, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30))
        );
        tkPanelLayout.setVerticalGroup(
            tkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tkPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fieldNK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(tkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldUsernameTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fieldUK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(fieldEK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(fieldNTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldPasswordTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(submitTK)
                .addContainerGap(171, Short.MAX_VALUE))
        );

        tbPanel.setBackground(new java.awt.Color(101, 157, 189));

        jLabel9.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nama:");

        jLabel5.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Jenis:");

        labelJMTB.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        labelJMTB.setForeground(new java.awt.Color(255, 255, 255));
        labelJMTB.setText("Jenis Makanan:");

        labelRasaTB.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        labelRasaTB.setForeground(new java.awt.Color(255, 255, 255));
        labelRasaTB.setText("Rasa Makanan:");

        labelJMinTB1.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        labelJMinTB1.setForeground(new java.awt.Color(255, 255, 255));
        labelJMinTB1.setText("Jenis Minuman:");

        labelRasaMinTB1.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        labelRasaMinTB1.setForeground(new java.awt.Color(255, 255, 255));
        labelRasaMinTB1.setText("Rasa Minuman:");

        labelEtcTB.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        labelEtcTB.setForeground(new java.awt.Color(255, 255, 255));
        labelEtcTB.setText("Jenis Barang:");

        ButtonTB.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        ButtonTB.setText("Submit");
        ButtonTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonTBActionPerformed(evt);
            }
        });

        makananRB.setBackground(new java.awt.Color(101, 157, 189));
        buttonGroup2.add(makananRB);
        makananRB.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        makananRB.setForeground(new java.awt.Color(255, 255, 255));
        makananRB.setText("Makanan");
        makananRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makananRBActionPerformed(evt);
            }
        });

        minumanRB.setBackground(new java.awt.Color(101, 157, 189));
        buttonGroup2.add(minumanRB);
        minumanRB.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        minumanRB.setForeground(new java.awt.Color(255, 255, 255));
        minumanRB.setText("Minuman");
        minumanRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minumanRBActionPerformed(evt);
            }
        });

        etcRB.setBackground(new java.awt.Color(101, 157, 189));
        buttonGroup2.add(etcRB);
        etcRB.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        etcRB.setForeground(new java.awt.Color(255, 255, 255));
        etcRB.setText("Etc");
        etcRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                etcRBActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("ID Barang:");

        fieldID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fieldIDMousePressed(evt);
            }
        });
        fieldID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fieldIDKeyPressed(evt);
            }
        });

        idCodeField.setText("M");
        idCodeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idCodeFieldActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Harga:");

        jLabel18.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Banyak Barang:");

        fieldHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fieldHargaKeyPressed(evt);
            }
        });

        fieldQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldQtyActionPerformed(evt);
            }
        });
        fieldQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fieldQtyKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout tbPanelLayout = new javax.swing.GroupLayout(tbPanel);
        tbPanel.setLayout(tbPanelLayout);
        tbPanelLayout.setHorizontalGroup(
            tbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tbPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(tbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tbPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(ButtonTB))
                    .addGroup(tbPanelLayout.createSequentialGroup()
                        .addGroup(tbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelJMTB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelRasaTB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelJMinTB1, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                            .addComponent(labelRasaMinTB1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelEtcTB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(tbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tbPanelLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(tbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tbPanelLayout.createSequentialGroup()
                                        .addComponent(makananRB)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(minumanRB)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(etcRB))
                                    .addComponent(fieldJMTB, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fieldRasaTB, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fieldJMinTB1, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(tbPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(tbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(fieldRasaMinTB1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tbPanelLayout.createSequentialGroup()
                                        .addComponent(idCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(fieldID, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))
                                    .addComponent(fieldNTB, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fieldEtcTB)
                                    .addComponent(fieldQty)
                                    .addComponent(fieldHarga))))))
                .addGap(30, 30, 30))
        );
        tbPanelLayout.setVerticalGroup(
            tbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tbPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(tbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(fieldID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(fieldNTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(fieldHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(fieldQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(tbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(makananRB)
                    .addComponent(minumanRB)
                    .addComponent(etcRB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelJMTB)
                    .addComponent(fieldJMTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelRasaTB)
                    .addComponent(fieldRasaTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelJMinTB1)
                    .addComponent(fieldJMinTB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelRasaMinTB1)
                    .addComponent(fieldRasaMinTB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelEtcTB)
                    .addComponent(fieldEtcTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonTB)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        viewPanel.setBackground(new java.awt.Color(101, 157, 189));

        jLabel17.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Cari ID :");

        btnCariBarang.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        btnCariBarang.setText("Cari");
        btnCariBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariBarangActionPerformed(evt);
            }
        });

        btnResetBarang.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        btnResetBarang.setText("Reset Pencarian");
        btnResetBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetBarangActionPerformed(evt);
            }
        });

        tableBarang.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        tableBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Barang", "Nama Barang", "Jenis Barang"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableBarang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tableBarang.setFocusable(false);
        tableBarang.setOpaque(false);
        tableBarang.setRowHeight(25);
        tableBarang.setSelectionBackground(new java.awt.Color(101, 157, 189));
        tableBarang.setShowVerticalLines(false);
        tableBarang.getTableHeader().setReorderingAllowed(false);
        tableBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableBarang);

        refreshTabelBarang.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        refreshTabelBarang.setForeground(new java.awt.Color(255, 255, 255));
        refreshTabelBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/refresh (2).png"))); // NOI18N
        refreshTabelBarang.setText("Refresh");
        refreshTabelBarang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshTabelBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshTabelBarangMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout viewPanelLayout = new javax.swing.GroupLayout(viewPanel);
        viewPanel.setLayout(viewPanelLayout);
        viewPanelLayout.setHorizontalGroup(
            viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewPanelLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldCariBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCariBarang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetBarang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(refreshTabelBarang))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        viewPanelLayout.setVerticalGroup(
            viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(fieldCariBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCariBarang)
                        .addComponent(btnResetBarang))
                    .addComponent(refreshTabelBarang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .addGap(13, 13, 13))
        );

        ltPanel.setBackground(new java.awt.Color(101, 157, 189));

        tableTransaksi.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        tableTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Nama Pelanggan", "ID Barang", "Nominal Uang", "Jumlah Beli", "Kembalian", "Tanggal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableTransaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tableTransaksi.setFocusable(false);
        tableTransaksi.setOpaque(false);
        tableTransaksi.setRowHeight(25);
        tableTransaksi.setSelectionBackground(new java.awt.Color(101, 157, 189));
        tableTransaksi.setShowVerticalLines(false);
        tableTransaksi.getTableHeader().setReorderingAllowed(false);
        tableTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTransaksiMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableTransaksi);

        panelClearTransaksi.setBackground(new java.awt.Color(85, 122, 149));
        panelClearTransaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelClearTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelClearTransaksiMouseClicked(evt);
            }
        });

        labelClearData.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        labelClearData.setForeground(new java.awt.Color(255, 255, 255));
        labelClearData.setText("Clear Data Transaksi");
        labelClearData.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelClearData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelClearDataMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelClearTransaksiLayout = new javax.swing.GroupLayout(panelClearTransaksi);
        panelClearTransaksi.setLayout(panelClearTransaksiLayout);
        panelClearTransaksiLayout.setHorizontalGroup(
            panelClearTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClearTransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelClearData)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelClearTransaksiLayout.setVerticalGroup(
            panelClearTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClearTransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelClearData)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        refreshTabelTransaksi.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        refreshTabelTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        refreshTabelTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/refresh (2).png"))); // NOI18N
        refreshTabelTransaksi.setText("Refresh");
        refreshTabelTransaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshTabelTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshTabelTransaksiMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout ltPanelLayout = new javax.swing.GroupLayout(ltPanel);
        ltPanel.setLayout(ltPanelLayout);
        ltPanelLayout.setHorizontalGroup(
            ltPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ltPanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(ltPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ltPanelLayout.createSequentialGroup()
                        .addComponent(panelClearTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 685, Short.MAX_VALUE)
                        .addComponent(refreshTabelTransaksi))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        ltPanelLayout.setVerticalGroup(
            ltPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ltPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ltPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelClearTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshTabelTransaksi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        lkPanel.setBackground(new java.awt.Color(101, 157, 189));

        tableKaryawan.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        tableKaryawan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Nama", "Umur", "E-mail", "No Telp", "Status Karyawan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableKaryawan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tableKaryawan.setFocusable(false);
        tableKaryawan.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tableKaryawan.setOpaque(false);
        tableKaryawan.setRowHeight(25);
        tableKaryawan.setSelectionBackground(new java.awt.Color(101, 157, 189));
        tableKaryawan.setShowVerticalLines(false);
        tableKaryawan.getTableHeader().setReorderingAllowed(false);
        tableKaryawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableKaryawanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableKaryawan);

        jLabel10.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Cari Nama :");

        fieldPencarianK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldPencarianKActionPerformed(evt);
            }
        });

        buttonSearchK.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        buttonSearchK.setText("Cari");
        buttonSearchK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSearchKActionPerformed(evt);
            }
        });

        resetSearchK.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        resetSearchK.setText("Reset Pencarian");
        resetSearchK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetSearchKActionPerformed(evt);
            }
        });

        refreshTabelKaryawan.setFont(new java.awt.Font("Open Sans Condensed", 0, 11)); // NOI18N
        refreshTabelKaryawan.setForeground(new java.awt.Color(255, 255, 255));
        refreshTabelKaryawan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/refresh (2).png"))); // NOI18N
        refreshTabelKaryawan.setText("Refresh");
        refreshTabelKaryawan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshTabelKaryawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshTabelKaryawanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout lkPanelLayout = new javax.swing.GroupLayout(lkPanel);
        lkPanel.setLayout(lkPanelLayout);
        lkPanelLayout.setHorizontalGroup(
            lkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lkPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(lkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lkPanelLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldPencarianK, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonSearchK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resetSearchK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 463, Short.MAX_VALUE)
                        .addComponent(refreshTabelKaryawan))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        lkPanelLayout.setVerticalGroup(
            lkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lkPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(resetSearchK, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(refreshTabelKaryawan))
                    .addGroup(lkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(fieldPencarianK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonSearchK)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 936, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tkPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tbPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(viewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(ltPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lkPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 462, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tkPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tbPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(viewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(ltPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lkPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void submitTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitTKActionPerformed
        String nama = fieldNK.getText();
        String username = fieldUsernameTK.getText();
        String umur = fieldUK.getText();
        String email = fieldEK.getText();
        String noTelp = fieldNTK.getText();
        String pass = fieldPasswordTK.getText();
        if (nama.trim().equals("") || username.trim().equals("") || umur.trim().equals("") || email.trim().equals("")
                || noTelp.trim().equals("") || pass.trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Isi Semua Data Dengan Benar!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            String query = "INSERT INTO user(nama,username,umur,email,noTelp,password) VALUES ('" + nama + "','" + username + "'," + Integer.parseInt(umur) + ",'" + email + "','" + noTelp + "','" + pass + "')";
            op.CUDData(query);
            JOptionPane.showMessageDialog(rootPane, "Karyawan Berhasil Ditambahkan", "Berhasil", 1);
            fieldNK.setText(null);
            fieldUsernameTK.setText(null);
            fieldUK.setText(null);
            fieldEK.setText(null);
            fieldNTK.setText(null);
            fieldPasswordTK.setText(null);
        }
    }//GEN-LAST:event_submitTKActionPerformed

    private void fieldPencarianKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldPencarianKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldPencarianKActionPerformed

    private void buttonSearchKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSearchKActionPerformed
        String cariNamaK = fieldPencarianK.getText();
        try {
            ResultSet rs = op.readData("SELECT * FROM user WHERE nama = '" + cariNamaK + "'");
            int count = 1;
            if (rs.next() == false) {
                JOptionPane.showMessageDialog(rootPane, "User tidak ditemukan", "Pemberitahuan", JOptionPane.INFORMATION_MESSAGE);
            } else {
                resetSearchK.setVisible(true);
                df.setRowCount(0);
                String nama = rs.getString("nama");
                int umur = rs.getInt("umur");
                String email = rs.getString("email");
                String noTelp = rs.getString("noTelp");
                df.addRow(new Object[]{count, nama, umur, email, noTelp});
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }//GEN-LAST:event_buttonSearchKActionPerformed

    private void resetSearchKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetSearchKActionPerformed
        df.setRowCount(0);
        resetSearchK.setVisible(false);
        try {
            ResultSet rs = op.readData("SELECT * FROM user WHERE level = 'karyawan'");
            int count = 1;
            while (rs.next()) {
                String nama = rs.getString("nama");
                int umur = rs.getInt("umur");
                String email = rs.getString("email");
                String noTelp = rs.getString("noTelp");
                df.addRow(new Object[]{count, nama, umur, email, noTelp});
                count++;
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }//GEN-LAST:event_resetSearchKActionPerformed

    private void tableKaryawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableKaryawanMouseClicked
        JTextField namaKaryawan = new JTextField();
        JTextField username = new JTextField();
        JTextField umur = new JTextField();
        JTextField email = new JTextField();
        JTextField noTelp = new JTextField();
        JTextField password = new JTextField();
        String status = null;
        Border border = new LineBorder(new Color(255, 51, 51), 1, true);
        username.setEditable(false);
        email.setEditable(false);
        username.setBorder(border);
        email.setBorder(border);
        Object[] object = {
            "Nama: ", namaKaryawan,
            "Username: ", username,
            "Umur:", umur,
            "Email:", email,
            "No Telepon:", noTelp,
            "Password:", password
        };
        int rowSelected = tableKaryawan.rowAtPoint(evt.getPoint());
        String nama = (String) tableKaryawan.getValueAt(rowSelected, 1);
        try {
            ResultSet rs = op.readData("SELECT * FROM user WHERE nama = '" + nama + "'");
            while (rs.next()) {
                namaKaryawan.setText(rs.getString("nama"));
                username.setText(rs.getString("username"));
                umur.setText(Integer.toString(rs.getInt("umur")));
                email.setText(rs.getString("email"));
                noTelp.setText(rs.getString("noTelp"));
                password.setText(rs.getString("password"));
                status = rs.getString("status");
            }
            UIManager.put("OptionPane.yesButtonText", "Update");
            if (status.equals("Aktif")) {
                UIManager.put("OptionPane.noButtonText", "Hapus");
            } else if (status.equals("Tidak Aktif")) {
                UIManager.put("OptionPane.noButtonText", "Aktifkan");
            }
            int info = JOptionPane.showConfirmDialog(rootPane, object, "Detail", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (info == JOptionPane.YES_OPTION) {
                op.CUDData("UPDATE user SET nama = '" + namaKaryawan.getText() + "', umur = " + umur.getText() + ",noTelp = '" + noTelp.getText()
                        + "',password = '" + password.getText() + "' WHERE username = '" + username.getText() + "'");
                df.setValueAt(namaKaryawan.getText(), rowSelected, 1);
                df.setValueAt(umur.getText(), rowSelected, 2);
                df.setValueAt(noTelp.getText(), rowSelected, 4);
            } else if (info == JOptionPane.NO_OPTION) {
                if (status.equals("Aktif")) {
                    op.CUDData("Update user SET status = 'Tidak Aktif' WHERE username = '" + username.getText() + "'");
                    df.setValueAt("Tidak Aktif", rowSelected, 5);
                } else if (status.equals("Tidak Aktif")) {
                    op.CUDData("Update user SET status = 'Aktif' WHERE username = '" + username.getText() + "'");
                    df.setValueAt("Aktif", rowSelected, 5);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }//GEN-LAST:event_tableKaryawanMouseClicked

    private void etcRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_etcRBActionPerformed
        labelEtcTB.setVisible(true);
        labelJMTB.setVisible(false);
        labelJMinTB1.setVisible(false);
        labelRasaMinTB1.setVisible(false);
        labelRasaTB.setVisible(false);
        fieldEtcTB.setVisible(true);
        fieldJMTB.setVisible(false);
        fieldJMinTB1.setVisible(false);
        fieldRasaMinTB1.setVisible(false);
        fieldRasaTB.setVisible(false);
        idCodeField.setText("E");
    }//GEN-LAST:event_etcRBActionPerformed

    private void minumanRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minumanRBActionPerformed
        labelEtcTB.setVisible(false);
        labelJMTB.setVisible(false);
        labelJMinTB1.setVisible(true);
        labelRasaMinTB1.setVisible(true);
        labelRasaTB.setVisible(false);
        fieldEtcTB.setVisible(false);
        fieldJMTB.setVisible(false);
        fieldJMinTB1.setVisible(true);
        fieldRasaMinTB1.setVisible(true);
        fieldRasaTB.setVisible(false);
        idCodeField.setText("Min");
    }//GEN-LAST:event_minumanRBActionPerformed

    private void makananRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makananRBActionPerformed
        labelEtcTB.setVisible(false);
        labelJMTB.setVisible(true);
        labelJMinTB1.setVisible(false);
        labelRasaMinTB1.setVisible(false);
        labelRasaTB.setVisible(true);
        fieldEtcTB.setVisible(false);
        fieldJMTB.setVisible(true);
        fieldJMinTB1.setVisible(false);
        fieldRasaMinTB1.setVisible(false);
        fieldRasaTB.setVisible(true);
        idCodeField.setText("M");
    }//GEN-LAST:event_makananRBActionPerformed

    private void btnPanelLBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelLBMouseClicked
        viewPanel.setVisible(true);
        tkPanel.setVisible(false);
        lkPanel.setVisible(false);
        tbPanel.setVisible(false);
        ltPanel.setVisible(false);
        btnPanelLB.setBackground(new Color(101, 157, 189));
        btnPanelLT.setBackground(new Color(255, 51, 51));
        btnPanelTB.setBackground(new Color(255, 51, 51));
        btnPanelTK.setBackground(new Color(255, 51, 51));
        btnPanelLK.setBackground(new Color(255, 51, 51));
        setTitle("Lihat Barang");
    }//GEN-LAST:event_btnPanelLBMouseClicked

    private void btnPanelTBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelTBMouseClicked
        viewPanel.setVisible(false);
        tkPanel.setVisible(false);
        lkPanel.setVisible(false);
        tbPanel.setVisible(true);
        ltPanel.setVisible(false);
        btnPanelTB.setBackground(new Color(101, 157, 189));
        btnPanelLT.setBackground(new Color(255, 51, 51));
        btnPanelLB.setBackground(new Color(255, 51, 51));
        btnPanelTK.setBackground(new Color(255, 51, 51));
        btnPanelLK.setBackground(new Color(255, 51, 51));
        setTitle("Tambah Barang");
    }//GEN-LAST:event_btnPanelTBMouseClicked

    private void btnPanelLTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelLTMouseClicked
        viewPanel.setVisible(false);
        tkPanel.setVisible(false);
        lkPanel.setVisible(false);
        ltPanel.setVisible(true);
        tbPanel.setVisible(false);
        btnPanelLT.setBackground(new Color(101, 157, 189));
        btnPanelLK.setBackground(new Color(255, 51, 51));
        btnPanelLB.setBackground(new Color(255, 51, 51));
        btnPanelTB.setBackground(new Color(255, 51, 51));
        btnPanelTK.setBackground(new Color(255, 51, 51));
        setTitle("Lihat Karyawan");
    }//GEN-LAST:event_btnPanelLTMouseClicked

    private void btnPanelTKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelTKMouseClicked
        viewPanel.setVisible(false);
        tkPanel.setVisible(true);
        lkPanel.setVisible(false);
        tbPanel.setVisible(false);
        ltPanel.setVisible(false);
        btnPanelTK.setBackground(new Color(101, 157, 189));
        btnPanelLT.setBackground(new Color(255, 51, 51));
        btnPanelTB.setBackground(new Color(255, 51, 51));
        btnPanelLB.setBackground(new Color(255, 51, 51));
        btnPanelLK.setBackground(new Color(255, 51, 51));
        setTitle("Tambah Karyawan");
    }//GEN-LAST:event_btnPanelTKMouseClicked

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        this.dispose();
        new GettingStarted().setVisible(true);
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void btnPanelLKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelLKMouseClicked
        viewPanel.setVisible(false);
        tkPanel.setVisible(false);
        lkPanel.setVisible(true);
        tbPanel.setVisible(false);
        ltPanel.setVisible(false);
        btnPanelLK.setBackground(new Color(101, 157, 189));
        btnPanelLT.setBackground(new Color(255, 51, 51));
        btnPanelLB.setBackground(new Color(255, 51, 51));
        btnPanelTB.setBackground(new Color(255, 51, 51));
        btnPanelTK.setBackground(new Color(255, 51, 51));
        setTitle("Lihat Karyawan");
    }//GEN-LAST:event_btnPanelLKMouseClicked

    private void panelClearTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelClearTransaksiMouseClicked
        dfT.setRowCount(0);
        op.CUDData("TRUNCATE TABLE transaksi");
    }//GEN-LAST:event_panelClearTransaksiMouseClicked

    private void labelClearDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelClearDataMouseClicked
        dfT.setRowCount(0);
        op.CUDData("TRUNCATE TABLE transaksi");
    }//GEN-LAST:event_labelClearDataMouseClicked

    private void ButtonTBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTBActionPerformed
        String jenisBarang = null;
        if (makananRB.isSelected()) {
            jenisBarang = makananRB.getText();
        } else if (minumanRB.isSelected()) {
            jenisBarang = minumanRB.getText();
        } else if (etcRB.isSelected()) {
            jenisBarang = etcRB.getText();
        }
        String idBarang = idCodeField.getText() + fieldID.getText();
        String namaBarang = fieldNTB.getText();
        String kuantitas = fieldQty.getText();
        String harga = fieldHarga.getText();
        String jenis = "-", rasa = "-";
        if (jenisBarang.equalsIgnoreCase("Makanan")) {
            jenis = fieldJMTB.getText();
            rasa = fieldRasaTB.getText();
        } else if (jenisBarang.equalsIgnoreCase("Minuman")) {
            jenis = fieldJMinTB1.getText();
            rasa = fieldRasaMinTB1.getText();
        } else {
            jenis = fieldEtcTB.getText();
        }
        if (jenisBarang == null && idBarang == null && namaBarang == null && jenis == null && kuantitas == null && harga == null) {
            JOptionPane.showMessageDialog(rootPane, "Isi Semua Data Dengan Benar!", "Peringatan!", JOptionPane.WARNING_MESSAGE);
        } else {
            op.CUDData("INSERT INTO barang(id_barang,namaBarang,jenisBarang,rasa,jenis,Harga,kuantitas,username) VALUES('"
                    + idBarang + "','" + namaBarang + "','" + jenisBarang + "','" + rasa + "','" + jenis + "','" + harga + "','" + kuantitas + "','" + namaUser + "')");
            JOptionPane.showMessageDialog(rootPane, "Barang berhasil ditambahkan!", "Pemberitahuan!", JOptionPane.INFORMATION_MESSAGE);
        }
        idCodeField.setText("M");
        fieldID.setText(null);
        fieldNTB.setText(null);
        fieldQty.setText(null);
        fieldHarga.setText(null);
        fieldJMTB.setText(null);
        fieldRasaTB.setText(null);
        fieldRasaMinTB1.setText(null);
        fieldJMinTB1.setText(null);
        fieldEtcTB.setText(null);
    }//GEN-LAST:event_ButtonTBActionPerformed

    private void idCodeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idCodeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idCodeFieldActionPerformed

    private void fieldIDMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fieldIDMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldIDMousePressed

    private void fieldIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldIDKeyPressed
        String id = fieldID.getText();
        int l = id.length();
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9') {
            if (l == 4) {
                fieldID.setEditable(false);
            } else {
                fieldID.setEditable(true);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            fieldID.setEditable(true);
        } else {
            fieldID.setEditable(false);
        }
    }//GEN-LAST:event_fieldIDKeyPressed

    private void fieldQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldQtyActionPerformed

    private void fieldHargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldHargaKeyPressed
        String harga = fieldHarga.getText();
        int l = harga.length();
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9') {
            fieldID.setEditable(true);
        } else if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            fieldID.setEditable(true);
        } else {
            fieldID.setEditable(false);
        }
    }//GEN-LAST:event_fieldHargaKeyPressed

    private void fieldQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldQtyKeyPressed
        String qty = fieldQty.getText();
        int l = qty.length();
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9') {
            fieldID.setEditable(true);
        } else if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            fieldID.setEditable(true);
        } else {
            fieldID.setEditable(false);
        }
    }//GEN-LAST:event_fieldQtyKeyPressed

    private void refreshTabelBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshTabelBarangMouseClicked
        dfB.setRowCount(0);
        displayBarang();
    }//GEN-LAST:event_refreshTabelBarangMouseClicked

    private void refreshTabelKaryawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshTabelKaryawanMouseClicked
        df.setRowCount(0);
        lihatKaryawan();
    }//GEN-LAST:event_refreshTabelKaryawanMouseClicked

    private void refreshTabelTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshTabelTransaksiMouseClicked
        dfT.setRowCount(0);
        displayTransaksi();
    }//GEN-LAST:event_refreshTabelTransaksiMouseClicked

    private void tableBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBarangMouseClicked
        JTextField idField = new JTextField();
        JTextField namaField = new JTextField();
        JTextField jenisBarangField = new JTextField();
        JTextField rasaField = new JTextField();
        JTextField jenisField = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField hargaField = new JTextField();
        JTextField qtyField = new JTextField();
        Border border = new LineBorder(new Color(255, 51, 51), 1, true);
        idField.setEditable(false);
        usernameField.setEditable(false);
        jenisBarangField.setEditable(false);
        idField.setBorder(border);
        usernameField.setBorder(border);
        jenisBarangField.setBorder(border);
        Object[] detail = {
            "ID Barang: ", idField,
            "Nama Barang: ", namaField,
            "Jenis Barang: ", jenisBarangField,
            "Rasa: ", rasaField,
            "Jenis: ", jenisField,
            "User: ", usernameField,
            "Harga: ", hargaField,
            "Quantity: ", qtyField
        };
        int rowPoint = tableBarang.rowAtPoint(evt.getPoint());
        String getID = tableBarang.getValueAt(rowPoint, 0).toString();
        ResultSet rs = op.readData("SELECT * FROM barang WHERE id_barang = '" + getID + "'");
        try {
            int count = 1;
            while (rs.next()) {
                if (rs.getString("jenisBarang").equals("Etc")) {
                    rasaField.setEditable(false);
                    rasaField.setBorder(border);
                }
                idField.setText(rs.getString("id_barang"));
                namaField.setText(rs.getString("namaBarang"));
                jenisBarangField.setText(rs.getString("jenisBarang"));
                rasaField.setText(rs.getString("rasa"));
                jenisField.setText(rs.getString("jenis"));
                usernameField.setText(rs.getString("username"));
                hargaField.setText(rs.getString("harga"));
                qtyField.setText(rs.getString("kuantitas"));
            }
            UIManager.put("OptionPane.yesButtonText", "Update");
            UIManager.put("OptionPane.noButtonText", "Hapus");
            int info = JOptionPane.showConfirmDialog(rootPane, detail, "Detail", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (info == JOptionPane.YES_OPTION) {
                op.CUDData("UPDATE barang SET namaBarang = '" + namaField.getText() + "', rasa = '" + rasaField.getText() + "',jenis = '" + jenisField.getText()
                        + "',harga = '" + hargaField.getText() + "', kuantitas = '" + qtyField.getText() + "' WHERE id_barang = '" + idField.getText() + "'");
            } else if (info == JOptionPane.NO_OPTION) {
                op.CUDData("DELETE FROM barang WHERE id_barang = '" + idField.getText() + "'");
                dfB.removeRow(rowPoint);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tableBarangMouseClicked

    private void btnCariBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariBarangActionPerformed
        String id = fieldCariBarang.getText();
        if (id == null) {
            JOptionPane.showMessageDialog(rootPane, "Masukkan ID Barang!", "Peringatan!", JOptionPane.WARNING_MESSAGE);
        } else {
            dfB.setRowCount(0);
            btnResetBarang.setVisible(true);
            try {
                ResultSet rs = op.readData("SELECT * FROM barang WHERE id_barang = '" + id + "'");
                if (rs.next()) {
                    String namaBarang = rs.getString("namaBarang");
                    String jenisBarang = rs.getString("jenisBarang");
                    dfB.addRow(new Object[]{id, namaBarang, jenisBarang});
                    fieldCariBarang.setText(null);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(rootPane, e, "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnCariBarangActionPerformed

    private void btnResetBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetBarangActionPerformed
        btnResetBarang.setVisible(false);
        dfB.setRowCount(0);
        displayBarang();
    }//GEN-LAST:event_btnResetBarangActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed

    private void tableTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTransaksiMouseClicked
        int rowPoint = tableTransaksi.rowAtPoint(evt.getPoint());
        String id = tableTransaksi.getValueAt(rowPoint, 2).toString();
        String nama = tableTransaksi.getValueAt(rowPoint, 1).toString();
        String qty = tableTransaksi.getValueAt(rowPoint, 4).toString();
        String namaBarang = null;
        int harga = 0;
        ResultSet rs = op.readData("SELECT * FROM barang WHERE id_barang = '"+id+"'");
        try {
            while (rs.next()) {
                namaBarang = rs.getString("namaBarang");
                harga = rs.getInt("harga");
            }
            JOptionPane.showMessageDialog(rootPane, "Nama: "+nama+"\nBarang yang dibeli: "+namaBarang+"\nBanyak Beli: "+qty
                    +"\nHarga Barang: "+harga,"Details",JOptionPane.INFORMATION_MESSAGE);
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "Error: "+e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tableTransaksiMouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        setState(ICONIFIED);
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseEntered
        jPanel6.setBackground(new Color(101,157,189));
    }//GEN-LAST:event_jPanel6MouseEntered

    private void jPanel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseExited
        jPanel6.setBackground(new Color(85,122,149));
    }//GEN-LAST:event_jPanel6MouseExited

    private void jLabel20MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseEntered
        jPanel6.setBackground(new Color(101,157,189));
    }//GEN-LAST:event_jLabel20MouseEntered

    private void jLabel20MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseExited
        jPanel6.setBackground(new Color(85,122,149));
    }//GEN-LAST:event_jLabel20MouseExited

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        setState(ICONIFIED);
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        if (getExtendedState() == NORMAL) {
            setExtendedState(MAXIMIZED_BOTH);
            posSize = 0;
            jLabel21.setText("max");
        } else {
            setExtendedState(NORMAL);
            posSize = 1;
            jLabel21.setText("min");
        }
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jLabel21MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseEntered
        jPanel7.setBackground(new Color(101,157,189));
    }//GEN-LAST:event_jLabel21MouseEntered

    private void jLabel21MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseExited
        jPanel7.setBackground(new Color(85,122,149));
    }//GEN-LAST:event_jLabel21MouseExited

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        if (getExtendedState() == NORMAL) {
            setExtendedState(MAXIMIZED_BOTH);
            posSize = 0;
            jLabel21.setText("max");
        } else {
            setExtendedState(NORMAL);
            posSize = 1;
            jLabel21.setText("min");
        }
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jPanel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseEntered
        jPanel7.setBackground(new Color(101,157,189));
    }//GEN-LAST:event_jPanel7MouseEntered

    private void jPanel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseExited
        jPanel7.setBackground(new Color(85,122,149));
    }//GEN-LAST:event_jPanel7MouseExited

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        posX = evt.getX();
        posY = evt.getY();
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        if (posSize == 1) {
            Rectangle rectangle = getBounds();
            setBounds(evt.getXOnScreen() - posX, evt.getYOnScreen() - posY, rectangle.width, rectangle.height);
        }
    }//GEN-LAST:event_jPanel1MouseDragged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonTB;
    private javax.swing.JButton btnCariBarang;
    private javax.swing.JPanel btnLogout;
    private javax.swing.JPanel btnPanelLB;
    private javax.swing.JPanel btnPanelLK;
    private javax.swing.JPanel btnPanelLT;
    private javax.swing.JPanel btnPanelTB;
    private javax.swing.JPanel btnPanelTK;
    private javax.swing.JButton btnResetBarang;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton buttonSearchK;
    private javax.swing.JRadioButton etcRB;
    private javax.swing.JTextField fieldCariBarang;
    private javax.swing.JTextField fieldEK;
    private javax.swing.JTextField fieldEtcTB;
    private javax.swing.JTextField fieldHarga;
    private javax.swing.JTextField fieldID;
    private javax.swing.JTextField fieldJMTB;
    private javax.swing.JTextField fieldJMinTB1;
    private javax.swing.JTextField fieldNK;
    private javax.swing.JTextField fieldNTB;
    private javax.swing.JTextField fieldNTK;
    private javax.swing.JTextField fieldPasswordTK;
    private javax.swing.JTextField fieldPencarianK;
    private javax.swing.JTextField fieldQty;
    private javax.swing.JTextField fieldRasaMinTB1;
    private javax.swing.JTextField fieldRasaTB;
    private javax.swing.JTextField fieldUK;
    private javax.swing.JTextField fieldUsernameTK;
    private javax.swing.JTextField idCodeField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelClearData;
    private javax.swing.JLabel labelEtcTB;
    private javax.swing.JLabel labelJMTB;
    private javax.swing.JLabel labelJMinTB1;
    private javax.swing.JLabel labelRasaMinTB1;
    private javax.swing.JLabel labelRasaTB;
    private javax.swing.JLabel labelWelcome;
    private javax.swing.JPanel lkPanel;
    private javax.swing.JPanel ltPanel;
    private javax.swing.JRadioButton makananRB;
    private javax.swing.JRadioButton minumanRB;
    private javax.swing.JPanel panelClearTransaksi;
    private javax.swing.JLabel refreshTabelBarang;
    private javax.swing.JLabel refreshTabelKaryawan;
    private javax.swing.JLabel refreshTabelTransaksi;
    private javax.swing.JButton resetSearchK;
    private javax.swing.JButton submitTK;
    private javax.swing.JTable tableBarang;
    private javax.swing.JTable tableKaryawan;
    private javax.swing.JTable tableTransaksi;
    private javax.swing.JPanel tbPanel;
    private javax.swing.JPanel tkPanel;
    private javax.swing.JPanel viewPanel;
    // End of variables declaration//GEN-END:variables
}
