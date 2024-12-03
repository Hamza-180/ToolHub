# ToolHub Project Documentatie

Welkom bij het **ToolHub** project! Dit platform stelt gebruikers in staat om producten te beheren, reserveringen te maken en zich te registreren/inloggen voor een gebruiksvriendelijke ervaring. Hieronder volgt een gedetailleerd overzicht van de belangrijkste componenten en configuraties binnen de ToolHub-toepassing.

###  Op xamp moet je zelf een database aanmaken genaamd toolhub zodat spring het database herkent.

## Overzicht van de Belangrijkste Componenten

### 1. **AuthController.java** (Authenticatie Controller)
Beheert de gebruikersauthenticatie, inclusief inloggen, registreren en uitloggen.

#### Belangrijke Methodes:
- **`login` (POST /api/auth/login)**: Authenticeert een gebruiker op basis van de opgegeven gebruikersnaam en het wachtwoord.
  - **Antwoord**: Retourneert 200 OK bij een succesvolle login en 401 Unauthorized bij een mislukte login.

- **`register` (POST /api/auth/register)**: Registreert een nieuwe gebruiker met de gegevens die in de request body worden meegegeven.
  - **Antwoord**: Retourneert 201 Created bij een succesvolle registratie en 500 Internal Server Error bij een fout.

- **`logout` (GET /api/auth/logout)**: Verwijdert de gebruikerssessie en stuurt de gebruiker naar de inlogpagina.
  - **Antwoord**: Redirect naar de loginpagina na uitloggen.

---

### 2. **CartController.java** (Winkelwagen Controller)
Beheert de winkelwagenfunctionaliteit, zoals het toevoegen van producten, verwijderen ervan en het afhandelen van de checkout.

#### Belangrijke Methodes:
- **`getCart` (GET /api/cart)**: Haalt de producten op die momenteel in de winkelwagen zitten.
  - **Antwoord**: Retourneert een lijst met producten in de winkelwagen.

- **`addToCart` (POST /api/cart)**: Voegt een product toe aan de winkelwagen.
  - **Antwoord**: Retourneert 200 OK bij succes.

- **`removeFromCart` (DELETE /api/cart/{id})**: Verwijdert een product uit de winkelwagen op basis van het ID.
  - **Antwoord**: Retourneert 200 OK bij succes.

- **`checkout` (POST /api/cart/checkout)**: Verwerkt de bestelling en maakt een reservering in de database.
  - **Antwoord**: Retourneert 200 OK bij een succesvolle checkout en 400 Bad Request bij een mislukte bestelling.

---

### 3. **ProductController.java** (Product Controller)
Beheert de productgerelateerde functionaliteiten, zoals het ophalen van producten, het filteren op categorieën en het weergeven van productdetails.

#### Belangrijke Methodes:
- **`getAllProducts` (GET /api/products)**: Haalt alle producten op uit de database.
  - **Antwoord**: Retourneert een lijst met alle producten.

- **`getProductById` (GET /api/products/{id})**: Haalt een specifiek product op aan de hand van het product-ID.
  - **Antwoord**: Retourneert de details van het product.

- **`getProductsByCategory` (GET /api/products/category/{category})**: Haalt producten op op basis van de opgegeven categorie.
  - **Antwoord**: Retourneert een lijst met producten in de opgegeven categorie.

- **`addProduct` (POST /api/products)**: Voegt een nieuw product toe aan de database.
  - **Antwoord**: Retourneert 201 Created bij een succesvol toegevoegd product.

- **`updateProduct` (PUT /api/products/{id})**: Werk een bestaand product bij op basis van het product-ID.
  - **Antwoord**: Retourneert 200 OK bij een succesvolle update.

- **`deleteProduct` (DELETE /api/products/{id})**: Verwijdert een product op basis van het product-ID.
  - **Antwoord**: Retourneert 200 OK bij een succesvolle verwijdering.

---

### 4. **ReservationController.java** (Reservering Controller)
Beheert de reserveringen van gebruikers voor producten.

#### Belangrijke Methodes:
- **`createReservation` (POST /api/reservations)**: Maakt een nieuwe reservering op basis van de gebruikersgegevens en productinformatie.
  - **Antwoord**: Retourneert 201 Created bij een succesvolle reservering.

- **`getReservations` (GET /api/reservations)**: Haalt alle reserveringen op uit de database.
  - **Antwoord**: Retourneert een lijst met alle reserveringen.

- **`getReservationById` (GET /api/reservations/{id})**: Haalt een specifieke reservering op aan de hand van het reserverings-ID.
  - **Antwoord**: Retourneert de details van de reservering.

