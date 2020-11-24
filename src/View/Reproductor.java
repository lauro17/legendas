package View;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

public class Reproductor extends javax.swing.JFrame {

    private final EmbeddedMediaPlayerComponent player;
    private final ArrayList<File> list;
    private boolean band = true;
    private int playing = 0;
    int codigoLegenda = 0;
    int repetir = 1;
    int repetirCount = 1;

    ///-----------------------------------------------------------///
    private JDialogMensagem jDialogMensagem;
    String status = "novo";
    String statusVideo = "pause";
    Boolean statusIncio = true;
    Boolean statusFim = true;
    Boolean statusLista = false;
    Boolean statusFerramentas = false;

    ///-----------------------------------------------------------///
    private final DefaultListModel model;

//    private boolean confirme = false;
//    private String selectValue;
//    private int selectIndex;
    ///-----------------------------------------------------------///
    /**
     * /**
     * Creates new form Reproductor
     *
     * @param vlc
     */
    public Reproductor(String vlc) {
        //Como alterar o ícone default do java JFrame Swing?
        URL url = this.getClass().getResource("/Imagens32/Deflaut.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(iconeTitulo);
        initComponents();
        jPanel1.setVisible(false);
        jPanel3.setVisible(false);

        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlc);
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

        player = new EmbeddedMediaPlayerComponent();
        panelReprodutor.add(player);
        player.setSize(panelReprodutor.getSize());
        player.setVisible(true);

        list = new ArrayList<>();

        ///-----------------------------------------------------------///
        model = new DefaultListModel();
        jList1.setModel(model);
        jList1.setSelectedIndex(0);
        ///-----------------------------------------------------------///
        //Listener para acompanhar a reprodução no jSlideProgresso
        player.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void lengthChanged(MediaPlayer mediaPlayer, long newLength) {
                lbDuracao.setText(formatTime(newLength));
            }

            @Override
            public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
                lbTime.setText(formatTime(newTime));
                if (statusIncio) {
                    jFInicio.setText(formatTime(newTime));
                }
                if (statusFim) {
                    jFFim.setText(formatTime(newTime));
                }
            }

