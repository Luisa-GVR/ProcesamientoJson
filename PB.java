import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class PB extends JFrame {
    private JTable table;

    public PB(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Car");
        model.addColumn("Price");
        model.addColumn("State");
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        try{

            Gson gson = new Gson();
            FileReader reader = new FileReader("car_sales.json");
            java.lang.reflect.Type tiposLista = new TypeToken<List<PB2>>() {}.getType();


            List<PB2> carrosLista = gson.fromJson(reader, tiposLista);

            for (PB2 pb2 : carrosLista) {
                model.addRow(new Object[]{
                        pb2.getId(),
                        pb2.getFirstName(),
                        pb2.getLastName(),
                        pb2.getCar(),
                        pb2.getPrice(),
                        pb2.getState()
                });
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PB app = new PB();
            app.setVisible(true);
        });
    }

}
