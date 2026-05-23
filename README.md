# Diogenes

Diogenes is a Java web application built with Spring Boot. 

## Current Features

- Account registration and sign-in (passwords stored as BCrypt hashes)
- Per-user journal entries, check-ins, and survey results
- Journal with automatic stress categorization
- Quick wellness check-in with Java-based age-aware recommendation logic
- Structured surveys page (PHQ-9, GAD-7, WHO-5) with scored results and guidance
- Dated journal entries
- Support guidance for different age groups
- Input validation

## Tech Stack

- Java 21
- Spring Boot
- Spring MVC
- Thymeleaf
- Spring Data JPA
- H2 Database
- HTML/CSS

## Project Structure

src/main/java/com/diogenes
|- auth          (login error codes)
|- config        (security)
|- controller
|- controller/advice  (shared form model attributes)
|- model
|- repository
|- security      (user principal, login failure handling)
|- service

src/main/resources/templates
|- layout.html   (shared page shell)
|- fragments.html (head, nav, overlay)

## How to Run

1. Open the project in the terminal.
2. Run (on this computer only):
```bash
mvn spring-boot:run
```

3. Open `http://localhost:8080` and create an account at `/register`, then sign in at `/login`
4. Open H2 console at `http://localhost:8080/h2-console` for the database

### Access from another device (same Wi‑Fi)

1. Start the app listening on your network (not only localhost):
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=lan
```

2. Find this Mac’s local IP (Wi‑Fi is usually `en0`):
```bash
ipconfig getifaddr en0
```
Example result: `192.168.1.42`

3. On the other device (phone, tablet, another laptop), open:
```
http://192.168.1.42:8080
```
Use your real IP instead of `192.168.1.42`.

4. Both devices must be on the **same network** (same home Wi‑Fi). Guest networks often block device-to-device traffic.

5. If the page does not load, allow incoming connections for Java in **System Settings → Network → Firewall** (or turn the firewall off briefly to test).

**Note:** This is for local testing only. The in-memory H2 database is still on the Mac running the app; other devices only use the UI/API over the network. For access over the internet you would need hosting (e.g. Railway, Render) or a tunnel (e.g. ngrok), not just this setting.

## Authentication

- Register at `/register`, sign in at `/login`, sign out from the nav button
- Passwords are hashed with BCrypt before they are saved to the `app_user` table
- Journal, assessment, and survey data are scoped to the signed-in user via `user_id`

## Pages

### Home

Shows the main navigation and links to the main sections.

### Assessment

Stores the latest quick check-in based on age group, sleep, energy, anxiety, and support choices.

### Surveys

Separate page at `/survey` for PHQ-9 (depression), GAD-7 (anxiety), and WHO-5 (well-being). Each submission saves total score, severity label, recommendation, and urgent-support flag when needed.

### Journal

Saves journal entries with a date, mood, reflection, and calculated stress category.

### Community and Support

Support now includes grounded guidance cards. Community is only a dummy placeholder page.
