# GestionChariteMicro
#  Plateforme Microservices de Gestion Caritative

`CharityManager` est une application distribuée basée sur une architecture **Microservices** avec Spring Boot et Spring Cloud. Elle permet de gérer de manière centralisée et découplée les associations, les actions de solidarité, les dons, les utilisateurs ainsi que le système de notification.

---

## 🏗️ Architecture Technique & Stack
- **Framework Principal :** Spring Boot (v3.3.5)
- **Écosystème Cloud :** Spring Cloud (v2023.0.3)
- **Langage :** Java 17
- **Gestion de Build :** Maven (Architecture multi-modules)
- **Communication :** Synchrone (OpenFeign) & Asynchrone
- **Découverte de services :** Netflix Eureka Server
- **Gestion de configuration :** Spring Cloud Config Server
- **Routage :** Spring Cloud Gateway

---

## 📦 Structure des Microservices (Isolation des bases de données)

L'architecture suit strictement le pattern *Database per Service* :

| Nom du Module | Port par défaut | Rôle principal |
| :--- | :---: | :--- |
| **`eureka-server`** | `8761` | Annuaire centralisé pour l'enregistrement et la découverte des services. |
| **`config-server`** | `9999` | Centralisation des fichiers de configuration externes. |
| **`gateway-source`** | `8888` | Point d'entrée unique (API Gateway) avec routage dynamique. |
| **`user-service`** | *Dynamique* | Gestion des utilisateurs, profils et authentification. |
| **`organisation-service`**| *Dynamique* | Gestion des associations et ONG partenaires. |
| **`actioncharite-service`**| *Dynamique* | Gestion des campagnes et événements de solidarité. |
| **`don-service`** | *Dynamique* | Traitement et suivi des transactions de dons. |
| **`participation-service`**| *Dynamique* | Gestion des inscriptions/participations aux actions. |

---

## 🚀 Guide de Démarrage

Pour lancer l'infrastructure correctement, respectez impérativement cet ordre de démarrage :

1. **Discovery Server (Eureka)** : Lancer `EurekaServerApplication` (Port `8761`)
2. **Config Server** : Lancer `ConfigServerApplication` (Port `9999`)
3. **API Gateway** : Lancer `GatewaySourceApplication` (Port `8888`)
4. **Microservices Métier** (`user-service`, `organisation-service`, `actioncharite-service`, `don-service`, etc.)

---

## 🧪 Validation & Tests
- **Tableau de bord Eureka :** [http://localhost:8761](http://localhost:8761)
- **Point d'entrée API Gateway :** [http://localhost:8888](http://localhost:8888)
