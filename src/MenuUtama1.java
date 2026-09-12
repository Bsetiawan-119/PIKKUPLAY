import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

class aRoundedButton extends JButton {
    private int radius = 20;
    private Color borderColor = Color.GRAY;
    private Color originalBackground;
    private Color backgroundBeforeHover;

    public aRoundedButton(String text) {
        super(text);

        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);

        setForeground(Color.BLACK);
        setFont(new Font("Poppins", Font.BOLD, 20));

        setPreferredSize(new Dimension(350, 70));
        setMaximumSize(new Dimension(350, 70));

        originalBackground = new Color(220, 220, 220, 190);
        setBackground(originalBackground);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                backgroundBeforeHover = getBackground();

                setBackground(new Color(0, 120, 215));
                setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                setBackground(
                    backgroundBeforeHover != null
                        ? backgroundBeforeHover
                        : originalBackground
                );

                setForeground(Color.BLACK);
            }
        });
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(getBackground());

        g2.fillRoundRect(
            0,
            0,
            getWidth(),
            getHeight(),
            radius,
            radius
        );

        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(borderColor);

        g2.setStroke(
            new BasicStroke(1.5f)
        );

        g2.drawRoundRect(
            0,
            0,
            getWidth() - 1,
            getHeight() - 1,
            radius,
            radius
        );

        g2.dispose();
    }

    @Override
    public Insets getInsets() {

        int value =
            Math.max(10, radius / 2);

        return new Insets(
            value,
            value,
            value,
            value
        );
    }
}


class aBackgroundPanel extends JPanel {

    private BufferedImage backgroundImage;

    public aBackgroundPanel(String imagePath) {

        try {

            backgroundImage =
                ImageIO.read(
                    new File(imagePath)
                );

        } catch (Exception e) {

            System.err.println(
                "Background tidak ditemukan: "
                + imagePath
            );
        }

        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (backgroundImage != null) {

            g.drawImage(
                backgroundImage,
                0,
                0,
                getWidth(),
                getHeight(),
                this
            );
        }
    }
}


public class MenuUtama1 extends JFrame {

    public MenuUtama1() {

        setTitle("Menu Utama");

        setExtendedState(
            JFrame.MAXIMIZED_BOTH
        );

        setDefaultCloseOperation(
            DO_NOTHING_ON_CLOSE
        );


        addWindowListener(
            new WindowAdapter() {

                @Override
                public void windowClosing(
                    WindowEvent e
                ) {

                    CustomCloseDialog dialog =
                        new CustomCloseDialog(
                            MenuUtama1.this
                        );

                    dialog.setVisible(true);

                    if (dialog.isConfirmed()) {

                        dispose();

                        System.exit(0);
                    }
                }
            }
        );

        aBackgroundPanel backgroundPanel =
            new aBackgroundPanel(
                "bg_pikku.jpg"
            );

        backgroundPanel.setLayout(
            new BorderLayout()
        );


        JLabel judul =
            new JLabel(
                "Pilih Game"
            );

        judul.setFont(
            new Font(
                "Poppins",
                Font.BOLD,
                38
            )
        );

        judul.setForeground(
            Color.WHITE
        );

        judul.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        aRoundedButton btnTebakTebakkan =
            new aRoundedButton(
                "Tebak-Tebakkan"
            );

        aRoundedButton btnGameTebakKata =
            new aRoundedButton(
                "Game Tebak Kata"
            );

        aRoundedButton btnScrabble =
            new aRoundedButton(
                "Game Scrabble"
            );


        Color transparentGray =
            new Color(
                220,
                220,
                220,
                190
            );


        aRoundedButton[] semuaTombol = {

            btnTebakTebakkan,

            btnGameTebakKata,

            btnScrabble
        };


        for (
            aRoundedButton button
            : semuaTombol
        ) {

            button.setBackground(
                transparentGray
            );

            button.setBorderColor(
                Color.DARK_GRAY
            );

            button.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );
        }


        btnTebakTebakkan
            .addActionListener(
                e -> {

                    dispose();

                    new TebakTebakkan()
                        .setVisible(true);
                }
            );

