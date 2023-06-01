import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cars")
public class CarServlet extends HttpServlet {
    private static final String FILE_PATH = "cars.json";
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Car> cars = readCarsFromFile();
        String json = gson.toJson(cars);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String make = request.getParameter("make");
        String model = request.getParameter("model");
        int year = Integer.parseInt(request.getParameter("year"));
        String color = request.getParameter("color");
        double price = Double.parseDouble(request.getParameter("price"));

        Car newCar = new Car(make, model, year, color, price);
        List<Car> cars = readCarsFromFile();
        cars.add(newCar);
        writeCarsToFile(cars);

        response.sendRedirect("index.jsp");
    }

    private List<Car> readCarsFromFile() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, new TypeToken<List<Car>>() {}.getType());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void writeCarsToFile(List<Car> cars) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(cars, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}