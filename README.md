# Concordia

<img src="documentation/images/java-swing-widget.jpg" alt="Widget" style="width:60%;">



## Overview
---

# Concordia

**Project Overview:**  
"Concordia" takes an old  **Java Swing** created in 2014, and uses **augmented AI**, to create a **swing front end locally and servlet for remote access from common code base**.  

**Project Purpose:**  
- **Reorganize a legacy Concordia app into clean layers**<br><br>
        WareHouse<br>
        ├── domain        (pure data + rules)<br>
        ├── repository    (JDBC / persistence)<br>
        ├── service       (business logic)<br>
        ├── controller    (Concordia + Servlet adapters)<br>
        ├── ui            (Concordia components only)<br>
        ├── infrastructure (bootstrapping, wiring)<br>

- **Infrastructure** created within app negating any need for Spring or DI      
- **Annotations** as labels ( semantic markers) 
- **Remote Acccess** using **servlet**, **Local Access** application building on **Java Concordia libraries**
- Integration of **augmented AI tools** to enhance development speed and functionality.  
- Fully **responsive design** for **Servlet** with attention to accessibility and user experience.  

**Live Project:** [Harmonia](#)  



<h2 align="center" id="TOC">Index</h2>

- [Concordia](#concordia)
  - [Overview](#overview)
- [Concordia](#concordia-1)
  - [Phases-one](#phases-one)
    - [Domain first](#domain-first)
  - [Phases-two](#phases-two)
    - [Repository extraction](#repository-extraction)
    - [Move database code without changing SQL](#move-database-code-without-changing-sql)
  - [Phases-three](#phases-three)
    - [Introduce Services (thin at first)](#introduce-services-thin-at-first)
  - [Phases-four](#phases-four)
    - [Controller layer (adapters)](#controller-layer-adapters)
  - [Phases-five](#phases-five)
    - [Clean the Concordia UI (liberation phase)](#clean-the-concordia-ui-liberation-phase)
  - [Phases-six](#phases-six)
    - [Infrastructure / Wiring](#infrastructure--wiring)
  - [Phases-seven](#phases-seven)
    - [Remove legacy shortcuts](#remove-legacy-shortcuts)
    - [Annotations](#annotations)
    - [Decouple](#decouple)
    - [Annotations](#annotations-1)
    - [Maven Build  Build file for project](#maven-build--build-file-for-project)
  

## Phases-one
---

### Domain first


<details>
    <summary>Details
    </summary>
    Phase 1 – Domain first 
        1️⃣ Move domain classes
            WareHouse.domain

        No logic changes
        Next, start with the domain layer. This is the safest step. Move your core model classes — Company, Item, history, User, Index — into a domain package. Do not add logic, do not add database code, do not add UI code. These classes should only represent data and relationships. Serialization is fine here.
</details>
<br>

[Back To Top](#concordia)

## Phases-two
---

### Repository extraction 


<details>
    <summary>Details
    </summary>
        2️⃣ Create repository package
            WareHouse.repository
</details>


### Move database code without changing SQL

<details>
    <summary>Details
    </summary>
        3️⃣ Move database code without changing SQL

        | Old class         | New repository                                             |
        | ----------------- | ---------------------------------------------------------- |
        | `Databases`       | `CompanyRepository`, `ItemRepository`, `HistoryRepository` |
        | `DatabaseBackup`  | `BackupRepository`                                         |
        | `DatabaseRestore` | `RestoreRepository`                                        |
        | `Serialized`      | `FileBackupRepository`                                     |
        Rules:

            One repository = one persistence concern

            JDBC only here

            Repositories return domain objects

            Add labels:
            @Repository
            public class ItemRepository { ... }

        No behaviour change yet.

        After that, extract the repository layer. This is where most of your current code actually belongs. All JDBC and file persistence logic moves here. Your existing DatabaseBackup, DatabaseRestore, Serialized, and similar classes become repositories. Each repository should focus on one persistence responsibility. At this stage, you are still not improving logic — only relocating it. These classes can be labelled with @Repository as a semantic marker, even without Spring.

</details>

<br>

[Back To Top](#swing---clean---layers)

## Phases-three
---

### Introduce Services (thin at first)
    

<details>
    <summary>Details
    </summary>
       4️⃣ Create service package
        WareHouse.service

        Create thin services:

        InventoryService

        BackupService

        @Service
        public class BackupService {
            private final BackupRepository repo;

            public void backup(List<Company> companies) {
                repo.backup(companies);
            }
        }

        At this stage:

        Services mostly delegate

            That’s OK
        RestoreService
        Then introduce the service layer. Services sit above repositories and represent business use cases such as inventory management, backups, restores, or reporting. Initially, services can be very thin and simply delegate to repositories. That is expected and correct. Label them with @Service to make intent clear.

</details>

<br>

[Back To Top](#swing---clean---layers)







## Phases-four
---

###  Controller layer (adapters)


<details>
    <summary>Details
    </summary>
        Phase 4 – Controller layer (adapters)
        5️⃣ Create controller package
            WareHouse.controller

            Purpose:

            Translate UI intent into service calls

            Controllers:

            InventoryController

            BackupController

            RestoreController

            Rules:

            Concordia talks only to controllers

            Controllers talk to services

            No JDBC

            No UI widgets in services


            @Component
            public class BackupController {
                private final BackupService service;

                public void backupPressed() {
                    service.backup();
                }
            }



        Next, add a controller layer. Controllers are the bridge between the Concordia UI (or servlets) and services. The UI should no longer call repositories or services directly. Controllers translate user actions like “Backup pressed” or “Delete item” into service calls. These are application controllers, not web controllers. Label them with @Component.
</details>
<br>

[Back To Top](#concordia)




## Phases-five
---

### Clean the Concordia UI (liberation phase)

<details>
    <summary>Details
    </summary>
        6️⃣ Simplify Concordia classes

        Now the UI becomes dumb (in a good way).

        Concordia does ONLY:

        Read text fields

        Show dialogs

        Call controller methods

        ❌ No SQL
        ❌ No business logic
        ❌ No static globals

        This is where Concordia suddenly becomes pleasant again.

        After that, clean up the Concordia UI. The UI should now only read user input, show dialogs, and call controller methods. There should be no SQL, no business logic, and no persistence code in Concordia classes. This is where Concordia becomes simple and manageable again.


</details>
<br>

[Back To Top](#concordia)



## Phases-six
---


### Infrastructure / Wiring




<details>
    <summary>Details
    </summary>

        WareHouse.infrastructure.ApplicationBootstrap sets up the database connection, repositories, service, controller, and launches the Mainframe. 
        This centralizes all object creation and wiring, replacing the need for Spring or any DI framework. You can now launch your app from ApplicationBootstrap. #

</details>
<br>

[Back To Top](#swing---clean---layers)



## Phases-seven
---

### Remove legacy shortcuts

<details>
    <summary>Details
    </summary>
    Only now:

        Remove static globals

        Remove maindriver.Company

        Remove hidden dependencies

        Remove circular references

        At this point:
        ✔ Testable
        ✔ Understandable
        ✔ Framework-ready (if you ever want Spring)






        Only at the end should you remove legacy shortcuts like static globals, hidden dependencies, and circular references. Do this last, when the structure is stable.


Phase 7 – Kill legacy shortcuts



Only now:

 fully remove the old main or further refactor!

Remove static globals

Remove maindriver.Company

Remove hidden dependencies

Remove circular references

At this point:
✔ Testable
✔ Understandable
✔ Framework-ready include 






Only at the end should you remove legacy shortcuts like static globals, hidden dependencies, and circular references. Do this last, when the structure is stable.


Annotations are labels, not magic:

@Repository

@Service

@Component






</details>
<br>

[Back To Top](#swing---clean---layers)


### Annotations 
---

<details>
    <summary>Details
    </summary>
    Semantic annotations are defined only as semantic markers- they do not carry meaning for humans (and future tools), but and have no runtime behaviour.
    They:
        do not create objects
        do not inject dependencies
        do not manage transactions
        do not change execution
    In this project @Service, @Repository, @Component, @Controller and @Transactional are used 
    They exist to say:
        “This class plays this role in the architecture.”

</details>
<br>

[Back To Top](#swing---clean---layers)


### Decouple 
---

<details>
    <summary>Details
    </summary>
    Introducing a real API boundary by decoupling the Swing client from the core application using HTTP-based Servlets.
    New Architecture
    backend/
        ├─ controller/      (Servlets = API boundary)<br>
        ├─ service/         (Business logic)<br>
        ├─ repository/      (JDBC)<br>
        ├─ domain/          (Entities)<br>
        ├─ dto/             (API models)<br>
        └─ infrastructure/  (DB, wiring)<br>
<br>
        swing-client/<br>
        ├─ ui/<br>
        ├─ http/<br>
        └─ dto/<br>
<br>
<br>
<br>
        ui/ — Swing screens & controllers<br>
                    JFrame, JPanel, JDialog<br>
                    Event handling<br>
                    Table models<br>
                    Input validation (UI-level only)<br>
 <br>                   
        http/ — API clients (boundary adapters)<br>
                    One class per backend resource<br>
                    Builds HTTP requests<br>
                    Handles status codes<br>
                    Maps JSON ↔ DTOs<br>
  <br>      
        dto/ — data transfer objects<br>
                    Plain data objects (POJOS)<br>
                    Serializable to/from JSON<br>
<br>
        🧠 How data flows (end-to-end)
                [User clicks button]
                        ↓
                UI (ui/)
                        ↓
                HTTP client (http/)
                        ↓   JSON over HTTP
                Backend Servlet
                        ↓
                HTTP response
                        ↓
                DTOs
                        ↓
                UI updates



        What Swing actually sends
                From Swing:
                GET /api/products
                POST /api/orders
                PUT /api/stock/42
        With:
        JSON payloads
        HTTP status codes
<br>
        Servlet: 
            Thin controllers by design
            REST encourages:
                No session state


            @WebServlet("/users")
            public class UserServlet {
                private final UserService userService;
        }

        4️⃣ REST Servlet (API boundary)
                controller/ProductServlet.java
                @WebServlet("/api/products")
                public class ProductServlet extends HttpServlet {

                    private ProductService service;
                    private ObjectMapper mapper;

                    @Override
                    public void init() {
                        DataSource ds = DataSourceFactory.create();
                        ProductRepository repo = new ProductRepository(ds);
                        service = new ProductService(repo);
                        mapper = new ObjectMapper();
                    }

                    @Override
                    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                            throws IOException {

                        List<Product> products = service.getAllProducts();

                        // Entity → DTO
                        List<ProductDto> dtoList = products.stream()
                            .map(p -> {
                                ProductDto dto = new ProductDto();
                                dto.id = p.getId();
                                dto.name = p.getName();
                                dto.stock = p.getStock();
                                return dto;
                            })
                            .toList();

                        resp.setContentType("application/json");
                        resp.setStatus(HttpServletResponse.SC_OK);
                        mapper.writeValue(resp.getOutputStream(), dtoList);
                    }
                }


🔥 This is the hard boundary
Everything dangerous stays behind it.

        The annotation:
        Adds no behavior
        Declares intent
        Makes future automation possible


</details>
<br>

[Back To Top](#decouple-swing-with-http-based-servlets)


### Annotations 
---

<details>
    <summary>Details
    </summary>
    decoupling of your Java Swing client from the backend using HTTP-based Servlets. Here’s a concise summary and explanation:

    Key Points:

    The Swing client (swing-client/) communicates with the backend (backend/) only via HTTP/JSON, not by calling Java classes directly.
    The backend is organized into clear layers: controller (Servlets as API boundary), service (business logic), repository (JDBC), domain (entities), dto (API models), and infrastructure (wiring, DB).
    The Swing client is split into ui (screens/controllers), http (API clients), and dto (data transfer objects).
    Data flow: User interacts with UI → HTTP client sends JSON over HTTP → Backend Servlet processes and responds → UI updates with DTOs.
    Example API calls: GET /api/products, POST /api/orders, PUT /api/stock/42, all using JSON payloads and HTTP status codes.
    Servlets act as thin controllers, with no session state, and map entities to DTOs for the API.
    Annotations like @WebServlet, @Service, @Repository, etc., are used as semantic markers only—they declare intent but add no runtime behavior.
    Purpose:

    This “hard boundary” ensures the UI is fully decoupled from backend logic and persistence, making the system more maintainable, testable, and ready for future automation or framework changes.
    If you need help implementing this structure, creating DTOs, or wiring up the HTTP client or Servlets, let me know your next step!


</details>
<br>

[Back To Top](#swing---clean---layers)


### Maven Build  Build file for project 
---

<details>
    <summary>Details
    </summary>
 Full project structure
    ```code
    concordia/                     <-- project root <br> 
├─ pom.xml                        <-- Maven POM (builds WAR + Swing JAR) <br> 
├─ src/Concordia/                 <-- all Java source files <br> 
│   ├─ annotations/               <-- custom backend annotations <br> 
│   │   └─ (e.g., Repository.java, Service.java) <br> 
│   ├─ controller/                <-- Servlets / REST controllers <br> 
│   │   └─ ConcordiaServlet.java <br> 
│   ├─ domain/                    <-- domain entities (backend) <br> 
│   │   └─ Company.java <br> 
│   │   └─ Item.java <br> 
│   │   └─ User.java <br> 
│   │   └─ History.java <br> 
│   ├─ dto/                       <-- DTO classes <br> 
│   │   └─ CompanyDto.java <br> 
│   │   └─ ItemDto.java <br> 
│   │   └─ UserDto.java <br> 
│   │   └─ HistoryDto.java <br> 
│   ├─ infrastructure/            <-- utility classes, configs <br> 
│   │   └─ DBConnection.java <br> 
│   │   └─ AppConfig.java <br> 
│   ├─ repository/                <-- DAO / database access <br> 
│   │   └─ CompanyRepository.java <br> 
│   │   └─ ItemRepository.java <br> 
│   │   └─ UserRepository.java <br> 
│   │   └─ HistoryRepository.java <br> 
│   ├─ service/                   <-- business logic <br> 
│   │   └─ CompanyService.java <br> 
│   │   └─ ItemService.java <br> 
│   │   └─ UserService.java <br> 
│   │   └─ HistoryService.java <br> 
│   ├─ Main.java                  <-- Swing main class (entry point) <br> 
│   ├─ MainDriver.java            <-- additional Swing launcher <br> 
│   ├─ AdminPanel.java            <-- Swing admin panel <br> 
│   ├─ CompanyItemTablePanel.java <-- Swing table panel <br> 
│   └─ (any other Swing UI classes) <br> 
├─ webapp/                        <-- web resources for WAR <br> 
│   ├─ WEB-INF/ <br> 
│   │   └─ web.xml                <-- optional servlet config <br> 
│   └─ (static files / JSPs) <br> 
├─ target/                        <-- Maven build output <br> 
│   ├─ classes/                   <-- compiled .class files <br> 
│   │   └─ Concordia/             <-- package folders reflect src/Concordia <br> 
│   ├─ concordia-backend.war      <-- WAR for Jetty deployment <br> 
│   └─ concordia-swing.jar        <-- Runnable Swing JAR <br> 


Notes

- Swing files (Main.java, MainDriver.java, AdminPanel.java, CompanyItemTablePanel.java) are directly under src/Concordia/.

- Backend folders (controller, domain, dto, repository, service, etc.) are all in the same src/Concordia/ root.

- Maven will compile all .java files in src/Concordia/ into target/classes/Concordia/....

- The main.class in the POM should point to the class with the Swing main() method, e.g.:

<main.class>Concordia.Main</main.class>


- Your WAR will only include backend + web resources; the Swing JAR is separate.

</details>
<br>

[Back To Top](#swing---clean---layers)

















