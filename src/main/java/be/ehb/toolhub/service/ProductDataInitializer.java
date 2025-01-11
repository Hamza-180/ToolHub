package be.ehb.toolhub.service;

import be.ehb.toolhub.model.Product;
import be.ehb.toolhub.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductDataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    public ProductDataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Controleer of de database al producten bevat

            // Voeg enkele producten toe als er nog geen producten zijn
            productRepository.save(new Product("Lamp 100W LED", "Lampen", "Energiezuinige LED-lamp van 100 watt.", 5, BigDecimal.valueOf(20.00)));
            productRepository.save(new Product("Podiumelement - 1m x 1m", "Podiumelementen", "Stabiel podiumelement voor tijdelijke opstellingen.", 3, BigDecimal.valueOf(75.00)));
            productRepository.save(new Product("Lichtpaneel RGB", "Lichtpanelen", "RGB lichtpaneel voor decoratie en verlichting.", 10, BigDecimal.valueOf(150.00)));
            productRepository.save(new Product("XLR Kabel 3 meter", "Kabels", "XLR kabel voor audioverbindingen, 3 meter.", 20, BigDecimal.valueOf(15.00)));
            productRepository.save(new Product("Powerstrip 5 stopcontacten", "Accessoires", "Powerstrip met 5 stopcontacten.", 15, BigDecimal.valueOf(10.00)));
            productRepository.save(new Product("Stage Monitor 15 inch", "Podiumelementen", "Stage monitor voor podiumgebruik.", 2, BigDecimal.valueOf(250.00)));
            productRepository.save(new Product("Mic Standaard", "Accessoires", "Verstelbare microfoonstandaard.", 8, BigDecimal.valueOf(25.00)));
            productRepository.save(new Product("LED Floodlight 200W", "Lampen", "LED floodlight voor evenementenverlichting.", 4, BigDecimal.valueOf(60.00)));
            productRepository.save(new Product("Audiomixer 8 kanaals", "Podiumelementen", "8 kanaals audiomixer voor geluidssystemen.", 1, BigDecimal.valueOf(350.00)));
            productRepository.save(new Product("Lichtpaneel LED Wit", "Lichtpanelen", "Wit LED lichtpaneel voor sfeerverlichting.", 6, BigDecimal.valueOf(80.00)));

    }
}
