<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Car List</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            loadCars();

            $("#addCarForm").submit(function(event) {
                event.preventDefault();
                addCar();
            });
        });

        function loadCars() {
            $.getJSON("cars", function(cars) {
                let tableBody = $("#carTable tbody");
                tableBody.empty();
                cars.forEach(function(car) {
                    let row = $("<tr>")
                        .append($("<td>").text(car.make))
                        .append($("<td>").text(car.model))
                        .append($("<td>").text(car.year))
                        .append($("<td>").text(car.color))
                        .append($("<td>").text(car.price));
                    tableBody.append(row);
                });
            });
        }

        function addCar() {
            let carData = $("#addCarForm").serialize();
            $.post("cars", carData, function() {
                loadCars();
                $("#addCarForm")[0].reset();
            });
        }
    </script>
</head>
<body>
<h1>Car List</h1>
<form id="addCarForm">
    <label for="make">Make:</label>
    <input type="text" id="make" name="make" required>
    <label for="model">Model:</label>
    <input type="text" id="model" name="model" required>
    <label for="year">Year:</label>
    <input type="number" id="year" name="year" required>
    <label for="color">Color:</label>
    <input type="text" id="color" name="color" required>
    <label for="price">Price:</label>
    <input type="number" id="price" name="price" step="0.01" required>
    <button type="submit">Add Car</button>
</form>
<table id="carTable" border="1">
    <thead>
    <tr>
        <th>Make</th>
        <th>Model</th>
        <th>Year</th>
        <th>Color</th>
        <th>Price</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>
</body>
</html>