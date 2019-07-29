# Warning
This README.rd contains only technical and licencing related issues and will not describe the application itself and its features! For an indepth description of the app itself, please read our wiki entry.  
Thank you!  

# Installation
To installand run this application on your computer, check if you fulfill following prerequisites:
1. Install JavaSE 11 and JavaEE 8, IntelliJ Ultimate Edition, Angular, node.js and NPM.
2. Make sure that you have a PostgreSQL database running on localhost:5432 with the name 'so-challenge'.
   The owner of the database should be 'postgres' with the password 'pgadminplaysdart31'
     
   Note: this is not a production release, so passwords and other private pieces of information can be public for now.
   
3. Install WildFly 17.0.1 JavaEE application server.

4. After that, try to emphasize this package structure:
   ```
   stackoverflowchallenge
          |
           --- git (your repo clone comes here)
               |
                --- so-challenge/ (parent-pom)
                    |
                     --- so-challenge-backend/(child-pom)
                         so-challenge-frontend/(child-pom)
                         .gitignore
                         README.md
                         pom.xml
          | 
           --- wildfly (your unzipped wildfly installation, that we will use later on)
           
          |
           --- workspace (.idea directory that is created by IntelliJ automatically --> it's annoying so we put it there)
   ```        

# Setup
1. Open IntelliJ Ultimate Edition 
2. Click on ```File > New > Module from Existing Sources...```
3. Double click on the parent pom.xml which is under the path: ```stackoverflowchallenge/git/so-challenge/pom.xml```
4. If IntelliJ asks you to choose your workspace folder, just choose ```stackoverflow/workspace``` for that
5. Next click on edit configuration on the top right dropdown (next to the green 'play' arrow)
6. A popup will open; click on the ```+``` sign 
7. A list saying add new configuration will appear
   In this list, search for JBoss server and choose 'Local'
8. After that pass in your path to the WildFly server and add 2 artifacts.
   Choose the exploded frontend and exploded backend.
9. Hit OK, click on the green 'play' button and you are good to go.

# Explanation of the code
We have 3 maven modules:  
      parent  
      backend  
      frontend  
      
The frontend is built with maven frontend plugin (link in wiki)

The backend code is using JPA for OR mapping.
It is using a @Stateless DAO to perform CRUD operations to our entity classes.
When creating an endpoint, just take a sample from a existing one, and put it inside the ```ApplicationConfig.java``` class!

The frontend is using Angular Material and consists of several components.



# Problems that we have

# Contribute
Everybody is welcome and can contribute to this project, since it is free software.
You can study, modify and dristribute the source code as you wish.
