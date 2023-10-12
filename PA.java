import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class PA {
    public static void main(String[] args) {
        try{
            File jsonFile = new File("car_sales.json");
            JSONTokener tokener = new JSONTokener(new FileReader(jsonFile));
            JSONArray jsonArray = new JSONArray(tokener);
            Map<String, Integer> marcaCuenta = new HashMap<>();

            JSONObject preciosPromedio = new JSONObject();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject carroData = jsonArray.getJSONObject(i);
                String marca = carroData.getString("car");
                double precio = Double.parseDouble(carroData.getString("price").substring(1));

                if (preciosPromedio.has(marca)) {
                    double tempPromedio = preciosPromedio.getDouble(marca);
                    preciosPromedio.put(marca, tempPromedio + precio);
                    marcaCuenta.put(marca, marcaCuenta.get(marca) + 1);
                } else {
                    preciosPromedio.put(marca, precio);
                    marcaCuenta.put(marca, 1);
                }
            }

            for (String marca : preciosPromedio.keySet()) {
                double total = preciosPromedio.getDouble(marca);
                int totalDiferentes = marcaCuenta.get(marca);
                double precioPromedioReal = total / totalDiferentes;
                preciosPromedio.put(marca, precioPromedioReal);
            }

            System.out.println(preciosPromedio.toString(2));

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
