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
productRepository.save(new Product("LED100", "Een energiezuinige 100W LED-lamp, ideaal voor zowel binnen- als buitentoepassingen, met lange levensduur en helder licht.", "LED", 5, BigDecimal.valueOf(20.00)));
            productRepository.save(new Product("Flood200", "Krachtige 200W LED-floodlight, ontworpen voor het verlichten van grote evenementen, biedt een breed en helder licht.", "LED", 4, BigDecimal.valueOf(60.00)));

            productRepository.save(new Product("Podium1x1", "Een stabiel podiumelement van 1m x 1m, ideaal voor tijdelijke opstellingen bij evenementen en snel op te zetten.", "Podium", 3, BigDecimal.valueOf(75.00)));
            productRepository.save(new Product("Monitor15", "Professionele 15 inch stage monitor, ontworpen voor podiumgebruik en biedt helder geluid voor optredens.", "Podium", 2, BigDecimal.valueOf(250.00)));
            productRepository.save(new Product("Mix8", "Een 8-kanaals audiomixer voor live-optredens, met voldoende ingangen voor verschillende geluidsbronnen en hoogwaardige geluidsbehandeling.", "Podium", 1, BigDecimal.valueOf(350.00)));

            productRepository.save(new Product("XLR3", "3 meter lange XLR-kabel voor betrouwbare verbindingen tussen audioapparatuur, geschikt voor live-geluidssystemen.", "Kabel", 20, BigDecimal.valueOf(15.00)));
            productRepository.save(new Product("Power5", "Powerstrip met 5 stopcontacten voor het aansluiten van meerdere apparaten tegelijkertijd, biedt bescherming tegen overbelasting.", "Access", 15, BigDecimal.valueOf(10.00)));
            productRepository.save(new Product("MicStand", "Verstelbare microfoonstandaard die geschikt is voor gebruik op het podium of in de studio, met robuuste ondersteuning voor verschillende microfoons.", "Access", 8, BigDecimal.valueOf(25.00)));
            productRepository.save(new Product("RGBPanel", "RGB lichtpaneel, perfect voor sfeerverlichting of decoratie, biedt veelzijdige kleuropties voor verschillende settings.", "LED", 10, BigDecimal.valueOf(150.00)));
            productRepository.save(new Product("LEDWhite", "Wit LED paneel voor sfeerverlichting, ideaal voor het creëren van een zachte en stijlvolle verlichting voor diverse omgevingen.", "LED", 6, BigDecimal.valueOf(80.00)));

    }
}
