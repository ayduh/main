Topic and definition:
1.	Topic: Warehouse Management System
Short description:
•	Goal: Make backend-application for managing the warehouse stock using the database and business logic of items accounting.
•	What application does: Simplifies warehouse inventory management by providing a structured way to store data in a database and apply business logic rules, such as preventing negative stock values.
Entity: Item:
- id
- name
- description
- quantity
- min_quantity
- category

HOW TO START:
1) To start the program go to src\main\java\com\ayduh\warehouse\Main.java
2) Open src\resources
3) Open pgAdmin
4) Execute init.sql as superuser (postgres)
5) Connect to Warehouse_DB as user `postgres`
6) Execute schema.sql
