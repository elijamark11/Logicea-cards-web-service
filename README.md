# Logicea-cards-web-service
Cards RESTful web service by Logicea (sr)
Start  by setting following env variables
1. **LOGICEA_DB_URL** which is basically url for the mysql database. An example: jdbc:mysql://127.0.01:3306/logicea_cards_api?allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=false
2. **DB_USERNAME** which is db username
3. **DB_PASSWORD** which is the above username db password.
These are critical to the running of the system, alternatively just append them directly in **com.logicea.cards.configs/DataSourceConfig**

An empty database is required, the system will create required tables.

The system will seed two users: an admin (admin@live.com) and a member (joe@admin.com). Both their passwords is: **admin**

**To login: post your credentials here:**
  http://localhost:8300/api/login
  and the login body **email** & **password** in x-www-form-urlencoded

Have used swagger to document the work.  
**http://localhost:8300/swagger-ui.html**

And it requires **Bearer** token which be gotten from login (**accessToken**). 

