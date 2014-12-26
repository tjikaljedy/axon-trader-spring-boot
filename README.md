axon-trader-spring-boot
=======================
Please note: this only RESTful part of AXON-TRADER

This project to integrated Spring-boot with Axon-trader. to run this project as following:

Pre-requisite
1. Install Spring STS with Gradle
2. Install MySQL or MongoDB
3. Web-browser Chrome with Advanced Rest Client

Setup-MySQL
1. Setup MySQL port to 13099 and create new database bageaxe_db with user: root, pass: admin

Working with Spring STS 
1. Open Spring STS
2. Select File > Import, and under General choose Existing Project Into...
3. To modified MySQL configuration to meet your database target please see under src/main/resources/META-INF/persistence.xml and also under src/main/resources/conf/cqrs-infrastructure-context.xml, persistence-infrastructure-context.xml
4. Open com.si.xe.trader.webui.init.RunDBInitializerWhenNeeded.java enable line 42
5. Open com.si.xe.Application.java and RUN as Java Application

After first running, application will throw the error because AXON-framework initial database setup was problem to auto-generate TABLE associationvalueentry with mysql. Please patch your mysql with patch.sql under project doc.

6. Run once again the com.si.xe.Application.java, if success to run , open web-browser Chrome and invoke post http://localhost:10888/api/inital


The main goals of this project to integrated Spring-boot with Axon-trader






 
