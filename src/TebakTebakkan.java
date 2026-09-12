
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

// Custom JButton with filled rounded background and rounded border + hover effect
class RoundedButton extends JButton {
    private int radius = 20;
    private Color borderColor = Color.GRAY;
    private Color originalBackground;

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false); // Custom painting
        setFocusPainted(false);
        setOpaque(false);
        setForeground(Color.BLACK);
        setFont(new Font("Poppins", Font.BOLD, 20));
        setPreferredSize(new Dimension(200, 50));

        originalBackground = new Color(220, 220, 220, 150);
        setBackground(originalBackground);

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setBackground(new Color(10, 102, 194)); // Calmer blue
                setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
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

// Custom JTextField with rounded corners
class RoundedTextField extends JTextField {
    private int radius = 20;

    public RoundedTextField() {
        super();
        setOpaque(false); // Make it transparent
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

// Custom JTextArea with rounded corners
class RoundedTextArea extends JTextArea {
    private int radius = 20;

    public RoundedTextArea(int rows, int columns) {
        super(rows, columns);
        setOpaque(false); // Make it transparent
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

// Custom JPanel to paint background image smoothly and nicely
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
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

public class TebakTebakkan extends JFrame {
    private final String[] daftarPertanyaan = {
        "Ikan apa yang tidak bisa kena air?",
        "Apa yang bisa bertambah tapi tidak bisa berkurang?",
        "Merek motor apa yang bisa bikin ketawa?",
        "Hewan apa yang bisa bikin bingung?",
        "Es apa yang bisa bikin ketawa?",
        "Pedang apa yang bisa bikin joget?",
        "Hewan apa yang namanya dua huruf?",
        "Pocong apa yang jadi favorit ibu-ibu?",
        "Sayur apa yang suka manggil?",
        "Buah apa yang susah move on?"
    };

    private final String[] daftarJawaban = {
        "ikan goreng",
        "umur",
        "yamaha",
        "jerapah",
        "es buah",
        "pedangdut",
        "udang",
        "pocongan harga",
        "kol",
        "melon"
    };

    private final List<Integer> urutanSoal;
    private int indexSoalSekarang = 0;
    private int nyawa = 3;
    private int skor = 0;
    private int totalPercobaan = 0;

    private JLabel labelStage;
    private JLabel labelPertanyaan;
    private RoundedTextField inputJawaban;
    private RoundedTextArea areaRiwayat;
    private JLabel labelSkor, labelNyawa;
    private RoundedButton tombolTebak, tombolSelanjutnya, tombolKembali;

    public TebakTebakkan() {
        setTitle("🎮 Game Tebak-Tebakan");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                aCustomConfirmDialog dialog = new aCustomConfirmDialog(
                    TebakTebakkan.this,
                    "Yakin ingin kembali ke Menu Utama?",
                    "Ya, Kembali",
                    "Batal",
                    new Color(0, 120, 215, 230)
                );
                dialog.setVisible(true);
                if (dialog.isConfirmed()) {
                    dispose();
                    new MenuUtama1().setVisible(true);
                }
            }
        });

        urutanSoal = new ArrayList<>();
        for (int i = 0; i < daftarPertanyaan.length; i++)
            urutanSoal.add(i);
        Collections.shuffle(urutanSoal);

        initUI();
        tampilkanSoal();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        BackgroundPanel backgroundPanel = new BackgroundPanel("bg_pikku2.jpg");
        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(new BorderLayout());

        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BorderLayout(20,20));
        container.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        backgroundPanel.add(container, BorderLayout.CENTER);

        JPanel panelUtama = new JPanel();
        panelUtama.setOpaque(false);
        panelUtama.setLayout(new BoxLayout(panelUtama, BoxLayout.Y_AXIS));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        container.add(panelUtama, BorderLayout.NORTH);

        labelStage = new JLabel("", SwingConstants.CENTER);
        labelStage.setFont(new Font("Poppins", Font.PLAIN, 24));
        labelStage.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelUtama.add(labelStage);
        panelUtama.add(Box.createRigidArea(new Dimension(0, 12)));

        labelPertanyaan = new JLabel("", SwingConstants.CENTER);
        labelPertanyaan.setFont(new Font("Poppins Semibold", Font.BOLD, 32));
        labelPertanyaan.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelPertanyaan.setForeground(new Color(30, 30, 30));
        panelUtama.add(labelPertanyaan);
        panelUtama.add(Box.createRigidArea(new Dimension(0, 25)));

        inputJawaban = new RoundedTextField();
        inputJawaban.setMaximumSize(new Dimension(700, 45));
        inputJawaban.setFont(new Font("Poppins", Font.PLAIN, 22));
        inputJawaban.addActionListener(e -> prosesJawaban());
        panelUtama.add(inputJawaban);
        panelUtama.add(Box.createRigidArea(new Dimension(0, 20)));

        tombolTebak = new RoundedButton("Tebak");
        tombolTebak.setFont(new Font("Poppins Semibold", Font.BOLD, 22));
        tombolTebak.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelUtama.add(tombolTebak);
        panelUtama.add(Box.createRigidArea(new Dimension(0, 12)));
        tombolTebak.addActionListener(e -> prosesJawaban());

        tombolSelanjutnya = new RoundedButton("Selanjutnya");
        tombolSelanjutnya.setFont(new Font("Poppins", Font.PLAIN, 20));
        tombolSelanjutnya.setEnabled(false);
        tombolSelanjutnya.setAlignmentX(Component.CENTER_ALIGNMENT);
        tombolSelanjutnya.addActionListener(e -> {
            indexSoalSekarang++;
            nyawa = 3;
            areaRiwayat.setText("");
            tampilkanSoal();
        });
        panelUtama.add(tombolSelanjutnya);

        JPanel panelRiwayat = new JPanel();
        panelRiwayat.setOpaque(false);
        panelRiwayat.setLayout(new BoxLayout(panelRiwayat, BoxLayout.Y_AXIS));
        panelRiwayat.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        container.add(panelRiwayat, BorderLayout.CENTER);

        JLabel labelRiwayat = new JLabel("Riwayat");
        labelRiwayat.setFont(new Font("Poppins", Font.BOLD, 20));
        labelRiwayat.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelRiwayat.add(labelRiwayat);
        panelRiwayat.add(Box.createRigidArea(new Dimension(0,12)));

        areaRiwayat = new RoundedTextArea(12, 60);
        areaRiwayat.setEditable(false);
        areaRiwayat.setFont(new Font("Consolas", Font.PLAIN, 16));
        areaRiwayat.setLineWrap(true);
        areaRiwayat.setWrapStyleWord(true);
        JScrollPane scrollRiwayat = new JScrollPane(areaRiwayat);
        scrollRiwayat.setBorder(BorderFactory.createEmptyBorder());
        panelRiwayat.add(scrollRiwayat);

        JPanel panelBawah = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 8));
        panelBawah.setOpaque(false);
        backgroundPanel.add(panelBawah, BorderLayout.SOUTH);

        labelSkor = new JLabel("Skor: 0");
        labelSkor.setFont(new Font("Poppins", Font.BOLD, 18));
        labelNyawa = new JLabel("Nyawa: 3");
        labelNyawa.setFont(new Font("Poppins", Font.BOLD, 18));
        tombolKembali = new RoundedButton("Menu Utama");
        tombolKembali.setFont(new Font("Poppins", Font.PLAIN, 18));
        tombolKembali.setPreferredSize(new Dimension(150, 40));

        tombolKembali.addActionListener(e -> {
            aCustomConfirmDialog dialog = new aCustomConfirmDialog(
                TebakTebakkan.this,
                "Yakin ingin kembali ke Menu Utama?",
                "Ya, Kembali",
                "Batal",
                new Color(0, 120, 215, 230)
            );
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                dispose();
                new MenuUtama1().setVisible(true);
            }
        });

        panelBawah.add(labelSkor);
        panelBawah.add(labelNyawa);
        panelBawah.add(tombolKembali);
    }

    private void tampilkanSoal() {
        if (indexSoalSekarang < urutanSoal.size()) {
            int idx = urutanSoal.get(indexSoalSekarang);
            labelStage.setText("Stage " + (indexSoalSekarang + 1) + " / " + urutanSoal.size());
            labelPertanyaan.setText(daftarPertanyaan[idx]);
            inputJawaban.setText("");
            inputJawaban.setEnabled(true);
            tombolTebak.setEnabled(true);
            tombolSelanjutnya.setEnabled(false);
            nyawa = 3;
            labelNyawa.setText("Nyawa: " + nyawa);
        } else {
            tampilkanHasilAkhir();
        }
    }

    private void prosesJawaban() {
        if (indexSoalSekarang >= urutanSoal.size() || nyawa <= 0) return;

        int idx = urutanSoal.get(indexSoalSekarang);
        String jawabanBenar = daftarJawaban[idx].trim().toLowerCase();
        String jawabanUser = inputJawaban.getText().trim().toLowerCase();

        if (jawabanUser.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Masukkan jawaban terlebih dahulu.",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        totalPercobaan++;

        if (jawabanUser.equals(jawabanBenar)) {
            skor += 10;
            areaRiwayat.append("Benar! Jawaban: " + daftarJawaban[idx] + "\n");
            inputJawaban.setEnabled(false);
            tombolTebak.setEnabled(false);
            tombolSelanjutnya.setEnabled(true);
        } else {
            nyawa--;
            if (nyawa > 0) {
                areaRiwayat.append("Salah. Coba lagi. Sisa nyawa: " + nyawa + "\n");
            } else {
                areaRiwayat.append("Salah. Jawaban benar adalah: " + daftarJawaban[idx] + "\n");
                inputJawaban.setEnabled(false);
                tombolTebak.setEnabled(false);
                tombolSelanjutnya.setEnabled(true);
            }
        }

        labelSkor.setText("Skor: " + skor);
        labelNyawa.setText("Nyawa: " + nyawa);
    }

    private void tampilkanHasilAkhir() {
        getContentPane().removeAll();
        repaint();

        setLayout(new BorderLayout());

        JPanel panelAkhir = new JPanel();
        panelAkhir.setLayout(new BoxLayout(panelAkhir, BoxLayout.Y_AXIS));
        panelAkhir.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        panelAkhir.setOpaque(false);

        JLabel labelAkhir = new JLabel(
            "<html><center>"
            + "<b>Permainan Selesai!</b><br><br>"
            + "<b>Skor Akhir:</b> <span style='color:#2361d2;font-size:44px'>" + skor + "</span><br><br>"
            + "Soal yang terjawab benar: <b>" + (skor/10) + "</b> dari <b>" + daftarPertanyaan.length + "</b><br>"
            + "Total percobaan: <b>" + totalPercobaan + "</b>"
            + "</center></html>",
            SwingConstants.CENTER
        );
        labelAkhir.setFont(new Font("Poppins", Font.BOLD, 36));
        labelAkhir.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelAkhir.add(Box.createVerticalStrut(30));
        panelAkhir.add(labelAkhir);
        panelAkhir.add(Box.createVerticalStrut(36));

        JPanel panelTombol = new JPanel();
        panelTombol.setOpaque(false);
        panelTombol.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 12));

        RoundedButton tombolMenu = new RoundedButton("Menu Utama");
        tombolMenu.setFont(new Font("Poppins", Font.BOLD, 24));
        tombolMenu.setPreferredSize(new Dimension(220, 54));
        tombolMenu.addActionListener(e -> {
            dispose();
            new MenuUtama1().setVisible(true);
        });

        RoundedButton tombolMulaiLagi = new RoundedButton("Mulai Lagi");
        tombolMulaiLagi.setFont(new Font("Poppins", Font.BOLD, 24));
        tombolMulaiLagi.setPreferredSize(new Dimension(160, 54));
        tombolMulaiLagi.addActionListener(e -> {
            skor = 0;
            totalPercobaan = 0;
            indexSoalSekarang = 0;
            nyawa = 3;
            urutanSoal.clear();
            for (int i = 0; i < daftarPertanyaan.length; i++) urutanSoal.add(i);
            Collections.shuffle(urutanSoal);
            getContentPane().removeAll();
            initUI();
            tampilkanSoal();
            revalidate();
            repaint();
        });

        panelTombol.add(tombolMenu);
        panelTombol.add(tombolMulaiLagi);

        panelAkhir.add(panelTombol);

        JPanel panelCenter = new JPanel(new GridBagLayout());
        panelCenter.setOpaque(false);
        panelCenter.add(panelAkhir);

        add(panelCenter, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    class aCustomConfirmDialog extends JDialog {
        private boolean confirmed = false;

        public aCustomConfirmDialog(JFrame parent, String pesan, String labelYa, String labelTidak, Color yaColor) {
            super(parent, "Konfirmasi", true);
            setUndecorated(true);

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(255,255,255,240));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);
                    g2.setColor(new Color(230,230,230,180));
                    g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 28, 28);
                    g2.dispose();
                }
            };
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
            panel.setBackground(new Color(255,255,255,0));

            JLabel label = new JLabel("<html><center>" + pesan + "</center></html>");
            label.setFont(new Font("Poppins", Font.BOLD, 22));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(label);
            panel.add(Box.createVerticalStrut(30));

            JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 22, 0));
            panelButton.setOpaque(false);

            RoundedButton btnYes = new RoundedButton(labelYa);
            btnYes.setBackground(yaColor);
            btnYes.setForeground(Color.WHITE);
            btnYes.setFont(new Font("Poppins", Font.BOLD, 20));
            btnYes.addActionListener(e -> {
                confirmed = true;
                dispose();
            });

            RoundedButton btnNo = new RoundedButton(labelTidak);
            btnNo.setBackground(new Color(200, 200, 200, 220));
            btnNo.setFont(new Font("Poppins", Font.BOLD, 20));
            btnNo.addActionListener(e -> {
                confirmed = false;
                dispose();
            });

            panelButton.add(btnYes);
            panelButton.add(btnNo);

            panel.add(panelButton);

            setContentPane(panel);
            pack();
            setLocationRelativeTo(parent);
        }

        public boolean isConfirmed() {
            return confirmed;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TebakTebakkan app = new TebakTebakkan();
            app.setVisible(true);
        });
    }
}