            @Override
            public void positionChanged(MediaPlayer mp, float pos) {
                if (band) {
                    int value = Math.min(100, Math.round(pos * 100.0f));
                    slideProgresso.setValue(value);

                }
            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {
                switch (repetir) {
                    case 1:
                        if (repetirCount <= 1) {
                            //enquanto for meno ou igual ao ultimo item da lista
                            if (playing < list.size() - 1) {
                                playing++;
                                jList1.setSelectedIndex(playing);
                                play();
                                btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pasue.png")));
                            } else {
                                player.getMediaPlayer().stop();
                                slideProgresso.setValue(0);
                                slideProgresso.setEnabled(false);
                                lbTime.setText(formatTime(player.getMediaPlayer().getTime()));
                                lbDuracao.setText(formatTime(player.getMediaPlayer().getTime()));
                                btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/play.png")));
                            }
                        }
                        break;
                    case 2:
                        if (repetirCount < 2) {
                            repetir();
                            play();
                            btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pasue.png")));
                            repetirCount++;
                        } else {
                            repetir();
                            btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/play.png")));
                        }
                        break;
                    case 3:
                        if (repetirCount < 3) {
                            repetir();
                            play();
                            btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pasue.png")));
                            repetirCount++;
                        } else {
                            repetir();
                            btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/play.png")));
                        }
                        break;

                    default:
                        repetirCount = 1;
                        break;
                }
            }

        });

    }

    private void repetir() {
        player.getMediaPlayer().stop();
        slideProgresso.setValue(0);
        slideProgresso.setEnabled(false);
        lbTime.setText(formatTime(player.getMediaPlayer().getTime()));
        lbDuracao.setText(formatTime(player.getMediaPlayer().getTime()));
    }

    private void play() {

        if (!list.isEmpty()) {
            String reprod = list.get(playing).getAbsolutePath();
            player.getMediaPlayer().playMedia(reprod);
            slideVolume.setValue(player.getMediaPlayer().getVolume());
            slideProgresso.setEnabled(true);
            setTitle("Legendador - " + list.get(playing).getName());
            menuSnap.setEnabled(true);
        }

    }

    private void Habilitar(boolean op) {
        btnPlay.setEnabled(op);
        btnStop.setEnabled(op);
        btnPrev.setEnabled(op);
        btnProx.setEnabled(op);
        menuPause.setEnabled(op);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuRemover = new javax.swing.JMenuItem();
        jPopupMenuList = new javax.swing.JPopupMenu();
        jMenuList = new javax.swing.JMenuItem();
        panelReprodutor = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnPlay = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnProx = new javax.swing.JButton();
        btnList = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnLegenda = new javax.swing.JButton();
        btnList2 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnMute = new javax.swing.JToggleButton();
        slideVolume = new javax.swing.JSlider();
        jToolBar2 = new javax.swing.JToolBar();
        lbTime = new javax.swing.JLabel();
        slideProgresso = new javax.swing.JSlider();
        lbDuracao = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jFInicio = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTLegenda = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jFFim = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        menuOpen = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jMenu3 = new javax.swing.JMenu();
        menuSnap = new javax.swing.JMenuItem();
        menuPause = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();

        jMenuRemover.setText("Remover");
        jMenuRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuRemoverActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuRemover);

        jMenuList.setText("Remover");
        jMenuList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuListActionPerformed(evt);
            }
        });
        jPopupMenuList.add(jMenuList);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Legendador");
        setMinimumSize(new java.awt.Dimension(500, 400));

        panelReprodutor.setMinimumSize(new java.awt.Dimension(100, 100));
        panelReprodutor.setNextFocusableComponent(this);
        panelReprodutor.setPreferredSize(new java.awt.Dimension(400, 300));
        panelReprodutor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelReprodutorMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelReprodutorLayout = new javax.swing.GroupLayout(panelReprodutor);
        panelReprodutor.setLayout(panelReprodutorLayout);
        panelReprodutorLayout.setHorizontalGroup(
            panelReprodutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 275, Short.MAX_VALUE)
        );
        panelReprodutorLayout.setVerticalGroup(
            panelReprodutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnPlay.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/play.png"))); // NOI18N
        btnPlay.setToolTipText("Play");
        btnPlay.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPlay.setEnabled(false);
        btnPlay.setMaximumSize(new java.awt.Dimension(50, 33));
        btnPlay.setMinimumSize(new java.awt.Dimension(50, 33));
        btnPlay.setPreferredSize(new java.awt.Dimension(50, 33));
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPlay);

        btnStop.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/stop.png"))); // NOI18N
        btnStop.setToolTipText("Stop");
        btnStop.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStop.setEnabled(false);
        btnStop.setFocusable(false);
        btnStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStop.setMaximumSize(new java.awt.Dimension(50, 33));
        btnStop.setMinimumSize(new java.awt.Dimension(50, 33));
        btnStop.setPreferredSize(new java.awt.Dimension(50, 33));
        btnStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });
        jToolBar1.add(btnStop);

        btnPrev.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/prev.png"))); // NOI18N
        btnPrev.setToolTipText("Anterior");
        btnPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrev.setEnabled(false);
        btnPrev.setFocusable(false);
        btnPrev.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPrev.setMaximumSize(new java.awt.Dimension(50, 33));
        btnPrev.setMinimumSize(new java.awt.Dimension(50, 33));
        btnPrev.setPreferredSize(new java.awt.Dimension(50, 33));
        btnPrev.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPrev);

        btnProx.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnProx.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/next.png"))); // NOI18N
        btnProx.setToolTipText("Proximo");
        btnProx.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProx.setEnabled(false);
        btnProx.setFocusable(false);
        btnProx.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProx.setMaximumSize(new java.awt.Dimension(50, 33));
        btnProx.setMinimumSize(new java.awt.Dimension(50, 33));
        btnProx.setPreferredSize(new java.awt.Dimension(50, 33));
        btnProx.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProxActionPerformed(evt);
            }
        });
        jToolBar1.add(btnProx);

        btnList.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/align-justify.png"))); // NOI18N
        btnList.setToolTipText("Playlist");
        btnList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnList.setFocusable(false);
        btnList.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnList.setMaximumSize(new java.awt.Dimension(50, 33));
        btnList.setMinimumSize(new java.awt.Dimension(50, 33));
        btnList.setPreferredSize(new java.awt.Dimension(50, 33));
        btnList.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListActionPerformed(evt);
            }
        });
        jToolBar1.add(btnList);
        jToolBar1.add(jSeparator1);

        btnLegenda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnLegenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/legendas.png"))); // NOI18N
        btnLegenda.setToolTipText("Legenda");
        btnLegenda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLegenda.setFocusable(false);
        btnLegenda.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLegenda.setMaximumSize(new java.awt.Dimension(50, 33));
        btnLegenda.setMinimumSize(new java.awt.Dimension(50, 33));
        btnLegenda.setPreferredSize(new java.awt.Dimension(50, 33));
        btnLegenda.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLegenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLegendaActionPerformed(evt);
            }
        });
        jToolBar1.add(btnLegenda);

        btnList2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnList2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/loop_1.png"))); // NOI18N
        btnList2.setToolTipText("Repetir");
        btnList2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnList2.setFocusable(false);
        btnList2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnList2.setMaximumSize(new java.awt.Dimension(50, 33));
        btnList2.setMinimumSize(new java.awt.Dimension(50, 33));
        btnList2.setPreferredSize(new java.awt.Dimension(50, 33));
        btnList2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnList2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnList2ActionPerformed(evt);
            }
        });
        jToolBar1.add(btnList2);
        jToolBar1.add(jSeparator2);

        btnMute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/muteOn.png"))); // NOI18N
        btnMute.setText("100%");
        btnMute.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMute.setFocusable(false);
        btnMute.setMaximumSize(new java.awt.Dimension(100, 35));
        btnMute.setMinimumSize(new java.awt.Dimension(100, 35));
        btnMute.setPreferredSize(new java.awt.Dimension(100, 35));
        btnMute.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                btnMuteStateChanged(evt);
            }
        });
        btnMute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMuteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnMute);

        slideVolume.setPaintTicks(true);
        slideVolume.setValue(100);
        slideVolume.setMaximumSize(new java.awt.Dimension(150, 33));
        slideVolume.setMinimumSize(new java.awt.Dimension(150, 33));
        slideVolume.setPreferredSize(new java.awt.Dimension(150, 33));
        slideVolume.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slideVolumeStateChanged(evt);
            }
        });
        jToolBar1.add(slideVolume);

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        lbTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTime.setText("00:00");
        lbTime.setMaximumSize(new java.awt.Dimension(50, 33));
        lbTime.setMinimumSize(new java.awt.Dimension(50, 33));
        lbTime.setPreferredSize(new java.awt.Dimension(50, 33));
        jToolBar2.add(lbTime);

        slideProgresso.setPaintTicks(true);
        slideProgresso.setValue(0);
        slideProgresso.setEnabled(false);
        slideProgresso.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slideProgressoStateChanged(evt);
            }
        });
        slideProgresso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                slideProgressoMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                slideProgressoMouseReleased(evt);
            }
        });
        jToolBar2.add(slideProgresso);

        lbDuracao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDuracao.setText("00:00");
        lbDuracao.setMaximumSize(new java.awt.Dimension(50, 33));
        lbDuracao.setMinimumSize(new java.awt.Dimension(50, 33));
        lbDuracao.setPreferredSize(new java.awt.Dimension(50, 33));
        jToolBar2.add(lbDuracao);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Inicio", "Texto", "Fim"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(10);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(20);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(20);
        }

        jPanel2.setOpaque(false);

        jFInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFInicioKeyPressed(evt);
            }
        });

        jLabel2.setText("Inicio");

        jTLegenda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTLegendaKeyPressed(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens32/inicio32x32.png"))); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel5.setText("Legenda");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens32/fim32x32.png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        jFFim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFFimKeyPressed(evt);
            }
        });

        jLabel3.setText("Fim");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("-");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTLegenda)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jFInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel7))
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jFFim, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(0, 0, 0))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel6, jLabel7});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jFFim, jFInicio});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jFFim, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTLegenda, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel7});

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jList1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList1MouseReleased(evt);
            }
        });
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addGap(19, 19, 19))
        );

        jMenuBar1.setDoubleBuffered(true);

        jMenu5.setText("Video");

        menuOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        menuOpen.setText("Abrir Arquivo");
        menuOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOpenActionPerformed(evt);
            }
        });
        jMenu5.add(menuOpen);

        jMenuBar1.add(jMenu5);

        jMenu6.setText("Legenda");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Nova Legenda");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem3);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Exportar");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem5);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Importar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem1);

        jMenuBar1.add(jMenu6);

        jMenu7.setText("View");

        jCheckBoxMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        jCheckBoxMenuItem1.setText("Ferramentas de Legenda");
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        jMenu7.add(jCheckBoxMenuItem1);

        jCheckBoxMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jCheckBoxMenuItem2.setText("Lista");
        jCheckBoxMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem2ActionPerformed(evt);
            }
        });
        jMenu7.add(jCheckBoxMenuItem2);

        jMenuBar1.add(jMenu7);

        jMenu3.setText("Controle");

        menuSnap.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        menuSnap.setText("Capturar");
        menuSnap.setEnabled(false);
        menuSnap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSnapActionPerformed(evt);
            }
        });
        jMenu3.add(menuSnap);

        menuPause.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SPACE, 0));
        menuPause.setText("Pausar / Continuar");
        menuPause.setEnabled(false);
        menuPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPauseActionPerformed(evt);
            }
        });
        jMenu3.add(menuPause);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Sobre");
        jMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu4MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu4);

        jMenu8.setText("Sair");
        jMenu8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu8ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu8);

        setJMenuBar(jMenuBar1);
        jMenuBar1.getAccessibleContext().setAccessibleParent(this);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelReprodutor, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelReprodutor, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOpenActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Arquivos de video...");
        String[] ext = {"mp4", "mpeg", "avi", "flv", "webm", "mkv", "rmvb"};
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de Videos", ext);
        fileChooser.setFileFilter(filter);
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            list.addAll(Arrays.asList(files));
            //--------adiciona--------
            jList1.setModel(model);
            model.clear();
            list.stream().forEach((f) -> {
                model.addElement(f.getAbsolutePath());
            });
            System.out.println("list = " + list);
            jList1.setSelectedIndex(0);
            //  --------
            Habilitar(true);
            setTitle("Legendador - " + list.get(playing).getName());
        }

    }//GEN-LAST:event_menuOpenActionPerformed

    private void menuSnapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSnapActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione caminho do VLC...");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int op = fileChooser.showSaveDialog(null);

        if (op == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
//            String newPath = file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".png";
            String newPath = file.getAbsolutePath() + ".png";

            if (player.getMediaPlayer().saveSnapshot(new File(newPath))) {
                JOptionPane.showMessageDialog(null, "Capturado em " + newPath);
            }

        }


    }//GEN-LAST:event_menuSnapActionPerformed

    private void slideVolumeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slideVolumeStateChanged
        // TODO add your handling code here:
        player.getMediaPlayer().setVolume(slideVolume.getValue());
        btnMute.setText(slideVolume.getValue() + "%");

    }//GEN-LAST:event_slideVolumeStateChanged

    private void slideProgressoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slideProgressoStateChanged
        // TODO add your handling code here:
        if (!band) {
            float np = slideProgresso.getValue() / 100f;
            player.getMediaPlayer().setPosition(np);

            lbTime.setText(formatTime(player.getMediaPlayer().getTime()));
            if (statusIncio) {
                jFInicio.setText(formatTime(player.getMediaPlayer().getTime()));
            }
            if (statusFim) {
                jFFim.setText(formatTime(player.getMediaPlayer().getTime()));
            }
        }
    }//GEN-LAST:event_slideProgressoStateChanged

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        try {
            if (statusVideo.equals("pause")) {

                switch ("" + player.getMediaPlayer().getMediaPlayerState()) {
                    //se o vido ainda não foi caregado
                    case "libvlc_NothingSpecial":
                        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pasue.png")));
                        btnList2.setEnabled(false);
                        statusVideo = "play";
                        play();
                        btnPlay.setFocusable(false);
                        break;
                    //se for a primeira vez que o video e rodado
                    case "libvlc_Playing":
                        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/play.png")));
                        btnList2.setEnabled(false);
                        statusVideo = "pause";
                        player.getMediaPlayer().setPause(player.getMediaPlayer().isPlaying());
                        break;
                    //se ele estiver pausado
                    case "libvlc_Paused":
                        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pasue.png")));
                        btnList2.setEnabled(true);
                        statusVideo = "play";
                        player.getMediaPlayer().play();
                        break;
                    //se esiver com stop
                    case "libvlc_Stopped":
                        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pasue.png")));
                        btnList2.setEnabled(true);
                        statusVideo = "play";
                        play();
                        btnPlay.setFocusable(false);
                    default:
//                        código se variável diferente de todos os valores;
                }

            } else if (statusVideo.equals("play")) {
                btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/play.png")));
                statusVideo = "pause";
                player.getMediaPlayer().setPause(player.getMediaPlayer().isPlaying());
            }
        } catch (Exception e) {
            jDialogMensagem = new JDialogMensagem(null, true);
            jDialogMensagem.showMessage("Aviso", "Error " + e, "AVISO", "PADRAO");
            jDialogMensagem.setVisible(true);
        }

    }//GEN-LAST:event_btnPlayActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        player.getMediaPlayer().stop();
        slideProgresso.setValue(0);
        slideProgresso.setEnabled(false);
        lbTime.setText(formatTime(player.getMediaPlayer().getTime()));
        lbDuracao.setText(formatTime(player.getMediaPlayer().getTime()));
        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/play.png")));
        btnList2.setEnabled(true);
