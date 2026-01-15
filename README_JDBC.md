# Migration vers JDBC - Instructions d'Installation

## Prérequis

1. **Java 17+** installé
2. **MySQL Server** installé et démarré
3. **Maven** (optionnel, si vous utilisez le pom.xml)

## Configuration MySQL

### 1. Démarrer MySQL
```bash
# Sur Windows
net start mysql

# Sur Linux/Mac
sudo systemctl start mysql
```

### 2. Créer la base de données (automatique)
L'application créera automatiquement la base de données `rendezvous_db` et les tables au premier démarrage.

### 3. Configuration de la connexion
Les paramètres de connexion sont dans `DatabaseConnection.java`:
- URL: `jdbc:mysql://localhost:3306/rendezvous_db`
- User: `root`
- Password: `""` (vide)

**Si vous avez un mot de passe MySQL différent**, modifiez la ligne:
```java
private static final String PASSWORD = "votre_mot_de_passe";
```

## Lancement de l'Application

### Option 1: Avec IntelliJ IDEA
1. Ouvrez le projet dans IntelliJ
2. Assurez-vous que le driver MySQL est dans les dépendances
3. Lancez `HelloApplication.main()`

### Option 2: Avec Maven
```bash
mvn clean javafx:run
```

### Option 3: Manuellement
1. Téléchargez le driver MySQL: https://dev.mysql.com/downloads/connector/j/
2. Ajoutez le JAR au classpath
3. Compilez et lancez

## Vérification de l'Installation

Après le premier lancement, vérifiez dans MySQL:
```sql
USE rendezvous_db;
SHOW TABLES;
SELECT COUNT(*) FROM clients;
SELECT COUNT(*) FROM rendezvous;
```

## Dépannage

### Erreur "Driver MySQL non trouvé"
- Ajoutez le driver MySQL Connector/J au classpath
- Vérifiez que le driver est dans le dossier lib/

### Erreur "Access denied for user 'root'"
- Vérifiez que MySQL est démarré
- Vérifiez le mot de passe dans `DatabaseConnection.java`
- Créez l'utilisateur si nécessaire: `CREATE USER 'root'@'localhost' IDENTIFIED BY '';`

### Erreur "Communications link failure"
- Vérifiez que MySQL est démarré sur le port 3306
- Vérifiez que le firewall ne bloque pas la connexion

## Migration des Données Existantes

Si vous avez des données dans les fichiers CSV, vous pouvez les importer manuellement:
```sql
-- Exemple pour les clients
LOAD DATA INFILE 'data/clients.csv' 
INTO TABLE clients 
FIELDS TERMINATED BY ',' 
LINES TERMINATED BY '\n' 
IGNORE 1 LINES;
```

## Structure de la Base de Données

- `clients`: id, nom, prenom
- `categories`: id, nom, description
- `lieux`: id, nom, adresse
- `utilisateurs`: id, nom, prenom, email
- `rendezvous`: id, client_id, categorie_id, lieu_id, date

## Avantages de JDBC vs CSV

✅ **Performance**: Requêtes SQL optimisées
✅ **Scalabilité**: Gère des milliers d'enregistrements
✅ **Intégrité**: Contraintes foreign key
✅ **Concurrence**: Gestion multi-utilisateurs
✅ **Sécurité**: Transactions et rollback
