import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.HashSet;
import java.util.Set;


class RRoundedButton extends JButton {
    private int radius = 20;
    private Color borderColor = Color.GRAY;
    private Color originalBackground;

    public RRoundedButton(String text) {
        super(text);
        setContentAreaFilled(false); 
        setFocusPainted(false);
        setOpaque(false);
        setForeground(Color.BLACK);
        setFont(new Font("Poppins", Font.BOLD, 20));
        setPreferredSize(new Dimension(200, 50));

        originalBackground = new Color(220, 220, 220, 150);
        setBackground(originalBackground);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(10, 102, 194)); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(originalBackground);
                setForeground(Color.BLACK);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

        g2.dispose();
    }
}

class RRoundedTextField extends JTextField {
    private int radius = 20;

    public RRoundedTextField() {
        super();
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground() != null ? getBackground() : Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        g2.dispose();
    }
}

class RRoundedTextArea extends JTextArea {
    private int radius = 20;

    public RRoundedTextArea(int rows, int columns) {
        super(rows, columns);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground() != null ? getBackground() : Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        g2.dispose();
    }
}

class BBackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BBackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            g2d.dispose();
        }
    }
}

public class GameTebakKata extends JFrame {
    private String kataRahasia;
    private char[] bantuanHuruf;
    private int hurufTerbuka = 0;
    private int percobaan = 0;
    private String kategori;
    private int currentStage = 0;
 
    private JLabel labelPertanyaan;
    private JPanel panelHuruf;
    private RRoundedTextField inputTebakan;
    private RRoundedTextArea areaRiwayat;
    private JLabel labelStatus;
    private RRoundedButton tombolTebak;
    private RRoundedButton tombolKembali;
    private JLabel labelProgress; 

    private Set<Integer> soalSudahDipakai = new HashSet<>();

    private BBackgroundPanel BbackgroundPanel;
    private BufferedImage backgroundImage;

    private static final String[][] DATA_HEWAN = {
            {"Hewan melata tanpa kaki", "ular"},
            {"Hewan ternak penghasil susu", "sapi"},
            {"Hewan amfibi kecil yang suka melompat", "katak"},
            {"Hewan buas belang yang hidup di hutan", "harimau"},
            {"Raja hutan", "singa"},
            {"Hewan besar dengan belalai", "gajah"},
            {"Hewan tinggi dengan leher panjang", "jerapah"},
            {"Hewan kecil penghisap darah", "nyamuk"},
            {"Serangga berbisa dengan banyak kaki", "kelabang"},
            {"Hewan malam yang bisa terbang dan tidur terbalik", "kelelawar"},
            {"Hewan lucu bertelinga panjang, suka wortel", "kelinci"}
    };

    private static final String[][] DATA_BUAH = {
            {"Buah merah sering dibuat jus", "apel"},
            {"Buah tropis berduri tajam", "durian"},
            {"Buah kecil ungu, sering dikeringkan", "anggur"},
            {"Buah oranye dengan banyak air", "jeruk"},
            {"Buah manis dengan biji besar, warna kuning", "mangga"},
            {"Buah dengan kulit berduri, warna merah", "rambutan"},
            {"Buah berbentuk bintang saat dipotong", "belimbing"},
            {"Buah besar berkulit hijau dengan daging merah", "semangka"},
            {"Buah bermahkota dengan kulit bersisik", "nanas"},
            {"Buah berwarna oranye dengan banyak biji hitam", "pepaya"}
    };

    public GameTebakKata(String kategori) {
        this.kategori = kategori.toLowerCase();
        setTitle("🎮 Game Tebak Kata - " + kategori.toUpperCase());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setUndecorated(true); 
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        try {
            backgroundImage = ImageIO.read(new File("bg_pikku3.jpg")); 
        } catch (Exception e) {
            System.err.println("Background image not found or cannot be loaded.");
        }

        BbackgroundPanel = new BBackgroundPanel("bg_pikku3.jpg");
        setContentPane(BbackgroundPanel);

        initGame();
        setVisible(true);
    }

    private int totalBenar = 0; 
    private int totalPercobaan = 0; 

