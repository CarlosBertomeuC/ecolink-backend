package com.ecolink.spring.loaders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ecolink.spring.entity.Category;
import com.ecolink.spring.entity.Product;
import com.ecolink.spring.entity.Startup;
import com.ecolink.spring.service.CategoryService;
import com.ecolink.spring.service.ProductService;
import com.ecolink.spring.service.StartupService;

@Component
@Order(4)
public class ProductDataLoader implements CommandLineRunner {
        @Autowired
        private ProductService service;

        @Autowired
        private StartupService startupService;

        @Autowired
        private CategoryService categoryService;


        @Override
        public void run(String... args) throws Exception {
                Startup beCause = startupService.findByName("BeCause");
                Startup chronosHub = startupService.findByName("ChronosHub");
                Startup station = startupService.findByName("Station");
                Startup rightHub = startupService.findByName("RightHub");
                Startup silvi = startupService.findByName("Silvi");
                Startup nutrishAi = startupService.findByName("Nutrish.ai");
                Startup kodiakHub = startupService.findByName("KodiakHub");
                Startup seasony = startupService.findByName("Seasony");
                Startup ecoTree = startupService.findByName("EcoTree");
                Startup goMore = startupService.findByName("GoMore");
                Startup goodWings = startupService.findByName("GoodWings");
                Startup glintSolar = startupService.findByName("GlintSolar");
                Startup spritju = startupService.findByName("Spritju");
                Startup deepBlu = startupService.findByName("DeepBlu");
                Startup vove = startupService.findByName("Vove");
                Startup perPlant = startupService.findByName("PerPlant");
                Startup dripdrop = startupService.findByName("Dripdrop");
                Startup legitify = startupService.findByName("Legitify");
                Startup whistleSystem = startupService.findByName("WhistleSystem");
                Startup flavorScale = startupService.findByName("FlavorScale");
                Startup climaider = startupService.findByName("Climaider");
                Startup tribe = startupService.findByName("Tribe");
                Startup peltarion = startupService.findByName("Peltarion");
                Startup donkeyRepublic = startupService.findByName("Donkey Republic");
                Startup pickYourPour = startupService.findByName("Pick Your Pour AS");
                Startup aliceAi = startupService.findByName("Alice AI");
                Startup leiaHealth = startupService.findByName("LEIA Health");

                Category booksAndStationery = categoryService.findByName("Books & Stationery");
                Category industrialAndOfficeSupplies = categoryService.findByName("Industrial & Office Supplies");
                Category groceryAndFood = categoryService.findByName("Grocery & Food");
                Category homeAndKitchen = categoryService.findByName("Home & Kitchen");
                Category sportsAndOutdoors = categoryService.findByName("Sports & Outdoors");
                Category automotiveAndAccessories = categoryService.findByName("Automotive & Accessories");
                Category fashionAndAccessories = categoryService.findByName("Fashion & Accessories");
                Category electronicsAndTechnology = categoryService.findByName("Electronics & Technology");
                Category healthAndWellness = categoryService.findByName("Health & Wellness");
                Category toolsAndHomeImprovement = categoryService.findByName("Tools & Home Improvement");

                List<Product> products = Arrays.asList(
                                new Product(beCause, "EcoAction Planner",
                                                "A sustainable planner and journal made from recycled materials, designed for businesses and entrepreneurs aiming to set, monitor, and communicate sustainability goals.",
                                                new BigDecimal("19.99"), LocalDate.of(2025, 2, 18),
                                                "libreta.jpg",
                                                new ArrayList<>(Arrays.asList(booksAndStationery))),
                                new Product(chronosHub, "Academic Toolkit",
                                                "A set of stationery and printed resources for researchers, including a planner, quick Open Access guides, checklists for APC management, and templates for communication with editors.",
                                                new BigDecimal("29.99"), LocalDate.of(2025, 2, 18),
                                                "toolkit.jpg",
                                                new ArrayList<>(Arrays.asList(booksAndStationery))),
                                new Product(station, "Community Building Kit",
                                                "A comprehensive kit with activity guides, worksheets, and collaboration tools to foster inclusive student communities and empower youth for positive change.",
                                                new BigDecimal("24.99"), LocalDate.of(2025, 2, 18),
                                                "comunity.jpg",
                                                new ArrayList<>(Arrays.asList(booksAndStationery))),
                                new Product(rightHub, "IP Organizer",
                                                "A high-quality file folder system for managing patents, trademarks, and copyrights, paired with a digital guide to streamline your IP documentation process.",
                                                new BigDecimal("34.99"), LocalDate.of(2025, 2, 18),
                                                "organizer.jpg",
                                                new ArrayList<>(Arrays.asList(industrialAndOfficeSupplies))),
                                new Product(silvi, "Research Companion",
                                                "A robust notebook and quick-reference guide for conducting systematic literature reviews and meta-analyses, including AI integration tips for streamlined research.",
                                                new BigDecimal("19.99"), LocalDate.of(2025, 2, 18),
                                                "handbook.jpg",
                                                new ArrayList<>(Arrays.asList(booksAndStationery))),
                                new Product(nutrishAi, "Smart Meal Prep Kit",
                                                "A curated set of meal prep containers, portion guides, and a nutrition planner, combined with a QR code link to AI-driven personalized diet recommendations.",
                                                new BigDecimal("39.99"), LocalDate.of(2025, 2, 18),
                                                "mealkit.jpg",
                                                new ArrayList<>(Arrays.asList(groceryAndFood))),
                                new Product(kodiakHub, "Supplier Sustainability Handbook",
                                                "A comprehensive handbook and workbook designed to help procurement teams foster sustainable relationships with suppliers and track performance metrics effectively.",
                                                new BigDecimal("21.99"), LocalDate.of(2025, 2, 18),
                                                "notebook.jpg",
                                                new ArrayList<>(Arrays.asList(booksAndStationery))),
                                new Product(seasony, "Hydroponic Starter Kit",
                                                "An easy-to-use indoor gardening system that brings vertical farming principles into your home, complete with seeds, nutrients, and monitoring instructions.",
                                                new BigDecimal("99.99"), LocalDate.of(2025, 2, 18),
                                                "Hydroponic.jpg",
                                                new ArrayList<>(Arrays.asList(homeAndKitchen))),
                                new Product(ecoTree, "Reforesting Kit",
                                                "A curated set of native tree saplings, planting tools, and a step-by-step guide to help you offset carbon footprints and contribute to local biodiversity.",
                                                new BigDecimal("49.99"), LocalDate.of(2025, 2, 18),
                                                "reforesting.jpg",
                                                new ArrayList<>(Arrays.asList(sportsAndOutdoors))),
                                new Product(goMore, "Carpool Comfort Pack",
                                                "A practical travel kit including a seat organizer, neck pillow, and reusable water bottle, enhancing shared rides while promoting eco-friendly travel.",
                                                new BigDecimal("29.99"), LocalDate.of(2025, 2, 18),
                                                "neckpillow.jpg",
                                                new ArrayList<>(Arrays.asList(automotiveAndAccessories))),
                                new Product(goodWings, "Sustainable Travel Set",
                                                "An eco-friendly travel essentials kit featuring refillable toiletry containers, a bamboo toothbrush, and a durable travel pouch to reduce single-use waste.",
                                                new BigDecimal("34.99"), LocalDate.of(2025, 2, 18),
                                                "bambo.jpg",
                                                new ArrayList<>(Arrays.asList(fashionAndAccessories))),
                                new Product(glintSolar, "Portable Charger",
                                                "A compact, foldable solar panel charger ideal for charging phones and small devices on the go, showcasing the power of renewable solar technology.",
                                                new BigDecimal("59.99"), LocalDate.of(2025, 2, 18),
                                                "charger.jpg",
                                                new ArrayList<>(Arrays.asList(electronicsAndTechnology))),
                                new Product(spritju, "Smart Energy Monitor",
                                                "A real-time energy tracking device that pairs with an intuitive dashboard, helping businesses and individuals monitor usage and reduce carbon footprints.",
                                                new BigDecimal("79.99"), LocalDate.of(2025, 2, 18),
                                                "energymonitor.jpg",
                                                new ArrayList<>(Arrays.asList(electronicsAndTechnology))),
                                new Product(deepBlu, "Dive Journal",
                                                "A durable water-resistant notebook designed for scuba and freedivers, featuring dive log templates, waterproof pages, and tips for marine conservation.",
                                                new BigDecimal("24.99"), LocalDate.of(2025, 2, 18),
                                                "divejournal.jpg",
                                                new ArrayList<>(Arrays.asList(sportsAndOutdoors))),
                                new Product(vove, "Household Essentials Bundle",
                                                "A curated pack of eco-friendly household products and refill pouches, combining convenience and sustainability for your everyday home care routine.",
                                                new BigDecimal("25.99"), LocalDate.of(2025, 2, 18),
                                                "household.jpg",
                                                new ArrayList<>(Arrays.asList(homeAndKitchen))),
                                new Product(perPlant, "Soil Sensor Kit",
                                                "An AI-compatible sensor kit that gives farmers real-time data on soil moisture, temperature, and nutrient levels, minimizing pesticide and fertilizer waste.",
                                                new BigDecimal("149.99"), LocalDate.of(2025, 2, 18),
                                                "soilsensor.jpg",
                                                new ArrayList<>(Arrays.asList(toolsAndHomeImprovement))),
                                new Product(dripdrop, "Recycled Umbrella",
                                                "A stylish, durable umbrella made from recycled plastic, ideal for hotels and personal use, with a commitment to plant a tree for each purchase.",
                                                new BigDecimal("19.99"), LocalDate.of(2025, 2, 18),
                                                "umbrella.jpg",
                                                new ArrayList<>(Arrays.asList(fashionAndAccessories))),
                                new Product(legitify, "E-Notary Signature Pad",
                                                "An advanced digital signature device that streamlines remote online notarization, offering secure audio-video authentication for legal documents.",
                                                new BigDecimal("129.99"), LocalDate.of(2025, 2, 18),
                                                "signaturePad.jpg",
                                                new ArrayList<>(Arrays.asList(electronicsAndTechnology))),
                                new Product(whistleSystem, "Compliance Kit",
                                                "A complete set of documentation, reference guides, and digital access to whistleblower compliance tools, ensuring businesses meet EU regulatory requirements.",
                                                new BigDecimal("59.99"), LocalDate.of(2025, 2, 18),
                                                "documentationkit.jpg",
                                                new ArrayList<>(Arrays.asList(industrialAndOfficeSupplies))),
                                new Product(flavorScale, "Tasting Journal",
                                                "A guided tasting journal that helps users explore and rate restaurant experiences, featuring flavor profiling tips and QR codes for digital discovery.",
                                                new BigDecimal("24.99"), LocalDate.of(2025, 2, 18),
                                                "tastingJournal.jpg",
                                                new ArrayList<>(Arrays.asList(booksAndStationery))),
                                new Product(climaider, "ESG Reporting Binder",
                                                "A sturdy binder and workbook for small and medium-sized businesses to track and report ESG metrics, complete with step-by-step climate action guides.",
                                                new BigDecimal("29.99"), LocalDate.of(2025, 2, 18),
                                                "esg.jpg",
                                                new ArrayList<>(Arrays.asList(industrialAndOfficeSupplies))),
                                new Product(tribe, "Transparent Insurance Binder",
                                                "A clear and concise organizational binder for storing coverage details, featuring simplified explanations of life and non-life insurance policies.",
                                                new BigDecimal("19.99"), LocalDate.of(2025, 2, 18),
                                                "documentsinsurence.jpg",
                                                new ArrayList<>(Arrays.asList(industrialAndOfficeSupplies))),
                                new Product(peltarion, "AI Starter Pack",
                                                "A beginner-friendly kit with an illustrated guide on building AI models, plus sample datasets and practical tips for health, wealth, and sustainability applications.",
                                                new BigDecimal("59.99"), LocalDate.of(2025, 2, 18),
                                                "starterPack.jpg",
                                                new ArrayList<>(Arrays.asList(electronicsAndTechnology))),
                                new Product(donkeyRepublic, "Bike Accessory Set",
                                                "A set of must-have bike accessories, including a smart lock, LED lights, and reflective gear, encouraging safe and sustainable urban cycling.",
                                                new BigDecimal("39.99"), LocalDate.of(2025, 2, 18),
                                                "bike.jpg",
                                                new ArrayList<>(Arrays.asList(sportsAndOutdoors))),
                                new Product(pickYourPour, "Tasting Kit",
                                                "An at-home tasting bundle with labeled glassware, flavor profile cards, and a digital guide for discovering personalized food and drink pairings.",
                                                new BigDecimal("34.99"), LocalDate.of(2025, 2, 18),
                                                "tastingKit.jpg",
                                                new ArrayList<>(Arrays.asList(homeAndKitchen))),
                                new Product(aliceAi, "Study Planner",
                                                "A student-oriented planner with interactive QR codes linking to personalized study modules, helping learners stay organized and motivated.",
                                                new BigDecimal("14.99"), LocalDate.of(2025, 2, 18),
                                                "studyplanner.jpg",
                                                new ArrayList<>(Arrays.asList(booksAndStationery))),
                                new Product(leiaHealth, "Smart Baby Monitor",
                                                "An AI-assisted baby monitor that tracks sleep patterns and vital signs, offering parents real-time insights and alerts through a user-friendly app.",
                                                new BigDecimal("99.99"), LocalDate.of(2025, 2, 18),
                                                "babySmart.jpg",
                                                new ArrayList<>(Arrays.asList(healthAndWellness))));

                products.forEach(product -> {
                        if (!service.existsByNameAndStartup(product.getName(), product.getStartup())) {
                                service.save(product);
                        }
                });
        }
}