//        JOptionPane.showMessageDialog(null, "stado " + player.getMediaPlayer().getMediaPlayerState());

    }//GEN-LAST:event_btnStopActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        if (playing > 0) {
            playing--;
            jList1.setSelectedIndex(playing);
        } else {
            playing = list.size() - 1;
            jList1.setSelectedIndex(list.size() - 1);
        }
        play();
        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pasue.png")));

    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnProxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProxActionPerformed
        if (playing < list.size() - 1) {
            playing++;
            jList1.setSelectedIndex(playing);
        } else {
            playing = 0;
            jList1.setSelectedIndex(0);
        }
        play();
        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pasue.png")));

    }//GEN-LAST:event_btnProxActionPerformed

    private void slideProgressoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slideProgressoMousePressed
        // TODO add your handling code here:
        band = false;
    }//GEN-LAST:event_slideProgressoMousePressed

    private void slideProgressoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slideProgressoMouseReleased
        // TODO add your handling code here:
        band = true;

    }//GEN-LAST:event_slideProgressoMouseReleased

    private void btnMuteStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_btnMuteStateChanged
        // TODO add your handling code here:


    }//GEN-LAST:event_btnMuteStateChanged

    private void btnMuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMuteActionPerformed
        // TODO add your handling code here:

        Icon ico;// = new ImageIcon(this.getClass().getResource("/img/muteOn"));

        if (btnMute.isSelected()) {
            ico = new ImageIcon(this.getClass().getResource("/img/muteOff.png"));
        } else {
            ico = new ImageIcon(this.getClass().getResource("/img/muteOn.png"));
        }

        player.getMediaPlayer().mute(btnMute.isSelected());
        btnMute.setIcon(ico);

    }//GEN-LAST:event_btnMuteActionPerformed

    private void btnListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListActionPerformed
        if (statusLista.equals(false)) {
            statusLista = true;
            jPanel3.setVisible(true);
            panelReprodutor.setVisible(false);
            jCheckBoxMenuItem2.setState(true);
        } else {
            statusLista = false;
            jPanel3.setVisible(false);
            panelReprodutor.setVisible(true);
            jCheckBoxMenuItem2.setState(false);
        }
    }//GEN-LAST:event_btnListActionPerformed

    private void jMenu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu4MouseClicked
        // TODO add your handling code here:
        Sobre sb = new Sobre(this, true);
        sb.setVisible(true);
    }//GEN-LAST:event_jMenu4MouseClicked

    private void menuPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPauseActionPerformed
        // TODO add your handling code here:
        player.getMediaPlayer().setPause(player.getMediaPlayer().isPlaying());
    }//GEN-LAST:event_menuPauseActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.isPopupTrigger()) {
            int linha = jTable1.getSelectedRow() + 1;
            if (linha > 0) {
                jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }

        jTLegenda.requestFocus();
        //pega os dados para alterar
        try {
            int linha = jTable1.getSelectedRow();
            if (linha >= 0) {
                codigoLegenda = Integer.parseInt(jTable1.getValueAt(linha, 0).toString());
                this.jFInicio.setText("" + jTable1.getValueAt(linha, 1));
                this.jTLegenda.setText("" + jTable1.getValueAt(linha, 2));
                this.jFFim.setText("" + jTable1.getValueAt(linha, 3));
                status = "alterar";
            }
        } catch (Exception e) {
            jDialogMensagem = new JDialogMensagem(null, true);
            jDialogMensagem.showMessage("Aviso", "Error " + e, "AVISO", "PADRAO");
            jDialogMensagem.setVisible(true);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTLegendaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLegendaKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            addItem();
        }

        //mostro como subtitulo