    private void initGame() {
    if (currentStage >= 10) {
        tampilkanAkhirGame();
        return;
    }

        String[] soal = getPertanyaanDanJawabanAcak();
        kataRahasia = soal[1].toLowerCase();
        bantuanHuruf = new char[kataRahasia.length()];
        for (int i = 0; i < bantuanHuruf.length; i++) {
            bantuanHuruf[i] = '_';
        }
        hurufTerbuka = 0;
        percobaan = 0;

        currentStage++;

        BbackgroundPanel.removeAll();
        BbackgroundPanel.repaint();

        Font poppinsTitle = loadPoppinsFont(40f);
        Font poppinsLabel = loadPoppinsFont(18f);
        Font poppinsSmall = loadPoppinsFont(16f);
        Font poppinsLarge = loadPoppinsFont(28f);
        Font poppinsExtraLarge = loadPoppinsFont(55f);

        labelPertanyaan = new JLabel(soal[0]);
        labelPertanyaan.setFont(poppinsTitle);
        labelPertanyaan.setHorizontalAlignment(SwingConstants.CENTER);
        labelPertanyaan.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        labelPertanyaan.setForeground(Color.BLACK);

        labelProgress = new JLabel("Stage: " + currentStage + " dari 10");
        labelProgress.setFont(poppinsLabel);
        labelProgress.setHorizontalAlignment(SwingConstants.CENTER);
        labelProgress.setForeground(Color.BLACK);
        labelProgress.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));

        JPanel panelLabelAtas = new JPanel(new BorderLayout());
        panelLabelAtas.setOpaque(false);
        panelLabelAtas.add(labelPertanyaan, BorderLayout.NORTH);
        panelLabelAtas.add(labelProgress, BorderLayout.SOUTH);

        panelHuruf = new JPanel(new GridLayout(1, kataRahasia.length(), 5, 5));
        panelHuruf.setOpaque(false);
        updatePanelHuruf(poppinsExtraLarge);

        inputTebakan = new RRoundedTextField();
        inputTebakan.setFont(poppinsLarge);
        inputTebakan.setPreferredSize(new Dimension(400, 40));
        inputTebakan.addActionListener(e -> prosesTebakan());

        tombolTebak = new RRoundedButton("Tebak");
        tombolTebak.setFont(poppinsSmall);
        tombolTebak.addActionListener(e -> prosesTebakan());

        tombolKembali = new RRoundedButton("Menu Utama");
        tombolKembali.setFont(poppinsSmall);
        tombolKembali.addActionListener(e -> kembaliKeMenu());

        JPanel panelInput = new JPanel();
        panelInput.setOpaque(false);
        panelInput.setBorder(new EmptyBorder(10, 0, 5, 0)); 
        JLabel labelTebakan = new JLabel("Tebakan:");
        labelTebakan.setFont(poppinsSmall);
        labelTebakan.setForeground(Color.BLACK);
        inputTebakan.setFont(poppinsSmall);
        panelInput.add(labelTebakan);
        panelInput.add(inputTebakan);
        panelInput.add(tombolTebak);

        areaRiwayat = new RRoundedTextArea(5, 40);
        areaRiwayat.setFont(poppinsLabel);
        areaRiwayat.setEditable(false);
        areaRiwayat.setLineWrap(true);
        areaRiwayat.setWrapStyleWord(true);
        areaRiwayat.setBackground(new Color(255, 255, 255, 230));
        areaRiwayat.setForeground(Color.BLACK);
        areaRiwayat.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JScrollPane scrollRiwayat = new JScrollPane(areaRiwayat);
        scrollRiwayat.setOpaque(false);
        scrollRiwayat.getViewport().setOpaque(false);
        scrollRiwayat.setBorder(null);

        JPanel panelRiwayatWrapper = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                int arc = 20;
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

                g2.setColor(new Color(0, 0, 0, 30));
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
            }
        };
        panelRiwayatWrapper.setOpaque(false);
        panelRiwayatWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelRiwayatWrapper.add(scrollRiwayat, BorderLayout.CENTER);

        labelStatus = new JLabel("Silakan menebak dengan benar...");
        labelStatus.setHorizontalAlignment(SwingConstants.CENTER);
        labelStatus.setForeground(Color.BLACK);
        labelStatus.setBorder(new EmptyBorder(5, 10, 5, 10)); 
        labelStatus.setFont(poppinsSmall);

        JPanel panelTombol = new JPanel();
        panelTombol.setOpaque(false);
        panelTombol.setBorder(new EmptyBorder(5, 0, 5, 0)); 
        panelTombol.add(tombolKembali);

        JPanel panelBawahGabungan = new JPanel(new BorderLayout());
        panelBawahGabungan.setOpaque(false);
        panelBawahGabungan.add(labelStatus, BorderLayout.NORTH);
        panelBawahGabungan.add(panelTombol, BorderLayout.SOUTH);

        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.setOpaque(false);
        panelAtas.add(panelLabelAtas, BorderLayout.NORTH);
        panelAtas.add(panelHuruf, BorderLayout.CENTER);
        panelAtas.add(panelInput, BorderLayout.SOUTH);

        JPanel panelKontenAtas = new JPanel(new BorderLayout());
        panelKontenAtas.setOpaque(false);
        panelKontenAtas.add(panelAtas, BorderLayout.NORTH);
        panelKontenAtas.add(panelBawahGabungan, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelKontenAtas, panelRiwayatWrapper);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerSize(3);
        splitPane.setEnabled(false);
        splitPane.setOpaque(false);
        ((JComponent) splitPane.getComponent(0)).setOpaque(false);
        ((JComponent) splitPane.getComponent(1)).setOpaque(false);

        BbackgroundPanel.setLayout(new BorderLayout());
        BbackgroundPanel.add(splitPane, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private Font loadPoppinsFont(float size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("Poppins-Bold.ttf")).deriveFont(Font.BOLD, size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            return font;
        } catch (Exception e) {
            return new Font("SansSerif", Font.BOLD, (int) size);
        }
    }

    private String[] getPertanyaanDanJawabanAcak() {
        String[][] data = kategori.equals("buah") ? DATA_BUAH : DATA_HEWAN;
        if (soalSudahDipakai.size() >= Math.min(10, data.length)) {
            return new String[]{"Soal sudah habis!", "habis"};
        }
        int index;
        do {
            index = (int) (Math.random() * data.length);
        } while (soalSudahDipakai.contains(index));
        soalSudahDipakai.add(index);
        return data[index];
    }

    private void updatePanelHuruf(Font font) {
        panelHuruf.removeAll();
        for (char c : bantuanHuruf) {
            JLabel label = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            label.setFont(font);
            label.setPreferredSize(new Dimension(100, 100));
            label.setOpaque(true);
            label.setBackground(new Color(255, 255, 255, 200));
            label.setForeground(Color.BLACK);
            label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            panelHuruf.add(label);
        }
        panelHuruf.revalidate();
        panelHuruf.repaint();
    }