- **`updateReservation` (PUT /api/reservations/{id})**: Werk een bestaande reservering bij op basis van het reserverings-ID.
  - **Antwoord**: Retourneert 200 OK bij een succesvolle update.

- **`deleteReservation` (DELETE /api/reservations/{id})**: Verwijdert een reservering op basis van het reserverings-ID.
  - **Antwoord**: Retourneert 200 OK bij een succesvolle verwijdering.

---

### 5. **SecurityConfig.java** (Beveiligingsconfiguratie)
Configureert de beveiliging van de applicatie door Spring Security te integreren voor gebruikersauthenticatie, autorisatie en sessiebeheer.

#### Belangrijke Configuraties:
- **`csrf().disable()`**: De CSRF-beveiliging wordt uitgeschakeld (vaak nodig voor API-gebaseerde applicaties).
- **`authorizeHttpRequests()`**: Definieert welke URL-patronen toegankelijk zijn en wie toegang heeft:
  - Pagina's zoals `/login` en `/register` zijn voor iedereen toegankelijk.
  - Alle andere aanvragen vereisen authenticatie.
- **`formLogin()`**: Bepaalt de loginpagina en de redirect-URL na een succesvolle login.
  - Gebruikers worden doorverwezen naar `/dashboard` na een succesvolle login.
- **`logout()`**: Configureert de logout-functionaliteit.
  - Na uitloggen worden gebruikers doorverwezen naar `/login?logout`.
- **`sessionManagement()`**: Beheert sessies.
  - Sessies worden alleen aangemaakt indien nodig, en bij een ongeldige sessie worden gebruikers naar de loginpagina gestuurd.
- **`httpBasic()`**: Voegt basisauthenticatie toe voor API-communicatie via HTTP Basic Authentication.
- **`passwordEncoder()`**: Maakt een bean voor wachtwoordcodering met BCrypt.
- **`authenticationManager()`**: Behandelt de authenticatie van de gebruiker op basis van de CustomUserDetailsService en passwordEncoder.

---

### 6. **UserServiceImpl.java** (Gebruikersservice Implementatie)
Biedt de implementatie voor het beheren van gebruikers, zoals het ophalen van gebruikers, het aanmaken van nieuwe gebruikers en het verwijderen van bestaande gebruikers.

#### Belangrijke Methodes:
- **`getAllUsers()`**: Haalt een lijst van alle gebruikers op uit de database.
- **`getUserById(Long id)`**: Haalt een specifieke gebruiker op door het ID.
- **`createUser(User user)`**: Maakt een nieuwe gebruiker aan en slaat deze op in de database. Het wachtwoord wordt gehasht met de passwordEncoder voordat het wordt opgeslagen.
- **`deleteUser(Long id)`**: Verwijdert een gebruiker op basis van het ID.
- **`registerUser(User user)`**: Registreert een nieuwe gebruiker nadat gecontroleerd is of de gebruikersnaam al bestaat. Het wachtwoord wordt gehasht voordat het wordt opgeslagen in de database.

---

### 7. **CustomUserDetailsService.java** (Aangepaste Gebruikersservice)
Implementeert Spring Security’s `UserDetailsService` interface en is verantwoordelijk voor het ophalen van gebruikersgegevens op basis van de gebruikersnaam voor authenticatie.

#### Belangrijke Methodes:
- **`loadUserByUsername(String username)`**: Haalt de gebruiker op uit de database op basis van de gebruikersnaam. Als de gebruiker niet wordt gevonden, wordt er een `UsernameNotFoundException` gegooid. Anders wordt een `UserDetails` object aangemaakt met de gebruikersnaam, wachtwoord en rollen.

#### Samenwerking:
- **Authenticatie**: Bij inloggen wordt de `loadUserByUsername` methode aangeroepen om de gebruiker op te halen uit de database. Spring Security vergelijkt het opgegeven wachtwoord met het gehashte wachtwoord in de database. Als ze overeenkomen, wordt de gebruiker geauthenticeerd.
- **Registratie**: De `registerUser` methode in de `UserServiceImpl` controleert of de gebruikersnaam al bestaat. Als deze niet bestaat, wordt het wachtwoord van de gebruiker gehasht en opgeslagen.
- **Beveiliging**: De `SecurityConfig` zorgt ervoor dat beveiligingsinstellingen zoals inloggen, uitloggen en autorisatie correct werken, door de juiste paden toegankelijk te maken of af te schermen.
- **Wachtwoordcodering**: Zowel bij registratie als login wordt het wachtwoord gehasht met de `BCryptPasswordEncoder`, wat zorgt voor veilige opslag van wachtwoorden.

---
