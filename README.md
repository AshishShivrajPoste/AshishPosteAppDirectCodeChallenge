# AppDirectCodingChallenge
Coding Challenge of AppDirect


#Instalation

The Project is created in Eclipse using Maven Build tool.

Steps  :

1) git clone https://github.com/AshishShivrajPoste/AshishPosteAppDirectCodeChallenge.git

2) mvn clean package
  Note : please change the consumerKey and consumerSecret in config.properties file under /src/main/resource before packaginf the application
3) Deploy the war generated into tomcat server.

  copy the .war generated in step 2 to tomcat webapp folder

4) start tomcat

I am using ngrok for creating the tunnel.

# Configuration

Please change the consumerKey and consumerSecret in config.properties file under /src/main/resource.



# AppDirect Event Notification URL configured

As I am using ngrok for tunnelling please update the URL accordinglly.

Subscription Create Notification URL

http://690c679c.ngrok.io/codechallenge/webapi/subscription/create?url={eventUrl}

Subscription Change Notification URL

http://690c679c.ngrok.io/codechallenge/webapi/subscription/change?url={eventUrl}

Subscription Cancel Notification URL

http://690c679c.ngrok.io/codechallenge/webapi/subscription/cancel?url={eventUrl}

Subscription Status Notification URL

http://690c679c.ngrok.io/codechallenge/webapi/subscription/status?url={eventUrl}

User Assignment Notification URL

http://690c679c.ngrok.io/codechallenge/webapi/user/assign?url={eventUrl}

User Unassignment Notification URL

http://690c679c.ngrok.io/codechallenge/webapi/user/unassign?url={eventUrl}