//        player.getMediaPlayer().setSubTitleFile(jTLegenda.getText());
    }//GEN-LAST:event_jTLegendaKeyPressed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        try {
            if (statusIncio) {
                jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens32/inicio32x32OFF.png")));
                statusIncio = false;

            } else if (statusIncio == false) {
                jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens32/inicio32x32.png")));
                statusIncio = true;

            }
        } catch (Exception e) {
            jDialogMensagem = new JDialogMensagem(null, true);
            jDialogMensagem.showMessage("Aviso", "Error " + e, "AVISO", "PADRAO");
            jDialogMensagem.setVisible(true);
        }
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        try {
            if (statusFim) {
                jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens32/fim32x32OFF.png")));
                statusFim = false;
//                em.pause();
//                cmdPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens24/botao-play.png")));
            } else if (statusFim == false) {
                jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens32/fim32x32.png")));
                statusFim = true;
            }
        } catch (Exception e) {
            jDialogMensagem = new JDialogMensagem(null, true);
            jDialogMensagem.showMessage("Aviso", "Error " + e, "AVISO", "PADRAO");
            jDialogMensagem.setVisible(true);
        }
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jMenuRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuRemoverActionPerformed
        // remover linha
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        if (jTable1.getSelectedRow() >= 0) {
            dtm.removeRow(jTable1.getSelectedRow());
            jTable1.setModel(dtm);

            status = "novo";
            jFInicio.setText("");
            jFFim.setText("");
            jTLegenda.setText("");
        }
    }//GEN-LAST:event_jMenuRemoverActionPerformed

    private void jFInicioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFInicioKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            jFFim.requestFocus();
        }
    }//GEN-LAST:event_jFInicioKeyPressed

    private void jFFimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFFimKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            jTLegenda.requestFocus();
        }
    }//GEN-LAST:event_jFFimKeyPressed

    private void panelReprodutorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelReprodutorMouseClicked
        jDialogMensagem = new JDialogMensagem(null, true);
        jDialogMensagem.showMessage("Aviso", "O campo de legenda não pode ser vazio", "AVISO", "PADRAO");
        jDialogMensagem.setVisible(true);
        if (statusVideo.equals("pause")) {
            btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pasue.png")));
            statusVideo = "play";
//            play();
//            String reprod = list.get(playing).getAbsolutePath();
//            player.getMediaPlayer().playMedia(reprod);
//            player.getMediaPlayer().play();

        } else if (statusVideo.equals("play")) {
            btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/play.png")));
            statusVideo = "pause";
            player.getMediaPlayer().setPause(player.getMediaPlayer().isPlaying());
        }

    }//GEN-LAST:event_panelReprodutorMouseClicked

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // Limpa e adiciona a primeira legenda
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setNumRows(0);
        status = "novo";
        modelo.addRow(new Object[]{jTable1.getRowCount() + 1, "00:00:00,000", "Primeira Legenda", "00:00:00,000"});
        jPanel1.setVisible(true);
        jCheckBoxMenuItem1.setState(true);

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // exporta a tabela
        //String filePath = "C:\\Users\\luizl\\Documents\\teste.srt";
        //File file = new File(filePath);
        JFileChooser file = new JFileChooser();
        file.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int filearquivo = file.showSaveDialog(null);
        File arquivo = file.getSelectedFile();
        if (filearquivo == 1) {
            // JtextFieldLocal.setText("");
        } else {
            try {
                FileWriter fw = new FileWriter(arquivo + ".srt");
                BufferedWriter bw = new BufferedWriter(fw);
                //faz um loop em todas as linhas da jTable1
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    if (i > 0) {
                        bw.write("\n");
                    }
                    //escreve o conteudo da celula no buffer     
                    bw.write("" + jTable1.getValueAt(i, 0) + "\n");
                    bw.write("" + jTable1.getValueAt(i, 1) + " --> ");
                    bw.write("" + jTable1.getValueAt(i, 3) + "\n");
                    bw.write("" + jTable1.getValueAt(i, 2) + "\n");
                }
                bw.close();
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Arquivo de legenda...");
        String[] ext = {"srt", "txt"};
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de legenda", ext);
        fileChooser.setFileFilter(filter);
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                File arquivo = fileChooser.getSelectedFile();
                FileReader fr = new FileReader(arquivo);
                BufferedReader br = new BufferedReader(fr);

                DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
                modelo.setNumRows(0);
                Object[] lines = br.lines().toArray();
                int count = 1;
                System.out.printf("lines " + lines.length);

                String codigo = "";
                String inicio = "";
                String conteudo = "";
                String fim = "";
                for (int i = 0; i < lines.length; i++) {
                    String[] row = lines[i].toString().split("\n");
                    if (row[0].equals("")) {
                    } else {
                        switch (count) {
                            case 1:
                                //ystem.out.printf("\n===========inicio=============\n");
                                //System.out.printf(lines[i].toString() + " ");
                                codigo = (lines[i].toString());
                                count++;
                                break;
                            case 2:
                                String[] time = row[0].split(" ");
                                //System.out.printf("inicio " + time[0] + " ");
                                inicio = time[0];
                                //System.out.printf("fim " + time[2] + " ");
                                fim = time[2];
                                count++;
                                break;
                            case 3:
                                //System.out.printf(lines[i].toString() + " ");
                                conteudo = lines[i].toString();
                                count++;
                                //System.out.printf("\n===========fim=============\n");
                                modelo.addRow(new Object[]{codigo, inicio, conteudo, fim});
                                count = 1;
                                break;
                            default:
                                break;
                        }

                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
        System.out.println("State: " + jCheckBoxMenuItem1.getState());
        if (jCheckBoxMenuItem1.getState()) {
            statusFerramentas = true;
            jPanel1.setVisible(true);
        } else {
            statusFerramentas = false;
            jPanel1.setVisible(false);
        }
    }//GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

    private void jCheckBoxMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem2ActionPerformed
        System.out.println("State: " + jCheckBoxMenuItem2.getState());
        if (jCheckBoxMenuItem2.getState()) {
            statusLista = true;
            jPanel3.setVisible(true);
            panelReprodutor.setVisible(false);
        } else {
            statusLista = false;
            jPanel3.setVisible(false);
            panelReprodutor.setVisible(true);
        }
    }//GEN-LAST:event_jCheckBoxMenuItem2ActionPerformed

    private void jMenuListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuListActionPerformed
        if (model.getSize() > 0) {
            model.removeElementAt(jList1.getSelectedIndex());
        }

    }//GEN-LAST:event_jMenuListActionPerformed

    private void jMenu8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu8ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenu8ActionPerformed

    private void btnLegendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLegendaActionPerformed
        System.out.println("State: " + jCheckBoxMenuItem1.getState());
        if (statusFerramentas.equals(false)) {
            statusFerramentas = true;
            jPanel1.setVisible(true);
            jCheckBoxMenuItem1.setState(true);
        } else {
            statusFerramentas = false;
            jPanel1.setVisible(false);
            jCheckBoxMenuItem1.setState(false);
        }


    }//GEN-LAST:event_btnLegendaActionPerformed

    private void btnList2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnList2ActionPerformed
        if (repetir < 3) {
            repetir++;
            repetirCount = 1;
        } else {
            repetir = 1;
        }
        //btnList2.setText("" + repetir);
        switch (repetir) {
            case 1:
                btnList2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/loop_1.png")));
                break;
            case 2:
                btnList2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/loop_2.png")));
                break;
            case 3:
                btnList2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/loop_3.png")));
                break;
            default:
                btnList2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/loop_1.png")));
                break;
        }
    }//GEN-LAST:event_btnList2ActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jList1ValueChanged

    private void jList1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseReleased
        if (evt.isPopupTrigger()) {
            int linha = jList1.getSelectedIndex();
            if (linha >= 0) {
                jPopupMenuList.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }//GEN-LAST:event_jList1MouseReleased

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        if (evt.getClickCount() == 2) {
            if (jList1.getSelectedIndex() > -1) {
                //se for a primeira vez que incia o video

                switch ("" + player.getMediaPlayer().getMediaPlayerState()) {
                    //se o vido ainda não foi caregado
                    case "libvlc_NothingSpecial":
                        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pasue.png")));
                        statusVideo = "play";
                        play();
                        btnPlay.setFocusable(false);
                        break;
                    case "libvlc_Paused":
                        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pasue.png")));
                        statusVideo = "play";
                        player.getMediaPlayer().play();
                        break;
                    default:
                    //código se variável diferente de todos os valores;
                }
            }
        }
    }//GEN-LAST:event_jList1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLegenda;
    private javax.swing.JButton btnList;
    private javax.swing.JButton btnList2;
    private javax.swing.JToggleButton btnMute;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnProx;
    private javax.swing.JButton btnStop;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JTextField jFFim;
    private javax.swing.JTextField jFInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuList;
    private javax.swing.JMenuItem jMenuRemover;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenuList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JTextField jTLegenda;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lbDuracao;
    private javax.swing.JLabel lbTime;
    private javax.swing.JMenuItem menuOpen;
    private javax.swing.JMenuItem menuPause;
    private javax.swing.JMenuItem menuSnap;
    private javax.swing.JPanel panelReprodutor;
    private javax.swing.JSlider slideProgresso;
    private javax.swing.JSlider slideVolume;
    // End of variables declaration//GEN-END:variables

    public void addItem() {
        if (jTLegenda.getText().isEmpty()) {
            jDialogMensagem = new JDialogMensagem(null, true);
            jDialogMensagem.showMessage("Aviso", "O campo de legenda não pode ser vazio", "AVISO", "PADRAO");
            jDialogMensagem.setVisible(true);
            jTLegenda.requestFocus();
        } else {
            if (status.equals("novo")) {
                DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
                modelo.addRow(new Object[]{jTable1.getRowCount() + 1, jFInicio.getText(), jTLegenda.getText(), jFFim.getText()});
                jFInicio.setText(jFFim.getText());
            }
            if (status.equals("alterar")) {
                jTable1.setValueAt(jFInicio.getText(), jTable1.getSelectedRow(), 1);
                jTable1.setValueAt(jTLegenda.getText(), jTable1.getSelectedRow(), 2);
                jTable1.setValueAt(jFFim.getText(), jTable1.getSelectedRow(), 3);
                jFInicio.setText("");
                jFFim.setText("");
                status = "novo";
            }

            jTLegenda.setText("");
            jTLegenda.requestFocus();

        }
    }

    public static String formatTime(long value) {
        value /= 1000;
        int hours = (int) value / 3600;
        int remainder = (int) value - hours * 3600;
        int minutes = remainder / 60;
        remainder = remainder - minutes * 60;
        int seconds = remainder;
        return String.format("%d:%02d:%02d", hours, minutes, seconds);
    }
}
