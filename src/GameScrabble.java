import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GameScrabble extends JFrame {
    private final String[] daftarKata = {"apel", "rumah", "kertas", "pisang", "bulan"};
    private final String[] daftarClue = {
            "Buah logo terkenal",
            "Tempat tinggal",
            "Benda untuk menulis atau mencetak",
            "Buah warna kuning",
            "Benda langit yang muncul saat malam",
    };

    private int indexKataSekarang = 0;
    private int nyawa = 2;
    private int skor = 0;
    private boolean[] statusStage;
    private int soalTerjawab = 0;  // Menyimpan jumlah soal yang sudah terjawab

    private JLabel labelHuruf, labelStage;
    private RoundedTextField inputTebakan;
    private RoundedTextArea areaRiwayat;
    private JLabel labelSkor, labelNyawa;
    private RoundedButtonC tombolTebak, tombolKembali;

    public GameScrabble() {
        setTitle("Game Scrabble");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        statusStage = new boolean[daftarKata.length];

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CustomConfirmDialog dialog = new CustomConfirmDialog(
                        GameScrabble.this,
                        "Yakin ingin keluar dari permainan?",
                        "Ya, Keluar",
                        "Batal",
                        new Color(220, 78, 65, 220)
                );
                dialog.setVisible(true);
                if (dialog.isConfirmed()) {
                    dispose();
                    new MenuUtama1().setVisible(true);
                }
            }
        });

        BackgroundPanel backgroundPanel;
        try {
            backgroundPanel = new BackgroundPanel("bg_pikku4.jpg"); // Ganti dengan path gambar Anda
        } catch (IOException e) {
            e.printStackTrace();
            backgroundPanel = new BackgroundPanel();
        }
        backgroundPanel.setLayout(new GridBagLayout());

        labelHuruf = new JLabel();
        labelHuruf.setFont(new Font("Poppins", Font.BOLD, 40));
        labelHuruf.setHorizontalAlignment(SwingConstants.CENTER);

        labelStage = new JLabel("Stage: 1/" + daftarKata.length);
        labelStage.setFont(new Font("Poppins", Font.BOLD, 24));
        labelStage.setHorizontalAlignment(SwingConstants.CENTER);

        inputTebakan = new RoundedTextField(15);
        inputTebakan.setFont(new Font("Poppins", Font.PLAIN, 24));
        inputTebakan.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        inputTebakan.addActionListener(e -> prosesTebakan());

        tombolTebak = new RoundedButtonC("Tebak Kata");
        tombolTebak.setFont(new Font("Poppins", Font.BOLD, 24));
        tombolTebak.setPreferredSize(new Dimension(200, 120));
        tombolTebak.addActionListener(e -> prosesTebakan());

        tombolKembali = new RoundedButtonC("Menu Utama");
        tombolKembali.setFont(new Font("Poppins", Font.BOLD, 18));
        tombolKembali.setPreferredSize(new Dimension(150, 45));
        tombolKembali.addActionListener(ev -> {
            CustomConfirmDialog dialog = new CustomConfirmDialog(
                    GameScrabble.this,
                    "Yakin ingin keluar dari permainan?",
                    "Ya, Keluar",
                    "Batal",
                    new Color(220, 78, 65, 220)
            );
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                dispose();
                new MenuUtama1().setVisible(true);
            }
        });

        areaRiwayat = new RoundedTextArea(10, 60);
        areaRiwayat.setFont(new Font("Poppins", Font.PLAIN, 18));
        areaRiwayat.setEditable(false);
        JScrollPane scrollRiwayat = new JScrollPane(areaRiwayat);
        scrollRiwayat.setBorder(BorderFactory.createTitledBorder("Riwayat"));

        labelSkor = new JLabel("Skor: " + skor);
        labelSkor.setFont(new Font("Poppins", Font.BOLD, 26));

        labelNyawa = new JLabel("Nyawa: " + nyawa);
        labelNyawa.setFont(new Font("Poppins", Font.BOLD, 26));

        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 10));
        panelInfo.setOpaque(false);
        panelInfo.add(labelSkor);
        panelInfo.add(labelNyawa);

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.X_AXIS));
        panelInput.setOpaque(false);

        JLabel labelKetik = new JLabel("Ketik Tebakan:");
        labelKetik.setFont(new Font("Poppins", Font.PLAIN, 20));
        labelKetik.setAlignmentY(Component.CENTER_ALIGNMENT);

        inputTebakan.setAlignmentY(Component.CENTER_ALIGNMENT);
        tombolTebak.setAlignmentY(Component.CENTER_ALIGNMENT);

        panelInput.add(labelKetik);
        panelInput.add(Box.createRigidArea(new Dimension(10, 0)));
        panelInput.add(inputTebakan);
        panelInput.add(Box.createRigidArea(new Dimension(15, 0)));
        panelInput.add(tombolTebak);

        inputTebakan.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputTebakan.getPreferredSize().height));
        panelInput.setMaximumSize(new Dimension(800, 60));

        JPanel panelUtama = new RoundedPanel();
        panelUtama.setLayout(new BoxLayout(panelUtama, BoxLayout.Y_AXIS));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        panelUtama.setOpaque(false);

        labelStage.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelHuruf.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollRiwayat.setAlignmentX(Component.CENTER_ALIGNMENT);
        tombolKembali.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelUtama.add(labelStage);
        panelUtama.add(Box.createVerticalStrut(15));
        panelUtama.add(labelHuruf);
        panelUtama.add(Box.createVerticalStrut(15));
        panelUtama.add(panelInput);
        panelUtama.add(Box.createVerticalStrut(15));
        panelUtama.add(panelInfo);
        panelUtama.add(Box.createVerticalStrut(15));
        panelUtama.add(scrollRiwayat);
        panelUtama.add(Box.createVerticalStrut(20));
        panelUtama.add(tombolKembali);

        backgroundPanel.add(panelUtama, new GridBagConstraints());

        setContentPane(backgroundPanel);

        setOpacity(0f);
        setVisible(true);
        fadeIn(this);

        tampilkanKataBerikutnya();
    }

    private void fadeIn(JFrame frame) {
        Timer timer = new Timer(20, null);
        timer.addActionListener(new ActionListener() {
            float opacity = 0f;
            public void actionPerformed(ActionEvent e) {
                opacity += 0.05f;
                frame.setOpacity(Math.min(opacity, 1f));
                if (opacity >= 1f) timer.stop();
            }
        });
        timer.start();
    }

    private void tampilkanKataBerikutnya() {
        if (indexKataSekarang < daftarKata.length) {
            String kata = daftarKata[indexKataSekarang];
            String hurufAcak = acakHuruf(kata);
            labelHuruf.setText("Susun huruf ini: " + hurufAcak);
            nyawa = 2;
            labelNyawa.setText("Nyawa: " + nyawa);
            labelStage.setText("Stage: " + (indexKataSekarang + 1) + "/" + daftarKata.length);
            areaRiwayat.append("Stage " + (indexKataSekarang + 1) + "/" + daftarKata.length + "\n");
        } else {
            tampilkanLayarAkhir();
        }
    }

    private String acakHuruf(String kata) {
        List<Character> hurufList = new ArrayList<>();
        for (char c : kata.toCharArray()) {
            hurufList.add(c);
        }

        String hasilAcak = kata;
        int percobaanAcak = 0;
        while (hasilAcak.equals(kata) && percobaanAcak < 10 && kata.length() > 1) {
            Collections.shuffle(hurufList);
            StringBuilder hasil = new StringBuilder();
            for (char c : hurufList) {
                hasil.append(c);
            }
            hasilAcak = hasil.toString();
            percobaanAcak++;
        }
        return hasilAcak;
    }

    private void prosesTebakan() {
        String tebakan = inputTebakan.getText().toLowerCase().trim();
        inputTebakan.setText("");

        String kataBenar = daftarKata[indexKataSekarang];
        String clue = daftarClue[indexKataSekarang];

        if (tebakan.isEmpty()) {
            aCustomInfoDialog dialog = new aCustomInfoDialog(
                    GameScrabble.this,
                    "Masukkan kata!"
            );
            dialog.setVisible(true);
            return;
        }

        if (tebakan.equals(kataBenar)) {
            skor += 20;
            statusStage[indexKataSekarang] = true;
            soalTerjawab++;
            areaRiwayat.append("Benar: " + tebakan + "\n");
            labelSkor.setText("Skor: " + skor);
            indexKataSekarang++;
            tampilkanKataBerikutnya();
        } else {
            nyawa--;
            labelNyawa.setText("Nyawa: " + nyawa);
            areaRiwayat.append("Salah: " + tebakan + "\n");

            if (nyawa == 1) {
                areaRiwayat.append("Clue: " + clue + "\n");
            }

            if (nyawa == 0) {
                areaRiwayat.append("Nyawa habis. Kata yang benar: " + kataBenar + "\n\n");
                statusStage[indexKataSekarang] = false;
                indexKataSekarang++;
                tampilkanKataBerikutnya();
            }
        }
    }

    private void tampilkanLayarAkhir() {
        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());

        JPanel panelAkhir = new JPanel();
        panelAkhir.setLayout(new BoxLayout(panelAkhir, BoxLayout.Y_AXIS));
        panelAkhir.setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));
        panelAkhir.setOpaque(false);

        JLabel labelAkhir = new JLabel(
                "<html><center>"
                        + "<b>Permainan Selesai!</b><br><br>"
                        + "<b>Skor Akhir:</b> <span style='color:#2361d2;font-size:44px'>" + skor + "</span><br><br>"
                        + "Soal yang terjawab benar: <b>" + soalTerjawab + "</b> dari <b>" + daftarKata.length + "</b>"
                        + "</center></html>", SwingConstants.CENTER);
        labelAkhir.setFont(new Font("Poppins", Font.BOLD, 36));
        labelAkhir.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelAkhir.add(Box.createVerticalStrut(30));
        panelAkhir.add(labelAkhir);
        panelAkhir.add(Box.createVerticalStrut(36));

        JPanel panelTombol = new JPanel();
        panelTombol.setOpaque(false);
        panelTombol.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 12));

        RoundedButtonC tombolMenu = new RoundedButtonC("Menu Utama");
        tombolMenu.setFont(new Font("Poppins", Font.BOLD, 24));
        tombolMenu.setPreferredSize(new Dimension(220, 54));
        tombolMenu.addActionListener(e -> {
            dispose();
            new MenuUtama1().setVisible(true);
        });

        RoundedButtonC tombolKeluar = new RoundedButtonC("Keluar");
        tombolKeluar.setFont(new Font("Poppins", Font.BOLD, 24));
        tombolKeluar.setPreferredSize(new Dimension(140, 54));
        tombolKeluar.addActionListener(e -> {
            CustomConfirmDialog dialog = new CustomConfirmDialog(
                    GameScrabble.this,
                    "Yakin ingin keluar dari permainan?",
                    "Ya, Keluar",
                    "Batal",
                    new Color(220, 78, 65, 220)
            );
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                dispose();
                System.exit(0);
            }
        });

        panelTombol.add(tombolMenu);
        panelTombol.add(tombolKeluar);

        panelAkhir.add(panelTombol);

        JPanel panelCenter = new JPanel(new GridBagLayout());
        panelCenter.setOpaque(false);
        panelCenter.add(panelAkhir);

        add(panelCenter, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    class RoundedPanel extends JPanel {
        private final int radius = 30;

        public RoundedPanel() {
            setOpaque(false);
            setBackground(new Color(255, 255, 255, 0));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
            g2.dispose();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(800, 600);
        }
    }

    class RoundedTextField extends JTextField {
        private int radius = 20;

        public RoundedTextField(int columns) {
            super(columns);
            setOpaque(false);
            setBackground(new Color(255, 255, 255, 0));
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(255, 255, 255, 60));
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

    class RoundedTextArea extends JTextArea {
        private int radius = 20;

        public RoundedTextArea(int rows, int columns) {
            super(rows, columns);
            setOpaque(false);
            setBackground(new Color(255, 255, 255, 0));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }

    class RoundedButtonC extends JButton {
        private int radius = 20;
        private Color borderColor = Color.GRAY;
        private Color originalBackground;

        public RoundedButtonC(String text) {
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
                    setForeground(Color.WHITE);
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

    class BackgroundPanel extends JPanel {
        private BufferedImage backgroundImage;

        public BackgroundPanel() {
            setOpaque(false);
        }

        public BackgroundPanel(String imagePath) throws IOException {
            backgroundImage = ImageIO.read(new File(imagePath));
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

    class CustomConfirmDialog extends JDialog {
        private boolean confirmed = false;

        public CustomConfirmDialog(JFrame parent, String pesan, String labelYa, String labelTidak, Color yaColor) {
            super(parent, "Konfirmasi", true);
            setUndecorated(true);

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(255, 255, 255, 240));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);
                    g2.setColor(new Color(230, 230, 230, 180));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 28, 28);
                    g2.dispose();
                }
            };
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
            panel.setBackground(new Color(255, 255, 255, 0));

            JLabel label = new JLabel("<html><center>" + pesan + "</center></html>");
            label.setFont(new Font("Poppins", Font.BOLD, 22));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(label);
            panel.add(Box.createVerticalStrut(30));

            JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 22, 0));
            panelButton.setOpaque(false);

            RoundedButtonC btnYes = new RoundedButtonC(labelYa);
            btnYes.setBackground(yaColor);
            btnYes.setForeground(Color.WHITE);
            btnYes.setFont(new Font("Poppins", Font.BOLD, 20));
            btnYes.addActionListener(e -> {
                confirmed = true;
                dispose();
            });

            RoundedButtonC btnNo = new RoundedButtonC(labelTidak);
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

    class aCustomInfoDialog extends JDialog {
        public aCustomInfoDialog(JFrame parent, String pesan) {
            super(parent, "Info", true);
            setUndecorated(true);

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(255, 255, 255, 245));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);
                    g2.setColor(new Color(210, 210, 210, 160));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 28, 28);
                    g2.dispose();
                }
            };
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
            panel.setBackground(new Color(255, 255, 255, 0));

            JLabel label = new JLabel("<html><center>" + pesan + "</center></html>");
            label.setFont(new Font("Poppins", Font.BOLD, 20));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(label);
            panel.add(Box.createVerticalStrut(30));

            JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            panelButton.setOpaque(false);

            RoundedButtonC btnOK = new RoundedButtonC("OK");
            btnOK.setBackground(new Color(0, 120, 215, 230));
            btnOK.setForeground(Color.WHITE);
            btnOK.setFont(new Font("Poppins", Font.BOLD, 18));
            btnOK.setPreferredSize(new Dimension(130, 44));
            btnOK.addActionListener(e -> dispose());

            panelButton.add(btnOK);
            panel.add(panelButton);

            setContentPane(panel);
            pack();
            setLocationRelativeTo(parent);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameScrabble::new);
    }
}