        btnGameTebakKata
            .addActionListener(
                e -> {

                    CustomTemaDialog dialog =
                        new CustomTemaDialog(
                            MenuUtama1.this
                        );

                    dialog.setVisible(true);


                    if (
                        dialog.getPilihan()
                        == 0
                    ) {

                        dispose();

                        new GameTebakKata(
                            "hewan"
                        );

                    } else if (
                        dialog.getPilihan()
                        == 1
                    ) {

                        dispose();

                        new GameTebakKata(
                            "buah"
                        );
                    }
                }
            );

        btnScrabble
            .addActionListener(
                e -> {

                    dispose();

                    new GameScrabble();
                }
            );

        JPanel panelTombol =
            new JPanel();

        panelTombol.setLayout(
            new BoxLayout(
                panelTombol,
                BoxLayout.Y_AXIS
            )
        );

        panelTombol.setOpaque(false);


        panelTombol.add(
            btnTebakTebakkan
        );

        panelTombol.add(
            Box.createVerticalStrut(22)
        );

        panelTombol.add(
            btnGameTebakKata
        );

        panelTombol.add(
            Box.createVerticalStrut(22)
        );

        panelTombol.add(
            btnScrabble
        );


        panelTombol.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        JPanel center =
            new JPanel();

        center.setOpaque(false);

        center.setLayout(
            new BoxLayout(
                center,
                BoxLayout.Y_AXIS
            )
        );


        center.add(
            Box.createVerticalStrut(385)
        );


        center.add(judul);


    
        center.add(
            Box.createVerticalStrut(40)
        );


        center.add(panelTombol);


        center.add(
            Box.createVerticalGlue()
        );


        backgroundPanel.add(
            center,
            BorderLayout.CENTER
        );

        setContentPane(
            backgroundPanel
        );