private void tampilkanAkhirGame() {
    BbackgroundPanel.removeAll();
    BbackgroundPanel.repaint();
    setLayout(new BorderLayout());
    
    JPanel panelHasil = new JPanel();
    panelHasil.setOpaque(false);
    panelHasil.setLayout(new BoxLayout(panelHasil, BoxLayout.Y_AXIS));
    panelHasil.setBorder(BorderFactory.createEmptyBorder(90, 30, 90, 30));
    
    JLabel labelSelamat = new JLabel("SELAMAT!", SwingConstants.CENTER);
    labelSelamat.setFont(loadPoppinsFont(54f));
    labelSelamat.setAlignmentX(Component.CENTER_ALIGNMENT);
    labelSelamat.setForeground(new Color(49, 120, 49));
    
    JLabel labelSkor = new JLabel(
        "<html><center>Total Skor Kamu:<br><span style='font-size:60px;color:#2361d2;'>" + totalBenar*10 + "</span></center></html>",
        SwingConstants.CENTER);
    labelSkor.setFont(loadPoppinsFont(40f));
    labelSkor.setAlignmentX(Component.CENTER_ALIGNMENT);
    labelSkor.setForeground(new Color(38, 61, 139));
    
    JLabel labelDetail = new JLabel(
        "<html><center>"
        + "Benar: <b>" + totalBenar + "</b> dari 10 stage<br>"
        + "Total Percobaan: <b>" + totalPercobaan + "</b></center></html>",
        SwingConstants.CENTER);
    labelDetail.setFont(loadPoppinsFont(27f));
    labelDetail.setAlignmentX(Component.CENTER_ALIGNMENT);
    labelDetail.setForeground(Color.DARK_GRAY);

    panelHasil.add(labelSelamat);
    panelHasil.add(Box.createRigidArea(new Dimension(0, 36)));
    panelHasil.add(labelSkor);
    panelHasil.add(Box.createRigidArea(new Dimension(0, 24)));
    panelHasil.add(labelDetail);
    panelHasil.add(Box.createRigidArea(new Dimension(0, 50)));

    JPanel panelTombol = new JPanel();
    panelTombol.setOpaque(false);

    RRoundedButton tombolMenu = new RRoundedButton("Kembali ke Menu Utama");
    tombolMenu.setFont(loadPoppinsFont(26f));
    tombolMenu.setPreferredSize(new Dimension(380, 54));
    tombolMenu.addActionListener(e -> {
        dispose();
        new MenuUtama1().setVisible(true);
    });

    RRoundedButton tombolKeluar = new RRoundedButton("Keluar");
    tombolKeluar.setFont(loadPoppinsFont(24f));
    tombolKeluar.setPreferredSize(new Dimension(140, 54));
    tombolKeluar.addActionListener(e -> System.exit(0));

    panelTombol.add(tombolMenu);
    panelTombol.add(Box.createRigidArea(new Dimension(18,0)));
    panelTombol.add(tombolKeluar);

    // Masukkan semua ke frame
    BbackgroundPanel.setLayout(new BorderLayout());
    BbackgroundPanel.add(panelHasil, BorderLayout.CENTER);
    BbackgroundPanel.add(panelTombol, BorderLayout.SOUTH);

    revalidate();
    repaint();
}

    private void prosesTebakan() {
        String tebakan = inputTebakan.getText().toLowerCase().trim();
        inputTebakan.setText("");

        if (tebakan.isEmpty()) {
            CustomInfoDialog dialog = new CustomInfoDialog(this, "Masukkan tebakan terlebih dahulu.");
            dialog.setVisible(true);
            return;
        }

        if (tebakan.length() != kataRahasia.length()) {
        CustomInfoDialog dialog = new CustomInfoDialog(this, "Kata harus terdiri dari " + kataRahasia.length() + " huruf.");
        dialog.setVisible(true);
        return;
        }

        percobaan++;

        if (tebakan.equals(kataRahasia)) {
            for (int i = 0; i < bantuanHuruf.length; i++) {
                bantuanHuruf[i] = kataRahasia.charAt(i);
            }
            updatePanelHuruf(loadPoppinsFont(55f));
            labelStatus.setText("Benar! Kata: " + kataRahasia + " dalam " + percobaan + " percobaan.");
            areaRiwayat.append("Tebakan benar: " + kataRahasia + " (" + percobaan + " percobaan)\n");
            totalBenar++; 
            totalPercobaan += percobaan; 
            selesaiGame();
        } else {
            if (hurufTerbuka < kataRahasia.length()) {
                bantuanHuruf[hurufTerbuka] = kataRahasia.charAt(hurufTerbuka);
                hurufTerbuka++;
            }
            updatePanelHuruf(loadPoppinsFont(55f));
            areaRiwayat.append("Salah: " + tebakan + "\n");
            labelStatus.setText("Salah! Coba lagi... Percobaan ke-" + percobaan);
        }
    }

    private void kembaliKeMenu() {
        int pilihan = JOptionPane.showConfirmDialog(
                this,
                "Yakin ingin kembali ke Menu Utama?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (pilihan == JOptionPane.YES_OPTION) {
            dispose();
            new MenuUtama1().setVisible(true);
        }
    }

    private void selesaiGame() {
    CustomNextStageDialog dialog = new CustomNextStageDialog(this);
    dialog.setVisible(true);
    if (dialog.isLanjut()) {
        if (currentStage < 10) {
            initGame();
        } else {
            tampilkanAkhirGame(); 
        }
    } else {
        tampilkanAkhirGame();
    }
}


