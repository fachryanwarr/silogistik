#How to run this project

1. Copy the contents of the .env.example file and create .env file with the copied contents
2. Go to the silogistik2106751070 folder and build Gradle
3. Match port `5432` to your local PostgreSQL port pada bagian `jdbc:postgresql://localhost:5432/...`
4. Create two databases with the names "apap-silogistik" and "apap-silogistik-prod"
5. Customize your PostgreSQL user and password in the `PROD_USERNAME`, `PROD_PASSWORD`, `DEV_USERNAME`, and `DEV_PASSWORD` sections
6. Run project from `Silogistik2106751070Application.java` file
7. Open `http://localhost:8080` from your browser