        tampilkanDenganFade();
    }

    private void tampilkanDenganFade() {

        try {

            setOpacity(0f);

            setVisible(true);


            Timer timer =
                new Timer(
                    20,
                    null
                );


            timer.addActionListener(
                new ActionListener() {

                    float opacity = 0f;


                    @Override
                    public void actionPerformed(
                        ActionEvent e
                    ) {

                        opacity += 0.05f;


                        try {

                            setOpacity(
                                Math.min(
                                    opacity,
                                    1f
                                )
                            );

                        } catch (
                            Exception ex
                        ) {

                            setOpacity(1f);

                            (
                                (Timer)
                                e.getSource()
                            ).stop();
                        }


                        if (
                            opacity >= 1f
                        ) {

                            (
                                (Timer)
                                e.getSource()
                            ).stop();
                        }
                    }
                }
            );


            timer.start();

        } catch (
            Exception e
        ) {

            setVisible(true);
        }
    }

    public class CustomCloseDialog
        extends JDialog {

        private boolean confirmed =
            false;


        public CustomCloseDialog(
            JFrame parent
        ) {

            super(
                parent,
                "Konfirmasi Keluar",
                true
            );


            setUndecorated(true);


            JPanel panel =
                buatPanelDialog(
                    28
                );


            panel.setLayout(
                new BoxLayout(
                    panel,
                    BoxLayout.Y_AXIS
                )
            );


            panel.setBorder(
                BorderFactory
                    .createEmptyBorder(
                        32,
                        32,
                        32,
                        32
                    )
            );


            JLabel label =
                new JLabel(
                    "<html><center>"
                    + "Yakin ingin keluar "
                    + "dari permainan?"
                    + "</center></html>"
                );


            label.setFont(
                new Font(
                    "Poppins",
                    Font.BOLD,
                    22
                )
            );


            label.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );


            panel.add(label);


            panel.add(
                Box.createVerticalStrut(
                    30
                )
            );


            JPanel panelButton =
                new JPanel(
                    new FlowLayout(
                        FlowLayout.CENTER,
                        22,
                        0
                    )
                );


            panelButton.setOpaque(
                false
            );


            // Tombol Ya
            aRoundedButton btnYes =
                new aRoundedButton(
                    "Ya, Keluar"
                );


            btnYes.setPreferredSize(
                new Dimension(
                    170,
                    55
                )
            );


            btnYes.setBackground(
                new Color(
                    220,
                    78,
                    65,
                    220
                )
            );


            btnYes.setForeground(
                Color.WHITE
            );


            btnYes.addActionListener(
                e -> {

                    confirmed = true;

                    dispose();
                }
            );


            // Tombol Batal
            aRoundedButton btnNo =
                new aRoundedButton(
                    "Batal"
                );


            btnNo.setPreferredSize(
                new Dimension(
                    150,
                    55
                )
            );


            btnNo.setBackground(
                new Color(
                    200,
                    200,
                    200,
                    220
                )
            );


            btnNo.addActionListener(
                e -> {

                    confirmed = false;

                    dispose();
                }
            );


            panelButton.add(
                btnYes
            );


            panelButton.add(
                btnNo
            );


            panel.add(
                panelButton
            );


            setContentPane(
                panel
            );


            pack();


            setLocationRelativeTo(
                parent
            );
        }


        public boolean isConfirmed() {

            return confirmed;
        }
    }


    class CustomTemaDialog
        extends JDialog {

        private int pilihan =
            -1;


        public CustomTemaDialog(
            JFrame parent
        ) {

            super(
                parent,
                "Pilih Tema",
                true
            );


            setUndecorated(
                true
            );


            JPanel panel =
                buatPanelDialog(
                    30
                );


            panel.setLayout(
                new BoxLayout(
                    panel,
                    BoxLayout.Y_AXIS
                )
            );


            panel.setBorder(
                BorderFactory
                    .createEmptyBorder(
                        32,
                        32,
                        32,
                        32
                    )
            );


            JLabel label =
                new JLabel(
                    "<html><center>"
                    + "Pilih tema untuk "
                    + "Tebak Kata:"
                    + "</center></html>"
                );


            label.setFont(
                new Font(
                    "Poppins",
                    Font.BOLD,
                    22
                )
            );


            label.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );


            panel.add(label);


            panel.add(
                Box.createVerticalStrut(
                    20
                )
            );


            JPanel panelButton =
                new JPanel(
                    new FlowLayout(
                        FlowLayout.CENTER,
                        22,
                        0
                    )
                );


            panelButton.setOpaque(
                false
            );


            // Tema Hewan
            aRoundedButton btnHewan =
                new aRoundedButton(
                    "Hewan"
                );


            btnHewan.setPreferredSize(
                new Dimension(
                    150,
                    55
                )
            );


            btnHewan.setBackground(
                new Color(
                    0,
                    120,
                    215,
                    230
                )
            );


            btnHewan.setForeground(
                Color.WHITE
            );


            btnHewan.addActionListener(
                e -> {

                    pilihan = 0;

                    dispose();
                }
            );


            // Tema Buah
            aRoundedButton btnBuah =
                new aRoundedButton(
                    "Buah"
                );


            btnBuah.setPreferredSize(
                new Dimension(
                    150,
                    55
                )
            );


            btnBuah.setBackground(
                new Color(
                    255,
                    167,
                    38,
                    230
                )
            );


            btnBuah.setForeground(
                Color.WHITE
            );


            btnBuah.addActionListener(
                e -> {

                    pilihan = 1;

                    dispose();
                }
            );


            panelButton.add(
                btnHewan
            );


            panelButton.add(
                btnBuah
            );


            panel.add(
                panelButton
            );


            setContentPane(
                panel
            );


            pack();


            setLocationRelativeTo(
                parent
            );
        }


        public int getPilihan() {

            return pilihan;
        }
    }

    private JPanel buatPanelDialog(
        int radius
    ) {

        JPanel panel =
            new JPanel() {

                @Override
                protected void paintComponent(
                    Graphics g
                ) {

                    Graphics2D g2 =
                        (Graphics2D)
                        g.create();


                    g2.setRenderingHint(
                        RenderingHints
                            .KEY_ANTIALIASING,
                        RenderingHints
                            .VALUE_ANTIALIAS_ON
                    );


                    g2.setColor(
                        new Color(
                            255,
                            255,
                            255,
                            245
                        )
                    );


                    g2.fillRoundRect(
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        radius,
                        radius
                    );


                    g2.setColor(
                        new Color(
                            220,
                            220,
                            220,
                            180
                        )
                    );


                    g2.drawRoundRect(
                        0,
                        0,
                        getWidth() - 1,
                        getHeight() - 1,
                        radius,
                        radius
                    );


                    g2.dispose();
                }
            };


        panel.setOpaque(
            false
        );


        return panel;
    }
    public static void main(
        String[] args
    ) {

        SwingUtilities.invokeLater(
            MenuUtama1::new
        );
    }
}