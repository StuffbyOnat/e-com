package frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// JPanel'i miras alan kendi özel objemiz
class ProductPane extends JPanel {

    // Veritabanından gelecek dataları tutacak değişkenler
    private int id;
    private String name;
    private double price;
    product prodcut;
    
    // Renk ayarları
    private final Color NORMAL_COLOR = Color.WHITE;
    private final Color HOVER_COLOR = new Color(64, 64, 64); // Üzerine gelince kararması için koyu gri

    public ProductPane(int id, String name, double price, String category, String description, String color, String size, String shade,String sku_Code) {
        this.id = id;
        this.name = name;
        this.price = price;
        prodcut = new product(id, name, category, price, description, color, size, shade,sku_Code);

        // Panelin varsayılan tasarımı
        setBackground(NORMAL_COLOR);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(150, 150));

        // Panelin içine yazıları (dataları) ekleme
        JLabel nameLbl = new JLabel(name);
        nameLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel priceLbl = new JLabel(price + " TL");
        priceLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(40)); // Üstten boşluk
        add(nameLbl);
        add(Box.createVerticalStrut(10)); // Araya boşluk
        add(priceLbl);

        // --- BÜTÜN OLAY BURADA: MOUSE ETKİLEŞİMLERİ ---
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Mouse panele girince arkaplanı karart ve imleci el işaretine çevir
                setBackground(HOVER_COLOR);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Mouse panelden çıkınca eski beyaz rengine döndür
                setBackground(NORMAL_COLOR);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Panele tıklandığında yapılacak işlem
                System.out.println("Tıklanan Obje ID: " + id);
                JOptionPane.showMessageDialog(null, name + " isimli ürüne tıkladın!");
            }
        });
    }
}