class CustomNextStageDialog extends JDialog {
    private boolean lanjut = false;

    public CustomNextStageDialog(JFrame parent) {
        super(parent, "Lanjut Stage?", true);
        setUndecorated(true);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255,255,255,240));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(new Color(210,210,210,150));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
                g2.dispose();
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
        panel.setBackground(new Color(255,255,255,0));

        JLabel label = new JLabel("<html><center>Kamu sudah menyelesaikan stage ini!<br>Mau lanjut ke stage berikutnya?</center></html>");
        label.setFont(new Font("Poppins", Font.BOLD, 22));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(30));

        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 22, 0));
        panelButton.setOpaque(false);

        RRoundedButton btnLanjut = new RRoundedButton("Lanjut");
        btnLanjut.setBackground(new Color(0, 120, 215, 230));
        btnLanjut.setForeground(Color.WHITE);
        btnLanjut.setFont(new Font("Poppins", Font.BOLD, 20));
        btnLanjut.addActionListener(e -> {
            lanjut = true;
            dispose();
        });

        RRoundedButton btnAkhir = new RRoundedButton("Lihat Skor Akhir");
        btnAkhir.setBackground(new Color(220, 78, 65, 220));
        btnAkhir.setForeground(Color.WHITE);
        btnAkhir.setFont(new Font("Poppins", Font.BOLD, 20));
        btnAkhir.addActionListener(e -> {
            lanjut = false;
            dispose();
        });

        panelButton.add(btnLanjut);
        panelButton.add(btnAkhir);

        panel.add(panelButton);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isLanjut() {
        return lanjut;
    }
}

class CustomInfoDialog extends JDialog {
    public CustomInfoDialog(JFrame parent, String message) {
        super(parent, "Info", true);
        setUndecorated(true);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255,255,255,240));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);
                g2.setColor(new Color(210,210,210,110));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 28, 28);
                g2.dispose();
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
        panel.setBackground(new Color(255,255,255,0));

        JLabel label = new JLabel("<html><center>" + message + "</center></html>");
        label.setFont(new Font("Poppins", Font.BOLD, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(24));

        JPanel panelButton = new JPanel();
        panelButton.setOpaque(false);

        RRoundedButton btnOK = new RRoundedButton("OK");
        btnOK.setFont(new Font("Poppins", Font.BOLD, 18));
        btnOK.setBackground(new Color(0, 120, 215, 230));
        btnOK.setForeground(Color.WHITE);
        btnOK.addActionListener(e -> dispose());

        panelButton.add(btnOK);

        panel.add(panelButton);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(parent);
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameTebakKata("hewan"));
    }
}


