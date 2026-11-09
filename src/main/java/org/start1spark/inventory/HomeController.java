
package org.start1spark.inventory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private final JdbcTemplate jdbcTemplate;

    // Connects the website to PostgreSQL
    public HomeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Opens the homepage
    @GetMapping("/")
    public String home() {
        return "home";
    }

    // Opens the inventory page
    @GetMapping("/inventory")
    public String inventory(Model model) {

        // Gets all 13 fields from the inventory database
        String sql = """
            SELECT
                item_name AS name,
                quantity,
                COALESCE(size, '') AS size,
                COALESCE(gender, '') AS gender,
                COALESCE(color, '') AS color,
                COALESCE(brand, '') AS brand,
                COALESCE(
                    TO_CHAR(date_received, 'MM/DD/YYYY'), ''
                ) AS date_received,
                COALESCE(donor, '') AS donor,
                COALESCE(acknowledged, '') AS acknowledged,
                COALESCE(status, '') AS status,
                estimated_value AS value,
                COALESCE(school, '') AS school,
                COALESCE(notes, '') AS notes
            FROM inventory
            ORDER BY item_id
            """;

        // Gets inventory records from PostgreSQL
        List<Map<String, Object>> items =
                jdbcTemplate.queryForList(sql);

        // Sends the inventory records to the HTML page
        model.addAttribute("items", items);

        return "inventory";
    }

    // Opens the Add Inventory Item form
    @GetMapping("/inventory/add")
    public String showAddItemForm() {
        return "add-item";
    }

    // Saves a new inventory item to PostgreSQL
    @PostMapping("/inventory/add")
    public String addItem(

            // Required fields
            @RequestParam String name,
            @RequestParam int quantity,

            // Optional item details
            @RequestParam(defaultValue = "") String size,
            @RequestParam(defaultValue = "") String gender,
            @RequestParam(defaultValue = "") String color,
            @RequestParam(defaultValue = "") String brand,

            // Optional date
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateReceived,

            // Donation information
            @RequestParam(defaultValue = "") String donor,
            @RequestParam(defaultValue = "") String acknowledged,
            @RequestParam String status,

            // Optional estimated value
            @RequestParam(required = false) BigDecimal value,

            // School and notes
            @RequestParam(defaultValue = "") String school,
            @RequestParam(defaultValue = "") String notes,

            RedirectAttributes redirectAttributes) {

        // Checks item name and quantity
        if (name.isBlank() || name.trim().length() > 150 ||
                quantity < 0) {

            redirectAttributes.addFlashAttribute(
                "error", "Enter a valid item name and quantity."
            );

            return "redirect:/inventory/add";
        }

        // Checks the status against sponsor's options
        List<String> validStatuses = List.of(
            "Given to Partner",
            "In Stock",
            "Discarded",
            "Status",
            "Partially Donated",
            "Donated"
        );

        if (!validStatuses.contains(status)) {

            redirectAttributes.addFlashAttribute(
                "error", "Select a valid inventory status."
            );

            return "redirect:/inventory/add";
        }

        // Checks acknowledgment against sponsor's options
        List<String> validAcknowledgments = List.of(
            "",
            "Y",
            "N",
            "n/a",
            "Acknowledged"
        );

        if (!validAcknowledgments.contains(acknowledged)) {

            redirectAttributes.addFlashAttribute(
                "error", "Select a valid acknowledgment."
            );

            return "redirect:/inventory/add";
        }

        // Saves blank acknowledgment as NULL
        String acknowledgedValue =
                acknowledged.isEmpty() ? null : acknowledged;

        // Checks that estimated value is not negative
        if (value != null && value.signum() < 0) {

            redirectAttributes.addFlashAttribute(
                "error", "Value cannot be negative."
            );

            return "redirect:/inventory/add";
        }

        // SQL query to save all 13 fields
        String sql = """
            INSERT INTO inventory (
                item_name,
                quantity,
                size,
                gender,
                color,
                brand,
                date_received,
                donor,
                acknowledged,
                status,
                estimated_value,
                school,
                notes
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        // Saves the information entered in the form
        jdbcTemplate.update(
            sql,
            name.trim(),
            quantity,
            size,
            gender,
            color,
            brand,
            dateReceived,
            donor,
            acknowledgedValue,
            status,
            value,
            school,
            notes
        );

        // Shows a message after saving an item
        redirectAttributes.addFlashAttribute(
            "success", "Inventory item added successfully!"
        );

        // Returns to the inventory page
        return "redirect:/inventory";
    }
}
